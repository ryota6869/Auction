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
import org.springframework.web.bind.annotation.RequestParam;
import oit.is.uno.auction.model.UsersMapper;
import oit.is.uno.auction.service.AsyncAuctionService;
import oit.is.uno.auction.model.AuctionMapper;
import oit.is.uno.auction.model.AwardsMapper;
import oit.is.uno.auction.model.ItemMapper;
import oit.is.uno.auction.model.AuctionInfo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
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
  AsyncAuctionService aService;

  @GetMapping("/home")
  public String home() {
    return "home.html";
  }

  @GetMapping("/forgot")
  public String forgot() {
    return "forgot.html";
  }

  @PostMapping("/forgot/pass")
  public String forgot(@RequestParam String name, ModelMap model) {
    String find;
    find = uMapper.selectPassByName(name);
    model.addAttribute("findPass", find);
    return "forgot.html";
  }

  @GetMapping("/auction")
  public String auction(ModelMap model, Principal prin) {
    ArrayList<AuctionInfo> auctionInfos = aService.syncShowAuctionInfos();

    Date today = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(today);

    Date sqlToday = Date.valueOf(date);
    for (AuctionInfo aInfo : auctionInfos) {
      Date sqlDate = Date.valueOf(aInfo.getDate());
      if (sqlDate.before(sqlToday) || sqlToday.compareTo(sqlDate) == 0) {
        int itemId = iMapper.selectItemIdByName(aInfo.getItemName());
        if (aInfo.getBidderId() != 0) {
          awMapper.insertAward(aInfo.getBidderId(), itemId);
        }
        aService.syncItemSold(aInfo.getId());
      }
    }

    auctionInfos = aService.syncShowAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);

    return "auction.html";
  }

  @GetMapping("/auction/bid")
  public String bid(@RequestParam Integer auctionId, ModelMap model, Principal prin) {

    AuctionInfo auctionInfo = aMapper.selectById(auctionId);

    model.addAttribute("auctionInfo", auctionInfo);
    return "bid.html";
  }

  @PostMapping("/auction/bid/insert")
  public String insert(@RequestParam Integer bid, @RequestParam String role, @RequestParam Integer auctionId,
      ModelMap model, Principal prin) {
    int userId = uMapper.selectIdByName(prin.getName());
    model.addAttribute("userId", userId);
    AuctionInfo newInfo = aMapper.selectById(auctionId);

    if (newInfo.getMaxBid() < bid) {
      aService.syncChangeWinner(auctionId, bid, userId);
    }

    // Debug: teacherで即時に落札するための処理（落札処理の確認）
    if (role.equals("admin")) {
      int itemId = iMapper.selectItemIdByName(newInfo.getItemName());
      awMapper.insertAward(uMapper.selectIdByName("teacher"), itemId);
      aService.syncItemSold(newInfo.getId());
      ArrayList<AuctionInfo> auctionInfos = aService.syncShowAuctionInfos();
      model.addAttribute("auctionInfos", auctionInfos);
      return "auction.html";
    }
    // ここまで

    newInfo = aMapper.selectById(auctionId);
    model.addAttribute("auctionInfo", newInfo);
    return "bid.html";
  }

  @GetMapping("auction/bid/async")
  public SseEmitter asyncProcess() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.aService.asyncShowAuctionInfos(sseEmitter);
    return sseEmitter;
  }
}
