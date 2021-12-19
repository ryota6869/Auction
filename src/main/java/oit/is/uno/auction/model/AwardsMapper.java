package oit.is.uno.auction.model;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AwardsMapper {
  @Insert("insert into awards (bidderId, itemId) values (#{bidderId}, #{itemId})")
  void insertAward(int bidderId, int itemId);

  @Select("select awards.id, items.name item from awards, items where items.id = awards.itemId and bidderId = #{bidderId}")
  ArrayList<Awards> selectAwardsByBidderId(int bidderId);
}
