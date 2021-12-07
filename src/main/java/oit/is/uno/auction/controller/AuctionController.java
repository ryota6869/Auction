package oit.is.uno.auction.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import oit.is.uno.auction.model.UsersMapper;
import oit.is.uno.auction.model.AuctionMapper;
import oit.is.uno.auction.model.AuctionInfo;

@Controller
public class AuctionController {

  @Autowired
  UsersMapper uMapper;

  @Autowired
  AuctionMapper aMapper;

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
  public String auction(ModelMap model) {
    ArrayList<AuctionInfo> auctionInfos = aMapper.selectAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);

    Date today = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(today);

    for (AuctionInfo aInfo : auctionInfos) {
      if (aInfo.getDate().equals(date)) {
        aMapper.deleteById(aInfo.getId());
      } else if (Integer.parseInt(aInfo.getDate().substring(0, 3)) <= Integer.parseInt(date.substring(0, 3))) {
        if (Integer.parseInt(aInfo.getDate().substring(5, 6)) <= Integer.parseInt(date.substring(5, 6))) {
          if (Integer.parseInt(aInfo.getDate().substring(8)) <= Integer.parseInt(date.substring(8))) {
            aMapper.deleteById(aInfo.getId());
          }
        }
      }
    }

    return "auction.html";
  }

  @PostMapping("/auction/bid")
  public String bid(@RequestParam Integer bid, @RequestParam Integer id, ModelMap model) {
    AuctionInfo newInfo = aMapper.selectById(id);
    if (newInfo.getMaxBid() < bid) {
      aMapper.updateMaxbidById(bid, id);
    }
    ArrayList<AuctionInfo> auctionInfos = aMapper.selectAuctionInfos();
    model.addAttribute("auctionInfos", auctionInfos);
    return "auction.html";
  }
}
