package oit.is.uno.auction.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.uno.auction.model.AuctionInfo;
import oit.is.uno.auction.model.AuctionMapper;

@Service
public class AsyncAuctionService {

  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncAuctionService.class);

  @Autowired
  AuctionMapper aMapper;

  public ArrayList<AuctionInfo> syncShowAuctionInfos() {
    return aMapper.selectAuctionInfos();
  }

  public void syncItemSold(int id) {
    aMapper.deleteById(id);
    this.dbUpdated = true;
  }

  public void syncChangeWinner(int auctionId, int bid, int userId) {
    aMapper.updateMaxbidById(bid, auctionId);
    aMapper.updateUserIdById(userId);
    this.dbUpdated = true;
  }
}
