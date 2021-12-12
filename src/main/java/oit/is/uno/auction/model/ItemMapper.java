package oit.is.uno.auction.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper {
  @Select("select id from items where name = #{name}")
  int selectItemIdByName(String name);
}
