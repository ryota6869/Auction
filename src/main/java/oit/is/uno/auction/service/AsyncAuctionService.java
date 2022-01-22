package oit.is.uno.auction.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

  public void syncSellItem(int sellerId, int itemId, String dueDate) {
    aMapper.insertInfo(sellerId, itemId, dueDate);
    this.dbUpdated = true;
  }

  @Async
  public void asyncShowAuctionInfos(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        ArrayList<AuctionInfo> aInfos = this.syncShowAuctionInfos();
        emitter.send(aInfos);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowAuctionInfos complete");
  }
}
