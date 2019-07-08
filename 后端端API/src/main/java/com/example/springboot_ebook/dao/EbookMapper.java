package com.example.springboot_ebook.dao;

import com.example.springboot_ebook.model.Bookinfo;
import com.example.springboot_ebook.model.Ebook;
import com.example.springboot_ebook.model.Nodeinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EbookMapper {

    Bookinfo  selectBookinfoByID(int id);
    List<Bookinfo> selectBookinfoByIDList();

    Nodeinfo  selectNodeByNodeID(int id);
    List<Nodeinfo>  selectNodeByNodeIDList(int id);
    List<Nodeinfo>  selectNodeByNodeIDListASC(int id);

    int insertBookinfo(Bookinfo info);
    int insertNodeinfo(Nodeinfo info);

//    Ebook updateEbook(int id);
//    Ebook deleteEbook(int id);

}
