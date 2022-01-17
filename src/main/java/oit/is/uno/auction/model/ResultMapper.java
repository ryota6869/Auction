package oit.is.uno.auction.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ResultMapper {
  @Insert("INSERT INTO RESULTS (sellerId, itemId, result, date) values (#{sellerId}, #{itemId}, #{result}, #{date})")
  void insertResult(int sellerId, int itemId, String result, String date);

  @Select("select results.id, items.name item, results.result, results.date from results, items where items.id = results.itemid and results.sellerId = #{sellerId}")
  ArrayList<Results> selectResultsBySellerId(int sellerId);
}
