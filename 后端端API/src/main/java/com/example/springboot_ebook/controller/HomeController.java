package com.example.springboot_ebook.controller;



import com.example.springboot_ebook.dao.EbookMapper;
import com.example.springboot_ebook.model.Bookinfo;
import com.example.springboot_ebook.model.Ebook;
import com.example.springboot_ebook.model.Nodeinfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.TextUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListenerMethodProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.el.ELManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class HomeController {
    ObjectMapper objectMapper=new ObjectMapper();
    //http://www.shuquge.com/txt/5809/index.html
    //剑来
    //http://www.shuquge.com/txt/8659/index.html
    //http://www.shuquge.com/txt/93950/index.html
    public  String html_url= "http://www.shuquge.com/txt/8659/index.html";
    public boolean tf=true;
    public int [] urlid=new int[500];
    //依赖注入
    @Autowired
    EbookMapper ebookMapper;
   public Http_Request hr=new Http_Request();
    @ResponseBody
    @RequestMapping(value = "/index")
    public String Index()throws JsonProcessingException{
        //control();
        if(tf)
        {
            showDayTime();
        }
        List<Bookinfo> bi=ebookMapper.selectBookinfoByIDList();
        return  objectMapper.writeValueAsString(bi);
    }

    @ResponseBody
    @RequestMapping(value = "/getbookinfo")
    public String Getbookinfo(HttpServletRequest request)throws JsonProcessingException{
        int id=Integer.parseInt( request.getParameter("id"));
        Bookinfo bi=ebookMapper.selectBookinfoByID(id);
        return  objectMapper.writeValueAsString(bi);
    }

    //书以及章节的信息抓取控制器
    public void control()
    {

        Bookinfo bi= getinfo();
        if (bi==null)
        {
            System.out.println("网络请求失败!");
        }
        else
        {
            if (ebookMapper.selectBookinfoByID(bi.getId())==null) {
                ebookMapper.insertBookinfo(bi);
            }else
            {
                System.out.println("书已经存在");
            }
            for (int a=0;a<urlid.length;a++){
                if (urlid[a]!=0&&ebookMapper.selectNodeByNodeID(urlid[a])==null) {

                    if(urlid[a]==24482478)
                    {
                        System.out.println("监听");
                    }
                    insNode(urlid[a],bi.getId(),bi.getCreateDate(),bi.getImgurl());
                }else
                {
                    System.out.println("章节已经存在");
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/read")
    public String read(HttpServletRequest request)throws JsonProcessingException
    {
        int id=Integer.parseInt( request.getParameter("id"));
        Nodeinfo ni=ebookMapper.selectNodeByNodeID(id);
         ni.setTxt(readftxt(ni.getBookid(),ni.getId()));
        //return result.toJSONString();
        return  objectMapper.writeValueAsString(ni);
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public String getlist(HttpServletRequest request) throws JsonProcessingException {
        int id=Integer.parseInt( request.getParameter("id"));
       List<Nodeinfo> fi=ebookMapper.selectNodeByNodeIDList(id);

       // Nodeinfo ni=ebookMapper.selectNodeByNodeID();
        return objectMapper.writeValueAsString(fi);
    }

    //访问url
    public Document open(String url)
    {
        String html= hr.doGet(url);
        if(html=="")
        {
            return null;
        }
        Document doc= Jsoup.parse(html);
        return doc;
    }

    //加载并解析html
    public Bookinfo getinfo()
    {
        Document doc= open(html_url);
        String name=doc.select("div.info >h2").first().html();//获取书名
        String author=doc.select("div.info >div.small >span").first().text();//作者
        String booktype=doc.select("div.info >div.small >span").eq(1).text();//类型
        String bookstate=doc.select("div.info >div.small >span").eq(2).text();//状态
        String booknumber=doc.select("div.info >div.small >span").eq(3).text();//字数
        String img_url=doc.select("div.info >div.cover >img").attr("abs:src");
        String[] Synopsis=doc.select("div.info >div.intro").first().text().split("展开>>");
        String time=doc.select("div.info >div.small >span.last").first().text();//获取时间
        String pattern="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Matcher p=Pattern.compile(pattern).matcher(time);
        while (p.find())
        {
            time=p.group();
        }
        int bookid=Integer.parseInt(Pattern.compile("[^0-9]").matcher(html_url).replaceAll("").trim());//书id
        Element node=doc.select("div.listmain >dl>dd>a").first();
        boolean tf=true;
        int Counter=0;
        while (tf)
        {
            try{
                urlid[Counter] =Integer.parseInt(doc.select("div.listmain >dl>dd>a").eq(Counter).attr("href").split(".html")[0]);
                Counter+=1;
            }catch (Exception e)
            {
                tf=false;
                System.out.println("所有章节id全部Git到手，开溜");
            }
        }
        String bookUrl="http://www.shuquge.com/txt/8659/";//书URL
        int node_id=Integer.parseInt(node.attr("href").split(".html")[0]);//章节id
        String nodeUrl=bookUrl+node_id+".html";//章节url
        String nodeName=node.text();//章节名-
        //添加书信息
        Bookinfo bi=new Bookinfo();
        bi.setId(bookid);//书id
        bi.setAuthor(author);//作者
        bi.setBookname(name);//书名
        bi.setCreateDate(time);//时间
        bi.setSynopsis(Synopsis[0]);//简介
        bi.setNodeName(nodeName);//章节名
        bi.setNodeUrl(nodeUrl);//章节url
        bi.setNode_id(node_id);//章节id
        bi.setImgurl(img_url);
        bi.setWordnumber(booknumber);//字数
        bi.setState(bookstate);//状态
        bi.setNewnode(nodeName);//最新章节
        bi.setBooktype(booktype);//类型
        return bi;
    }

    public  int  insNode(int nodeid,int bookid ,String date,String imgurl)
    {
        String bookUrl="http://www.shuquge.com/txt/"+bookid+"/"+nodeid+".html";//书URL
        Document doc= open(bookUrl);
        String nodeName=doc.select("div.book.reader >div.content>h1").text();

        int tip=0;
        if (ebookMapper.selectNodeByNodeID(nodeid)==null)
        {
            String content=Jsoup.parse(hr.doGet(bookUrl)).select("div#content").text();//获取文章的内容
            Nodeinfo ni=new Nodeinfo();
            ni.setId(nodeid);//章节id
            ni.setNodeName(nodeName);//章节名
            ni.setBookid(bookid);//书id
            ni.setNodedate(date);
            ni.setImgurl(imgurl);
            tip= ebookMapper.insertNodeinfo(ni);//添加
            if (tip==1)
            {
                newfile(bookid,nodeid,content);//
            }else
            {
                System.out.println("添加失败！");
            }
        }
        return tip;
    }

    //创建本地文件夹
    public  void newfile(int bookid,int nodeid,String content)
    {
        // 指定路径如果没有则创建并添加
        String BookUrl="D:\\"+bookid;
        String NodeName=BookUrl+"\\"+nodeid+".txt";
        File file = new File(BookUrl);
        //判断是否存在
        if (!file.exists()) {
        //创建父目录文件
            file.mkdirs();
        }
        File filetxt = new File(NodeName);
        if (!filetxt.exists()) {
            FileWriter fwriter = null;
            try {
                fwriter = new FileWriter(NodeName);
                fwriter.write(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fwriter.flush();
                    fwriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("txt创建成功");
        } else {
            System.out.println("txt已存在");
        }
    }

    //读取本地文件夹
    public  String readftxt(int bookid,int nodeid) {
        String fileName="D:\\"+bookid+"\\"+nodeid+".txt";
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"utf-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String data = new String(sb); //StringBuffer ==> String
       return  data;
    }

    //txt下载
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        int id=Integer.parseInt( request.getParameter("id"));
        int bookid=0;
        if(ebookMapper.selectBookinfoByID(id)!=null)
        {
            List<Nodeinfo> nodeinfos=ebookMapper.selectNodeByNodeIDListASC(id);
            String [] url=new String[nodeinfos.size()];
            for (int a=0;a<nodeinfos.size();a++)
            {
                url[a]="D:\\"+id+"\\"+nodeinfos.get(a).getId()+".txt";
            }
            String outFile="D:\\"+id+"\\"+id+".txt";
            bookid=id;
            mergeFiles(url,outFile);

        }else
        {
            bookid=ebookMapper.selectNodeByNodeID(id).getBookid();
        }

        String fileName = id+".txt";// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://"+bookid+"//";
            File file = new File(realPath , fileName);

            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    //定时执行器
    public  void showDayTime() {
        tf=false;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 10, 44, 00);//设置要执行的日期时间

        Date defaultdate = calendar.getTime();

        Timer dTimer = new Timer();
        dTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("每日任务开始执行");
                control();
                System.out.println("每日任务执行完毕");
            }
        }, defaultdate , 24* 60* 60 * 1000);//24* 60* 60 * 1000
    }

    //合并章节txt为一本
    public static final int BUFSIZE = 1024 * 8;
    public static void mergeFiles(String[] files,String outFile) {
        FileChannel outChannel = null;
       System.out.println("需要合并的txt文件: " + Arrays.toString(files) + " into " + outFile);
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for(String f : files){
                Charset charset=Charset.forName("utf-8");
                CharsetDecoder chdecoder=charset.newDecoder();
                CharsetEncoder chencoder=charset.newEncoder();
                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
                CharBuffer charBuffer=chdecoder.decode(bb);
                ByteBuffer nbuBuffer=chencoder.encode(charBuffer);
                while(fc.read(nbuBuffer) != -1){

                    bb.flip();
                    nbuBuffer.flip();
                    outChannel.write(nbuBuffer);
                    bb.clear();
                    nbuBuffer.clear();
                }
                fc.close();
            }
            System.out.println("n合1完成!!! ");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}
        }
    }
}
