package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by wulei on 2016/2/18.
 */
public class AllowanceBean {

  /**
   * count : 36
   * next : http://api.xiaolumeimei.com/rest/v1/pmt/carrylog/get_clk_list?page=2
   * previous : null
   * results : [{"id":393273,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-08-25","created":"2015-08-26T09:56:55"},{"id":310473,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-07-07","created":"2015-07-08T04:38:31"},{"id":299184,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-07-06","created":"2015-07-07T04:37:44"},{"id":276493,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-07-04","created":"2015-07-05T04:38:09"},{"id":264314,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-07-03","created":"2015-07-04T04:38:44"},{"id":240098,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-07-01","created":"2015-07-02T04:37:34"},{"id":229733,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-06-30","created":"2015-07-01T04:36:45"},{"id":219645,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-06-29","created":"2015-06-30T04:36:39"},{"id":208851,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-06-27","created":"2015-06-28T04:42:41"},{"id":200031,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-06-26","created":"2015-06-27T04:41:45"}]
   */

  private int count;
  private String next;
  private Object previous;
  /**
   * id : 393273
   * carry_type : in
   * xlmm : 44
   * value_money : 0.0
   * carry_type_name : 收入
   * log_type : click
   * carry_date : 2015-08-25
   * created : 2015-08-26T09:56:55
   */

  @SerializedName("results") private List<AllowanceEntity> allowances;

  public void setCount(int count) {
    this.count = count;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public void setPrevious(Object previous) {
    this.previous = previous;
  }

  public void setAllowances(List<AllowanceEntity> allowances) {
    this.allowances = allowances;
  }

  public int getCount() {
    return count;
  }

  public String getNext() {
    return next;
  }

  public Object getPrevious() {
    return previous;
  }

  public List<AllowanceEntity> getAllowances() {
    return allowances;
  }

  public static class AllowanceEntity {
    private int id;
    private String carry_type;
    private int xlmm;
    private double value_money;
    private String carry_type_name;
    private String log_type;
    private String carry_date;
    private String created;
    private double mDaylyInAmount;
    private double mDaylyClkAmount;

    public void setId(int id) {
      this.id = id;
    }

    public void setCarry_type(String carry_type) {
      this.carry_type = carry_type;
    }

    public void setXlmm(int xlmm) {
      this.xlmm = xlmm;
    }

    public void setValue_money(double value_money) {
      this.value_money = value_money;
    }

    public void setCarry_type_name(String carry_type_name) {
      this.carry_type_name = carry_type_name;
    }

    public void setLog_type(String log_type) {
      this.log_type = log_type;
    }

    public void setCarry_date(String carry_date) {
      this.carry_date = carry_date;
    }

    public void setCreated(String created) {
      this.created = created;
    }

    public void setDaylyInAmount(double daylyInAmount) {
      this.mDaylyInAmount = daylyInAmount;
    }

    public void setDaylyClkAmount(double daylyClkAmount ) {
      this.mDaylyClkAmount = daylyClkAmount;
    }

    public int getId() {
      return id;
    }

    public String getCarry_type() {
      return carry_type;
    }

    public int getXlmm() {
      return xlmm;
    }

    public double getValue_money() {
      return value_money;
    }

    public String getCarry_type_name() {
      return carry_type_name;
    }

    public String getLog_type() {
      return log_type;
    }

    public String getCarry_date() {
      return carry_date;
    }

    public String getCreated() {
      return created;
    }

    public double getDaylyInAmount() {return mDaylyInAmount;}

    public double getDaylyClkAmount() {return mDaylyClkAmount;}
  }
}
