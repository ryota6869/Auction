package oit.is.uno.auction.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BagMapper {
  @Select("Select quantity from bag where userId = #{userId} and itemId = #{itemId}")
  int selectQuantityOfItem(int userId, int itemId);

  @Update("UPDATE BAG SET quantity = #{quantity} where userId = #{userId} and itemId = #{itemId}")
  void updQuantity(int userId, int itemId, int quantity);
}
