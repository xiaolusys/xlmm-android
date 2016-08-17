package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/2/4.
 */
public class WithdrawCashHisBean {

  /**
   * count : 13
   * next : http://dev.xiaolumeimei.com/rest/v1/pmt/cashout?page=2
   * previous : null
   * results : [{"id":49,"xlmm":1,"value_money":190,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T20:16:15"},{"id":48,"xlmm":1,"value_money":190,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T20:02:35"},{"id":46,"xlmm":1,"value_money":190,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T19:57:45"},{"id":44,"xlmm":1,"value_money":180,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T18:32:20"},{"id":45,"xlmm":1,"value_money":200,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T18:32:42"},{"id":47,"xlmm":1,"value_money":22,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T19:59:47"},{"id":43,"xlmm":1,"value_money":200,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T18:30:41"},{"id":42,"xlmm":1,"value_money":20,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T18:30:19"},{"id":41,"xlmm":1,"value_money":190,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T18:26:57"},{"id":40,"xlmm":1,"value_money":22,"get_status_display":"已拒绝","status":"rejected","created":"2015-07-07T18:26:07"}]
   */

  private int count;
  private String next;
  private String previous;
  /**
   * id : 49
   * xlmm : 1
   * value_money : 190.0
   * get_status_display : 已拒绝
   * status : rejected
   * created : 2015-07-07T20:16:15
   */

  private List<WithdrawCashRecord> results;

  public void setCount(int count) {
    this.count = count;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public void setPrevious(String previous) {
    this.previous = previous;
  }

  public void setResults(List<WithdrawCashRecord> results) {
    this.results = results;
  }

  public int getCount() {
    return count;
  }

  public String getNext() {
    return next;
  }

  public String getPrevious() {
    return previous;
  }

  public List<WithdrawCashRecord> getResults() {
    return results;
  }

  public static class WithdrawCashRecord {
    private int id;
    private int xlmm;
    private double value_money;
    private String get_status_display;
    private String status;
    private String created;

    public void setId(int id) {
      this.id = id;
    }

    public void setXlmm(int xlmm) {
      this.xlmm = xlmm;
    }

    public void setValue_money(double value_money) {
      this.value_money = value_money;
    }

    public void setGet_status_display(String get_status_display) {
      this.get_status_display = get_status_display;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public void setCreated(String created) {
      this.created = created;
    }

    public int getId() {
      return id;
    }

    public int getXlmm() {
      return xlmm;
    }

    public double getValue_money() {
      return value_money;
    }

    public String getGet_status_display() {
      return get_status_display;
    }

    public String getStatus() {
      return status;
    }

    public String getCreated() {
      return created;
    }
  }
}
