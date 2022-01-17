package oit.is.uno.auction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import oit.is.uno.auction.model.UsersMapper;
import oit.is.uno.auction.model.AwardsMapper;
import oit.is.uno.auction.model.Awards;
import oit.is.uno.auction.model.ResultMapper;
import oit.is.uno.auction.model.Results;
import java.util.ArrayList;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Controller
public class HomeController {
  @Autowired
  UsersMapper uMapper;

  @Autowired
  AwardsMapper awMapper;

  @Autowired
  ResultMapper rMapper;

  @GetMapping("/home")
  public String home(Principal prin, ModelMap model) {
    String cust = prin.getName();
    int bonus = 10000;

    Date today = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(today);

    if (uMapper.selectLoginByName(cust).equals(date)) {
      uMapper.updLastLogin(date, cust);
    } else {
      model.addAttribute("bonus", bonus);
      uMapper.updLastLogin(date, cust);
      int current = uMapper.selectMoneyById(uMapper.selectIdByName(cust));
      uMapper.updMoney(bonus + current, cust);
    }
    int userId = uMapper.selectIdByName(cust);
    int money = uMapper.selectMoneyById(userId);
    model.addAttribute("money", money);

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

  @GetMapping("history")
  public String history(ModelMap model, Principal prin) {
    int bidderId = uMapper.selectIdByName(prin.getName());
    ArrayList<Awards> awards = awMapper.selectAwardsByBidderId(bidderId);
    model.addAttribute("awards", awards);
    return "history.html";
  }

  @GetMapping("result")
  public String result(ModelMap model, Principal prin) {
    int sellerId = uMapper.selectIdByName(prin.getName());
    ArrayList<Results> results = rMapper.selectResultsBySellerId(sellerId);
    model.addAttribute("results", results);
    return "result.html";
  }
}
