# Spring Boot框架写的电子书爬虫
 * #### 语言：
   * Java、Javascript、Jquery
 * #### 涉及技术：
   * SpringBoot框架、MyBatis框架、MySQL数据库、Bootstrap框架
 * #### 功能介绍：
   * 抓取网址所有分类书籍的URL；
   * 根据输入的书名去查找数据库的 URL 再进行章节的抓取；
   * 根据输入的 URL 去抓取所有的章节，并保存到本地；
   * 前端首页根据书籍id显示，并能提供下载整本书；
   * 前端详细页面根据对应的书籍id加载章节，点击详情时加载对应章节内 容提供实时预览，并提供章节下载；
 * #### 重要的类及接口介绍：
   *	总页面控制器: controller.Homecontroller 
   *	URL请求响应: controller.Http_Request 
   *	增删改查Mapper接口: dao.EbookMapper 
   *	书籍信息实体类: model.Bookinfo 
   *	章节信息实体类: model.Nodeinfo 
   *	sql语句: Mapper.EbookMapper.xml 
   *	跨域处理: SpringbootEbookApplication 
 * #### 简介：
   * 项目纯属自娱自乐

