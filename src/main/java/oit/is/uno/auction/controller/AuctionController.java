package oit.is.uno.auction.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.security.Principal;
//import java.util.Date;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import oit.is.uno.auction.model.UsersMapper;
import oit.is.uno.auction.service.AsyncAuctionService;
import oit.is.uno.auction.model.AuctionMapper;
import oit.is.uno.auction.model.AwardsMapper;
import oit.is.uno.auction.model.ItemMapper;
import oit.is.uno.auction.model.AuctionInfo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import oit.is.uno.auction.model.Items;
import oit.is.uno.auction.model.ResultMapper;
import oit.is.uno.auction.model.BagMapper;
import oit.is.uno.auction.model.DateInit;

@Controller
@RequestMapping("/auction")
public class AuctionController {

  @Autowired
  UsersMapper uMapper;

  @Autowired
  AuctionMapper aMapper;

  @Autowired
  ItemMapper iMapper;

  @Autowired
  AwardsMapper awMapper;

  @Autowired
  ResultMapper rMapper;

  @Autowired
  BagMapper bMapper;

  @Autowired
  AsyncAuctionService aService;

  @GetMapping()
  public String auction(ModelMap model, Principal prin) {
    int quantity;
    ArrayList<AuctionInfo> auctionInfos = aService.syncShowAuctionInfos();

    Date today = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(today);

    Date sqlToday = Date.valueOf(date);
    for (AuctionInfo aInfo : auctionInfos) {
      Date sqlDate = Date.valueOf(aInfo.getDate());
      if (sqlDate.before(sqlToday) || sqlToday.compareTo(sqlDate) == 0) {
        int sellerId = uMapper.selectIdByName(aInfo.getSellerName());
        int itemId = iMapper.selectItemIdByName(aInfo.getItemName());
        if (aInfo.getBidderId() != 0) {
          int bidderMoney = uMapper.selectMoneyById(aInfo.getBidderId());
          int sellerMoney = uMapper.selectMoneyById(uMapper.selectIdByName(aInfo.getSellerName()));
          if (bidderMoney >= aInfo.getMaxBid()) {
            awMapper.insertAward(aInfo.getBidderId(), itemId);
            String name = uMapper.selectNameById(aInfo.getBidderId());
            int bidderCurrent = bidderMoney - aInfo.getMaxBid();
            uMapper.updMoney(bidderCurrent, name);
            int sellerCurrent = sellerMoney + aInfo.getMaxBid();
            uMapper.updMoney(sellerCurrent, aInfo.getSellerName());
            quantity = bMapper.selectQuantityOfItem(aInfo.getBidderId(), itemId);
            bMapper.updQuantity(aInfo.getBidderId(), itemId, quantity + 1);
            rMapper.insertResult(sellerId, itemId, "成功", date);
          } else {
            quantity = bMapper.selectQuantityOfItem(sellerId, itemId);
            bMapper.updQuantity(sellerId, itemId, quantity + 1);
            rMapper.insertResult(sellerId, itemId, "失敗", date);
          }
        } else {
          quantity = bMapper.selectQuantityOfItem(sellerId, itemId);
          bMapper.updQuantity(sellerId, itemId, quantity + 1);
          rMapper.insertResult(sellerId, itemId, "失敗", date);
        }
        aService.syncItemSold(aInfo.getId());
      }
    }

    auctionInfos = aService.syncShowAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);

