package oit.is.uno.auction.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuctionMapper {
  @Select("select auction.id, auction.maxbid, auction.date, items.name, users.name from auction, items, users where auction.itemid=items.id and auction.sellerid=users.id")
  ArrayList<AuctionInfo> selectAuctionInfos();
}
