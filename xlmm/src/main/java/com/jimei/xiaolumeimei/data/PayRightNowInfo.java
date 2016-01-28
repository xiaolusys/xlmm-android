package com.jimei.xiaolumeimei.data;

/**
 * Created by lei on 16/1/28.
 */
public class PayRightNowInfo {
  int item_id;
  int sku_id;
  int num;
  int addr_id;
  int channel;
  double payment;
  double post_fee;
  double discount_fee;
  double total_fee;
  int uuid;

  public int getItem_id() {
    return item_id;
  }

  public int getSku_id() {
    return sku_id;
  }

  public int getNum() {
    return num;
  }

  public int getAddr_id() {
    return addr_id;
  }

  public int getChannel() {
    return channel;
  }

  public double getPayment() {
    return payment;
  }

  public double getPost_fee() {
    return post_fee;
  }

  public double getDiscount_fee() {
    return discount_fee;
  }

  public double getTotal_fee() {
    return total_fee;
  }

  public int getUuid() {
    return uuid;
  }

  public void setItem_id(int item_id) {
    this.item_id = item_id;
  }

  public void setSku_id(int sku_id) {
    this.sku_id = sku_id;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public void setAddr_id(int addr_id) {
    this.addr_id = addr_id;
  }

  public void setChannel(int channel) {
    this.channel = channel;
  }

  public void setPayment(double payment) {
    this.payment = payment;
  }

  public void setPost_fee(double post_fee) {
    this.post_fee = post_fee;
  }

  public void setDiscount_fee(double discount_fee) {
    this.discount_fee = discount_fee;
  }

  public void setTotal_fee(double total_fee) {
    this.total_fee = total_fee;
  }

  public void setUuid(int uuid) {
    this.uuid = uuid;
  }
}
