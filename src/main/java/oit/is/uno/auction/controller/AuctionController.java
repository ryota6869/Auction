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
import oit.is.uno.auction.model.AuctionMapper;
import oit.is.uno.auction.model.AwardsMapper;
import oit.is.uno.auction.model.ItemMapper;
import oit.is.uno.auction.model.AuctionInfo;

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
    ArrayList<AuctionInfo> auctionInfos = aMapper.selectAuctionInfos();

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
        aMapper.deleteById(aInfo.getId());
      }
    }

    int userId = uMapper.selectIdByName(prin.getName());

    auctionInfos = aMapper.selectAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);
    model.addAttribute("userId", userId);

    return "auction.html";
  }

  @PostMapping("/auction/bid")
  public String bid(@RequestParam Integer bid, @RequestParam Integer auctionId, @RequestParam Integer userId,
      ModelMap model) {
    AuctionInfo newInfo = aMapper.selectById(auctionId);
    if (newInfo.getMaxBid() < bid) {
      aMapper.updateMaxbidById(bid, auctionId);
      aMapper.updateUserIdById(userId);
    }
    ArrayList<AuctionInfo> auctionInfos = aMapper.selectAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);
    return "auction.html";
  }
}
