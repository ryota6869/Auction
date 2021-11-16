package oit.is.uno.auction.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {
  @Select("select pass from users where name = '#{name}'")
  String selectPassByName(String name);
}