    return "auction.html";
  }

  @GetMapping("/bid")
  public String bid(@RequestParam Integer auctionId, ModelMap model, Principal prin) {

    AuctionInfo auctionInfo = aMapper.selectById(auctionId);
    String userName = prin.getName();
    if (auctionInfo.getSellerName().equals(userName)) {
      model.addAttribute("seller", userName);
    } else {
      model.addAttribute("bidder", userName);
    }
    model.addAttribute("auctionInfo", auctionInfo);
    return "bid.html";
  }

  @GetMapping("/bid/end")
  public String end(@RequestParam Integer auctionId, ModelMap model) {
    AuctionInfo newInfo = aMapper.selectById(auctionId);
    int quantity;

    // System.out.println(newInfo.getBidderId());

    Date today = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(today);

    int sellerId = uMapper.selectIdByName(newInfo.getSellerName());
    int itemId = iMapper.selectItemIdByName(newInfo.getItemName());

    if (newInfo.getBidderId() != 0) {
      int bidderMoney = uMapper.selectMoneyById(newInfo.getBidderId());
      int sellerMoney = uMapper.selectMoneyById(uMapper.selectIdByName(newInfo.getSellerName()));
      if (bidderMoney >= newInfo.getMaxBid()) {
        awMapper.insertAward(newInfo.getBidderId(), itemId);
        String name = uMapper.selectNameById(newInfo.getBidderId());
        int bidderCurrent = bidderMoney - newInfo.getMaxBid();
        uMapper.updMoney(bidderCurrent, name);
        int sellerCurrent = sellerMoney + newInfo.getMaxBid();
        uMapper.updMoney(sellerCurrent, newInfo.getSellerName());
        quantity = bMapper.selectQuantityOfItem(newInfo.getBidderId(), itemId);
        bMapper.updQuantity(newInfo.getBidderId(), itemId, quantity + 1);
        rMapper.insertResult(sellerId, itemId, "成功", date);
      } else {
        // System.out.println("NO MONEY");
        quantity = bMapper.selectQuantityOfItem(sellerId, itemId);
        bMapper.updQuantity(sellerId, itemId, quantity + 1);
        rMapper.insertResult(sellerId, itemId, "失敗", date);
      }
    } else {
      // System.out.println("NO BIDDER");
      quantity = bMapper.selectQuantityOfItem(sellerId, itemId);
      bMapper.updQuantity(sellerId, itemId, quantity + 1);
      rMapper.insertResult(sellerId, itemId, "失敗", date);
    }
    aService.syncItemSold(auctionId);
    ArrayList<AuctionInfo> aInfos = aService.syncShowAuctionInfos();
    model.addAttribute("auctionInfos", aInfos);
    return "auction.html";
  }

  @PostMapping("/bid/insert")
  public String insert(@RequestParam Integer bid, @RequestParam Integer auctionId, ModelMap model, Principal prin) {
    int userId = uMapper.selectIdByName(prin.getName());
    model.addAttribute("userId", userId);
    AuctionInfo newInfo = aMapper.selectById(auctionId);

    if (newInfo.getMaxBid() < bid) {
      aService.syncChangeWinner(auctionId, bid, userId);
    }

    newInfo = aMapper.selectById(auctionId);
    model.addAttribute("auctionInfo", newInfo);
    model.addAttribute("bidder", prin.getName());
    return "bid.html";
  }

  @GetMapping("/bid/async")
  public SseEmitter asyncProcess() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.aService.asyncShowAuctionInfos(sseEmitter);
    return sseEmitter;
  }

  @GetMapping("/sell")
  public String sell(Principal prin, ModelMap model) {
    int userId = uMapper.selectIdByName(prin.getName());
    ArrayList<Items> items = iMapper.selectItems(userId);
    model.addAttribute("items", items);
    DateInit date = new DateInit();
    model.addAttribute("today", date.getTomorrowDate());
    boolean flag = true;
    if (iMapper.countItems(userId) == 0) {
      flag = false;
    }
    model.addAttribute("possible", flag);
    return "sell.html";
  }

  @PostMapping("/selling")
  public String selling(ModelMap model, Principal prin, @RequestParam Integer itemId, @RequestParam String dueDate) {
    int sellerId = uMapper.selectIdByName(prin.getName());
    aService.syncSellItem(sellerId, itemId, dueDate);
    int quantity = bMapper.selectQuantityOfItem(sellerId, itemId);
    bMapper.updQuantity(sellerId, itemId, quantity - 1);
    ArrayList<AuctionInfo> auctionInfos = aService.syncShowAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);
    return "auction.html";
  }

}
