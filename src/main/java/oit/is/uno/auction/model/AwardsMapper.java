package oit.is.uno.auction.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AwardsMapper {
  @Insert("insert into awards (bidderId, itemId) values (#{bidderId}, #{itemId})")
  void insertAward(int bidderId, int itemId);
}
