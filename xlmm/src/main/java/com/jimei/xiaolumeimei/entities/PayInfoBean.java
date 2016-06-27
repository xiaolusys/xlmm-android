package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/28.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class PayInfoBean {

  /**
   * info : 支付成功
   * trade : {"id":45555,"tid":"xd2016xxx","channel":"wx"}
   * charge : {"order_no":"xd160623576b3efc3a519","extra":{"open_id":"our5huIOSuFF5FdojFMFNP5HNOmA"},"app":"app_LOOajDn9u9WDjfHa","livemode":true,"currency":"cny","time_settle":null,"time_expire":1466653471,"id":"ch_WfbbTKyTKmT44iTmXLyLGKSK","subject":"小鹿美美平台交易","failure_msg":null,"channel":"wx_pub","metadata":{"color":"red"},"body":"订单ID(366559),订单金额(69.90)","credential":{"object":"credential","wx_pub":{"package":"prepay_id=wx201606230944313536d8cf380312696196","timeStamp":"1466646271","signType":"MD5","paySign":"3D1C88384E5F1420A7EFC432B3928053","appId":"wx3f91056a2928ad2d","nonceStr":"3b9fcceda9eed71e5913806f60935792"}},"client_ip":"121.199.168.159","description":null,"amount_refunded":0,"refunded":false,"object":"charge","paid":false,"amount_settle":6948,"time_paid":null,"failure_code":null,"refunds":{"url":"/v1/charges/ch_WfbbTKyTKmT44iTmXLyLGKSK/refunds","has_more":false,"object":"list","data":[]},"created":1466646271,"transaction_no":null,"amount":6990}
   * code : 0
   * channel : wx_pub
   */

  private String info;
  /**
   * id : 45555
   * tid : xd2016xxx
   * channel : wx
   */

  private TradeBean trade;
  /**
   * order_no : xd160623576b3efc3a519
   * extra : {"open_id":"our5huIOSuFF5FdojFMFNP5HNOmA"}
   * app : app_LOOajDn9u9WDjfHa
   * livemode : true
   * currency : cny
   * time_settle : null
   * time_expire : 1466653471
   * id : ch_WfbbTKyTKmT44iTmXLyLGKSK
   * subject : 小鹿美美平台交易
   * failure_msg : null
   * channel : wx_pub
   * metadata : {"color":"red"}
   * body : 订单ID(366559),订单金额(69.90)
   * credential : {"object":"credential","wx_pub":{"package":"prepay_id=wx201606230944313536d8cf380312696196","timeStamp":"1466646271","signType":"MD5","paySign":"3D1C88384E5F1420A7EFC432B3928053","appId":"wx3f91056a2928ad2d","nonceStr":"3b9fcceda9eed71e5913806f60935792"}}
   * client_ip : 121.199.168.159
   * description : null
   * amount_refunded : 0
   * refunded : false
   * object : charge
   * paid : false
   * amount_settle : 6948
   * time_paid : null
   * failure_code : null
   * refunds : {"url":"/v1/charges/ch_WfbbTKyTKmT44iTmXLyLGKSK/refunds","has_more":false,"object":"list","data":[]}
   * created : 1466646271
   * transaction_no : null
   * amount : 6990
   */

  private ChargeBean charge;
  private int code;
  private String channel;

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public TradeBean getTrade() {
    return trade;
  }

  public void setTrade(TradeBean trade) {
    this.trade = trade;
  }

  public ChargeBean getCharge() {
    return charge;
  }

  public void setCharge(ChargeBean charge) {
    this.charge = charge;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public static class TradeBean {
    private int id;
    private String tid;
    private String channel;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getTid() {
      return tid;
    }

    public void setTid(String tid) {
      this.tid = tid;
    }

    public String getChannel() {
      return channel;
    }

    public void setChannel(String channel) {
      this.channel = channel;
    }
  }

  public static class ChargeBean {
    private String order_no;
    /**
     * open_id : our5huIOSuFF5FdojFMFNP5HNOmA
     */

    private ExtraBean extra;
    private String app;
    private boolean livemode;
    private String currency;
    private Object time_settle;
    private int time_expire;
    private String id;
    private String subject;
    private Object failure_msg;
    private String channel;
    /**
     * color : red
     */

    private MetadataBean metadata;
    private String body;
    /**
     * object : credential
     * wx_pub : {"package":"prepay_id=wx201606230944313536d8cf380312696196","timeStamp":"1466646271","signType":"MD5","paySign":"3D1C88384E5F1420A7EFC432B3928053","appId":"wx3f91056a2928ad2d","nonceStr":"3b9fcceda9eed71e5913806f60935792"}
     */

    private CredentialBean credential;
    private String client_ip;
    private Object description;
    private int amount_refunded;
    private boolean refunded;
    private String object;
    private boolean paid;
    private int amount_settle;
    private Object time_paid;
    private Object failure_code;
    /**
     * url : /v1/charges/ch_WfbbTKyTKmT44iTmXLyLGKSK/refunds
     * has_more : false
     * object : list
     * data : []
     */

    private RefundsBean refunds;
    private int created;
    private Object transaction_no;
    private int amount;

    public String getOrder_no() {
      return order_no;
    }

    public void setOrder_no(String order_no) {
      this.order_no = order_no;
    }

    public ExtraBean getExtra() {
      return extra;
    }

    public void setExtra(ExtraBean extra) {
      this.extra = extra;
    }

    public String getApp() {
      return app;
    }

    public void setApp(String app) {
      this.app = app;
    }

    public boolean isLivemode() {
      return livemode;
    }

    public void setLivemode(boolean livemode) {
      this.livemode = livemode;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public Object getTime_settle() {
      return time_settle;
    }

    public void setTime_settle(Object time_settle) {
      this.time_settle = time_settle;
    }

    public int getTime_expire() {
      return time_expire;
    }

    public void setTime_expire(int time_expire) {
      this.time_expire = time_expire;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public Object getFailure_msg() {
      return failure_msg;
    }

    public void setFailure_msg(Object failure_msg) {
      this.failure_msg = failure_msg;
    }

    public String getChannel() {
      return channel;
    }

    public void setChannel(String channel) {
      this.channel = channel;
    }

    public MetadataBean getMetadata() {
      return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
      this.metadata = metadata;
    }

    public String getBody() {
      return body;
    }

    public void setBody(String body) {
      this.body = body;
    }

    public CredentialBean getCredential() {
      return credential;
    }

    public void setCredential(CredentialBean credential) {
      this.credential = credential;
    }

    public String getClient_ip() {
      return client_ip;
    }

    public void setClient_ip(String client_ip) {
      this.client_ip = client_ip;
    }

    public Object getDescription() {
      return description;
    }

    public void setDescription(Object description) {
      this.description = description;
    }

    public int getAmount_refunded() {
      return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
      this.amount_refunded = amount_refunded;
    }

    public boolean isRefunded() {
      return refunded;
    }

    public void setRefunded(boolean refunded) {
      this.refunded = refunded;
    }

    public String getObject() {
      return object;
    }

    public void setObject(String object) {
      this.object = object;
    }

    public boolean isPaid() {
      return paid;
    }

    public void setPaid(boolean paid) {
      this.paid = paid;
    }

    public int getAmount_settle() {
      return amount_settle;
    }

    public void setAmount_settle(int amount_settle) {
      this.amount_settle = amount_settle;
    }

    public Object getTime_paid() {
      return time_paid;
    }

    public void setTime_paid(Object time_paid) {
      this.time_paid = time_paid;
    }

    public Object getFailure_code() {
      return failure_code;
    }

    public void setFailure_code(Object failure_code) {
      this.failure_code = failure_code;
    }

    public RefundsBean getRefunds() {
      return refunds;
    }

    public void setRefunds(RefundsBean refunds) {
      this.refunds = refunds;
    }

    public int getCreated() {
      return created;
    }

    public void setCreated(int created) {
      this.created = created;
    }

    public Object getTransaction_no() {
      return transaction_no;
    }

    public void setTransaction_no(Object transaction_no) {
      this.transaction_no = transaction_no;
    }

    public int getAmount() {
      return amount;
    }

    public void setAmount(int amount) {
      this.amount = amount;
    }

    public static class ExtraBean {
      private String open_id;

      public String getOpen_id() {
        return open_id;
      }

      public void setOpen_id(String open_id) {
        this.open_id = open_id;
      }
    }

    public static class MetadataBean {
      private String color;

      public String getColor() {
        return color;
      }

      public void setColor(String color) {
        this.color = color;
      }
    }

    public static class CredentialBean {
      private String object;
      /**
       * package : prepay_id=wx201606230944313536d8cf380312696196
       * timeStamp : 1466646271
       * signType : MD5
       * paySign : 3D1C88384E5F1420A7EFC432B3928053
       * appId : wx3f91056a2928ad2d
       * nonceStr : 3b9fcceda9eed71e5913806f60935792
       */

      private WxPubBean wx_pub;

      public String getObject() {
        return object;
      }

      public void setObject(String object) {
        this.object = object;
      }

      public WxPubBean getWx_pub() {
        return wx_pub;
      }

      public void setWx_pub(WxPubBean wx_pub) {
        this.wx_pub = wx_pub;
      }

      public static class WxPubBean {
        @SerializedName("package")
        private String packageX;
        private String timeStamp;
        private String signType;
        private String paySign;
        private String appId;
        private String nonceStr;

        public String getPackageX() {
          return packageX;
        }

        public void setPackageX(String packageX) {
          this.packageX = packageX;
        }

        public String getTimeStamp() {
          return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
          this.timeStamp = timeStamp;
        }

        public String getSignType() {
          return signType;
        }

        public void setSignType(String signType) {
          this.signType = signType;
        }

        public String getPaySign() {
          return paySign;
        }

        public void setPaySign(String paySign) {
          this.paySign = paySign;
        }

        public String getAppId() {
          return appId;
        }

        public void setAppId(String appId) {
          this.appId = appId;
        }

        public String getNonceStr() {
          return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
          this.nonceStr = nonceStr;
        }
      }
    }

    public static class RefundsBean {
      private String url;
      private boolean has_more;
      private String object;
      private List<?> data;

      public String getUrl() {
        return url;
      }

      public void setUrl(String url) {
        this.url = url;
      }

      public boolean isHas_more() {
        return has_more;
      }

      public void setHas_more(boolean has_more) {
        this.has_more = has_more;
      }

      public String getObject() {
        return object;
      }

      public void setObject(String object) {
        this.object = object;
      }

      public List<?> getData() {
        return data;
      }

      public void setData(List<?> data) {
        this.data = data;
      }
    }
  }
}
