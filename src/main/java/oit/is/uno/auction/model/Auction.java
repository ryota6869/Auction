package oit.is.uno.auction.model;

public class Auction {
  int id;
  int itemId;
  int sellerId;
  int maxBid;
  String date;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getSellerId() {
    return sellerId;
  }

  public void setSellerId(int sellerId) {
    this.sellerId = sellerId;
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

}
