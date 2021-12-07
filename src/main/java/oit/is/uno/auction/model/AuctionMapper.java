package oit.is.uno.auction.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AuctionMapper {
  @Select("select auction.id, auction.maxbid, auction.date, items.name itemName, users.name sellerName from auction, items, users where auction.itemid=items.id and auction.sellerid=users.id")
  ArrayList<AuctionInfo> selectAuctionInfos();

  @Select("select auction.id, auction.maxbid, auction.date, items.name itemName, users.name sellerName from auction, items, users where auction.itemid=items.id and auction.sellerid=users.id and auction.id=#{id}")
  AuctionInfo selectById(int id);

  @Update("update auction set maxbid =#{bid} where id=#{id}")
  void updateMaxbidById(int bid, int id);
}
