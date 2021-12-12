package oit.is.uno.auction.model;

public class AuctionInfo {
  int id;
  int maxBid;
  String date;
  String itemName;
  String sellerName;
  int bidderId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getMaxBid() {
    return maxBid;
  }

  public void setMaxBid(int maxBid) {
    this.maxBid = maxBid;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public int getBidderId() {
    return bidderId;
  }

  public void setBidderId(int bidderId) {
    this.bidderId = bidderId;
  }
}
