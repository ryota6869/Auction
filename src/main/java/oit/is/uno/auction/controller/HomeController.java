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
import java.util.ArrayList;
import java.security.Principal;

@Controller
public class HomeController {
  @Autowired
  UsersMapper uMapper;

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

  @GetMapping("history")
  public String history(ModelMap model, Principal prin) {
    int bidderId = uMapper.selectIdByName(prin.getName());
    ArrayList<Awards> awards = awMapper.selectAwardsByBidderId(bidderId);
    model.addAttribute("awards", awards);
    return "history.html";
  }
}
