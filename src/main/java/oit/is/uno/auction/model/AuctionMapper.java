package oit.is.uno.auction.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AuctionMapper {
  @Select("select auction.id, auction.maxbid, auction.date, items.name itemName, users.name sellerName, auction.bidderid from auction, items, users where auction.itemid=items.id and auction.sellerid=users.id")
  ArrayList<AuctionInfo> selectAuctionInfos();

  @Select("select auction.id, auction.maxbid, auction.date, items.name itemName, users.name sellerName, auction.bidderid from auction, items, users where auction.itemid=items.id and auction.sellerid=users.id and auction.id=#{id}")
  AuctionInfo selectById(int id);

  @Update("update auction set maxbid =#{bid} where id=#{id}")
  void updateMaxbidById(int bid, int id);

  @Update("update auction set bidderid =#{userId}")
  void updateUserIdById(int userId);

  @Delete("delete from auction where id=#{id}")
  void deleteById(int id);

  @Insert("INSERT INTO AUCTION (itemId, sellerId, maxBid, date) VALUES (#{itemId}, #{sellerId}, 0, #{dueDate});")
  void insertInfo(int sellerId, int itemId, String dueDate);
}
