package oit.is.uno.auction.controller;

import java.util.ArrayList;

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
    return "auction.html";
  }
}
