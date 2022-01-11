package oit.is.uno.auction.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UsersMapper {
  @Select("select pass from users where name = #{name}")
  String selectPassByName(String name);

  @Select("select id from users where name = #{name}")
  int selectIdByName(String name);

  @Select("select name from users where id = #{id}")
  String selectNameById(int id);

  @Select("select login from users where name = #{name}")
  String selectLoginByName(String name);

  @Select("SELECT money from users where id = #{userId}")
  int selectMoneyById(int userId);

  @Update("UPDATE USERS SET login = #{date} where name = #{name}")
  void updLastLogin(String date, String name);

  @Update("UPDATE USERS SET money = #{bonus} where name = #{name}")
  void updMoney(int bonus, String name);

}
