package oit.is.uno.auction.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper {
  @Select("select id from items where name = #{name}")
  int selectItemIdByName(String name);

  @Select("select * from items")
  ArrayList<Items> selectItems();
}
