package oit.is.uno.auction.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper {
  @Select("select id from items where name = #{name}")
  int selectItemIdByName(String name);

  @Select("select items.id, items.name, ROW_NUMBER() OVER(PARTITION BY bag.userid ORDER BY bag.userid ASC) num from items, bag where items.id = bag.itemId and bag.userId = #{userId} and bag.quantity > 0;")
  ArrayList<Items> selectItems(int userId);

  @Select("select count(*) from items, bag where items.id = bag.itemId and bag.userId = #{userId} and bag.quantity > 0;")
  int countItems(int userId);
}
