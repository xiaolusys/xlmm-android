package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AllOdersBean {

  @Override public String toString() {
    return "AllOdersBean{" +
        "mCount=" + mCount +
        ", mNext='" + mNext + '\'' +
        ", mPrevious=" + mPrevious +
        ", mResults=" + mResults +
        '}';
  }

  /**
   * count : 137
   * next : http://api.xiaolumeimei.com/rest/v1/trades.json?page=2
   * previous : null
   * results : [{"id":275635,"url":"http://api.xiaolumeimei.com/rest/v1/trades/275635.json","orders":[{"id":302107,"oid":"xo16011156933d7f55da7","item_id":"30786","title":"韩版百搭皮裙/黑色","sku_id":"120694","num":1,"outer_id":"821277480051","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"XL","pic_path":"http://image.xiaolu.so/MG-1452155820775-韩版百搭皮裙3.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd16011156933d7736d11","buyer_nick":"meron-da","buyer_id":1,"channel":"wx_pub","payment":49.9,"post_fee":0,"total_fee":49.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1452155820775-韩版百搭皮裙3.png","buyer_message":"","trade_type":0,"created":"2016-01-11T13:28:31","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":270439,"url":"http://api.xiaolumeimei.com/rest/v1/trades/270439.json","orders":[{"id":296361,"oid":"xo1601035689318904fde","item_id":"22909","title":"字母印花加绒休闲套装/图片色","sku_id":"89693","num":1,"outer_id":"822274320011","total_fee":99.9,"payment":99.9,"discount_fee":0,"sku_name":"L","pic_path":"http://image.xiaolu.so/MG-1449798444842-weqwwwwweq.jpg","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd16010356893185a3e52","buyer_nick":"meron-da","buyer_id":1,"channel":"wx_pub","payment":99.9,"post_fee":0,"total_fee":99.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1449798444842-weqwwwwweq.jpg","buyer_message":"","trade_type":0,"created":"2016-01-03T22:34:49","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":270426,"url":"http://api.xiaolumeimei.com/rest/v1/trades/270426.json","orders":[{"id":296347,"oid":"xo160103568925b3ac285","item_id":"18003","title":"秒杀  字母印花针织连衣裙/红色","sku_id":"70308","num":1,"outer_id":"819254000014","total_fee":39.9,"payment":39.9,"discount_fee":0,"sku_name":"均码","pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvADR3DUV0O1x6wcEOmlfrWpR6Ibhgrk49Ujz31Kl9nk8ia357eKaTI45FtSTeGqZqeco2gFbNokOQ/0?wx_fmt=png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":true}],"tid":"xd160103568925b02e09f","buyer_nick":"meron-da","buyer_id":1,"channel":"wx_pub","payment":39.9,"post_fee":0,"total_fee":39.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvADR3DUV0O1x6wcEOmlfrWpR6Ibhgrk49Ujz31Kl9nk8ia357eKaTI45FtSTeGqZqeco2gFbNokOQ/0?wx_fmt=png","buyer_message":"","trade_type":0,"created":"2016-01-03T21:44:19","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":260832,"url":"http://api.xiaolumeimei.com/rest/v1/trades/260832.json","orders":[{"id":284574,"oid":"xo151224567bb6a7384dd","item_id":"23446","title":"加绒弹力踩脚打底裤/黑色","sku_id":"91858","num":1,"outer_id":"821257080011","total_fee":39.9,"payment":39.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450762916853-加绒弹力踩脚打底裤02.jpg","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd151224567bb69ca3b2a","buyer_nick":"meron-da","buyer_id":1,"channel":"wx_pub","payment":39.9,"post_fee":0,"total_fee":39.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450762916853-加绒弹力踩脚打底裤02.jpg","buyer_message":"","trade_type":0,"created":"2015-12-24T17:11:03","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":259311,"url":"http://api.xiaolumeimei.com/rest/v1/trades/259311.json","orders":[{"id":282666,"oid":"xo151223567a745691623","item_id":"29380","title":"气质显瘦大毛领连帽羽绒服/粉色","sku_id":"115128","num":1,"outer_id":"818269900012","total_fee":269.9,"payment":269.9,"discount_fee":0,"sku_name":"XL","pic_path":"http://image.xiaolu.so/MG-1450683204400-气质显瘦大毛领连帽羽绒服04.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd151223567a745100001","buyer_nick":"meron-da","buyer_id":1,"channel":"alipay_wap","payment":269.9,"post_fee":0,"total_fee":269.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450683204400-气质显瘦大毛领连帽羽绒服04.png","buyer_message":"","trade_type":0,"created":"2015-12-23T18:15:50","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":259277,"url":"http://api.xiaolumeimei.com/rest/v1/trades/259277.json","orders":[{"id":282632,"oid":"xo151223567a670ac4016","item_id":"29340","title":"百搭竖纹麻花针织套头毛衣/灰色","sku_id":"114934","num":1,"outer_id":"820250520013","total_fee":59.9,"payment":59.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450683085804-百搭竖纹麻花针织套头毛衣01.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd151223567a670631c10","buyer_nick":"meron-da","buyer_id":1,"channel":"wx_pub","payment":59.9,"post_fee":0,"total_fee":59.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450683085804-百搭竖纹麻花针织套头毛衣01.png","buyer_message":"","trade_type":0,"created":"2015-12-23T17:19:06","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":259217,"url":"http://api.xiaolumeimei.com/rest/v1/trades/259217.json","orders":[{"id":282552,"oid":"xo151223567a50188cc0d","item_id":"29323","title":"高领蕾丝拼接袖时尚毛衣##/深紫色","sku_id":"114891","num":1,"outer_id":"820239730014","total_fee":79.9,"payment":79.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450683040793-高领蕾丝拼接袖时尚毛衣04.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd151223567a5014411e4","buyer_nick":"meron-da","buyer_id":1,"channel":"alipay_wap","payment":79.9,"post_fee":0,"total_fee":79.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450683040793-高领蕾丝拼接袖时尚毛衣04.png","buyer_message":"","trade_type":0,"created":"2015-12-23T15:41:12","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":258966,"url":"http://api.xiaolumeimei.com/rest/v1/trades/258966.json","orders":[{"id":282272,"oid":"xo151223567a11c4cc571","item_id":"29158","title":"千鸟格流苏披肩围巾/绿色","sku_id":"114197","num":1,"outer_id":"824281700113","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450512930491-千鸟格流苏披肩围巾02.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd151223567a11bc4b413","buyer_nick":"meron-da","buyer_id":1,"channel":"alipay_wap","payment":49.9,"post_fee":0,"total_fee":49.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450512930491-千鸟格流苏披肩围巾02.png","buyer_message":"","trade_type":0,"created":"2015-12-23T11:15:16","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":258838,"url":"http://api.xiaolumeimei.com/rest/v1/trades/258838.json","orders":[{"id":282134,"oid":"xo1512225679470ed2fee","item_id":"20865","title":"V领弹力修身毛衣/白色","sku_id":"81700","num":1,"outer_id":"820266180011","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450508144347-V领弹力修身毛衣02.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd15122256794707aa73d","buyer_nick":"meron-da","buyer_id":1,"channel":"alipay_wap","payment":49.9,"post_fee":0,"total_fee":49.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450508144347-V领弹力修身毛衣02.png","buyer_message":"","trade_type":0,"created":"2015-12-22T20:50:22","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":258833,"url":"http://api.xiaolumeimei.com/rest/v1/trades/258833.json","orders":[{"id":282129,"oid":"xo151222567945aba1535","item_id":"20865","title":"V领弹力修身毛衣/白色","sku_id":"81700","num":1,"outer_id":"820266180011","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450508144347-V领弹力修身毛衣02.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd1512225679459b3fda7","buyer_nick":"meron-da","buyer_id":1,"channel":"alipay_wap","payment":49.9,"post_fee":0,"total_fee":49.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450508144347-V领弹力修身毛衣02.png","buyer_message":"","trade_type":0,"created":"2015-12-22T20:44:27","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":258831,"url":"http://api.xiaolumeimei.com/rest/v1/trades/258831.json","orders":[{"id":282127,"oid":"xo15122256794565dedb4","item_id":"20865","title":"V领弹力修身毛衣/白色","sku_id":"81700","num":1,"outer_id":"820266180011","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"均码","pic_path":"http://image.xiaolu.so/MG-1450508144347-V领弹力修身毛衣02.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd1512225679450b90c28","buyer_nick":"meron-da","buyer_id":1,"channel":"alipay_wap","payment":49.9,"post_fee":0,"total_fee":49.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"http://image.xiaolu.so/MG-1450508144347-V领弹力修身毛衣02.png","buyer_message":"","trade_type":0,"created":"2015-12-22T20:43:17","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":251070,"url":"http://api.xiaolumeimei.com/rest/v1/trades/251070.json","orders":[{"id":272894,"oid":"xo151212566b99ef74c00","item_id":"15250","title":"婴儿全棉印花床品十件套/蓝色","sku_id":"59356","num":1,"outer_id":"9170167004","total_fee":319.9,"payment":319.9,"discount_fee":0,"sku_name":"130*70","pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvCdm4DUNGZUibvibEhAKB7CstRZWiayFr2KQatm59icr7J6pb3ddD8P1a5SQxnhudicbB6lJLmZ8ziaOhw/0?wx_fmt=jpeg","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}],"tid":"xd151212566b99eb1425b","buyer_nick":"meron-da","buyer_id":1,"channel":"wx_pub","payment":319.9,"post_fee":0,"total_fee":319.9,"discount_fee":0,"status":7,"status_display":"交易关闭","order_pic":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvCdm4DUNGZUibvibEhAKB7CstRZWiayFr2KQatm59icr7J6pb3ddD8P1a5SQxnhudicbB6lJLmZ8ziaOhw/0?wx_fmt=jpeg","buyer_message":"","trade_type":0,"created":"2015-12-12T11:52:15","pay_time":null,"consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":251062,"url":"http://api.xiaolumeimei.com/rest/v1/trades/251062.json","orders":[{"id":272873,"oid":"xo151212566b995a1ac97","item_id":"20230","title":"恒温空气棉睡袋/兔兔果园","sku_id":"79232","num":1,"outer_id":"915246280013","total_fee":49.9,"payment":38.01,"discount_fee":11.89,"sku_name":"90","pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvbiaOATN6ddNibSFjZgauqibiczV2LskmNSFiaZiaJCoqxgfs3vDu157Fedibxuf5CG0s1Brzgria0bQeaXg/0?wx_fmt=png","status":6,"status_display":"退款关闭","refund_status":7,"refund_status_display":"退款成功","refund_id":32704,"kill_title":false},{"id":272874,"oid":"xo151212566b995a22cbc","item_id":"28154","title":"大毛领中长款毛呢外套/绿色","sku_id":"110295","num":1,"outer_id":"818285680013","total_fee":159.9,"payment":121.79,"discount_fee":38.11,"sku_name":"XXL","pic_path":"http://image.xiaolu.so/MG-1449714330497-大毛领中长款毛呢外套03.jpg","status":6,"status_display":"退款关闭","refund_status":7,"refund_status_display":"退款成功","refund_id":32705,"kill_title":false}],"tid":"xd151212566b99400c446","buyer_nick":"meron-da","buyer_id":1,"channel":"wallet","payment":159.8,"post_fee":0,"total_fee":209.8,"discount_fee":50,"status":6,"status_display":"退款关闭","order_pic":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvbiaOATN6ddNibSFjZgauqibiczV2LskmNSFiaZiaJCoqxgfs3vDu157Fedibxuf5CG0s1Brzgria0bQeaXg/0?wx_fmt=png","buyer_message":"","trade_type":0,"created":"2015-12-12T11:49:46","pay_time":"2015-12-12T11:49:46","consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":251019,"url":"http://api.xiaolumeimei.com/rest/v1/trades/251019.json","orders":[{"id":272799,"oid":"xo151212566b9646502d6","item_id":"20083","title":"调整型无痕美背文胸/蓝色","sku_id":"78672","num":1,"outer_id":"820241940013","total_fee":69.9,"payment":69.9,"discount_fee":0,"sku_name":"85C","pic_path":"http://image.xiaolu.so/MG-1449719033474-调整型无痕美背文胸 03.png","status":6,"status_display":"退款关闭","refund_status":7,"refund_status_display":"退款成功","refund_id":32703,"kill_title":false}],"tid":"xd151212566b962e4409b","buyer_nick":"meron-da","buyer_id":1,"channel":"wallet","payment":69.9,"post_fee":0,"total_fee":69.9,"discount_fee":0,"status":6,"status_display":"退款关闭","order_pic":"http://image.xiaolu.so/MG-1449719033474-调整型无痕美背文胸 03.png","buyer_message":"","trade_type":0,"created":"2015-12-12T11:36:38","pay_time":"2015-12-12T11:36:38","consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""},{"id":239921,"url":"http://api.xiaolumeimei.com/rest/v1/trades/239921.json","orders":[{"id":259207,"oid":"xo151201565d8ee8b3a55","item_id":"27062","title":"蕾丝拼接打底衫/黑色","sku_id":"106154","num":1,"outer_id":"820279670012","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"4XL","pic_path":"http://image.xiaolu.so/MG-1448863068251-2.png","status":6,"status_display":"退款关闭","refund_status":7,"refund_status_display":"退款成功","refund_id":31319,"kill_title":false}],"tid":"xd151201565d8ed99da9b","buyer_nick":"meron-da","buyer_id":1,"channel":"wx","payment":49.9,"post_fee":0,"total_fee":49.9,"discount_fee":0,"status":6,"status_display":"退款关闭","order_pic":"http://image.xiaolu.so/MG-1448863068251-2.png","buyer_message":"","trade_type":0,"created":"2015-12-01T20:13:28","pay_time":"2015-12-01T20:13:39","consign_time":null,"out_sid":"","logistics_company":null,"receiver_name":"梅生","receiver_state":"上海","receiver_city":"徐汇区","receiver_district":"内环以内","receiver_address":"平凉路　988号","receiver_mobile":"18621623915","receiver_phone":""}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private Object mPrevious;
  /**
   * id : 275635
   * url : http://api.xiaolumeimei.com/rest/v1/trades/275635.json
   * orders : [{"id":302107,"oid":"xo16011156933d7f55da7","item_id":"30786","title":"韩版百搭皮裙/黑色","sku_id":"120694","num":1,"outer_id":"821277480051","total_fee":49.9,"payment":49.9,"discount_fee":0,"sku_name":"XL","pic_path":"http://image.xiaolu.so/MG-1452155820775-韩版百搭皮裙3.png","status":7,"status_display":"交易关闭","refund_status":0,"refund_status_display":"没有退款","refund_id":null,"kill_title":false}]
   * tid : xd16011156933d7736d11
   * buyer_nick : meron-da
   * buyer_id : 1
   * channel : wx_pub
   * payment : 49.9
   * post_fee : 0
   * total_fee : 49.9
   * discount_fee : 0
   * status : 7
   * status_display : 交易关闭
   * order_pic : http://image.xiaolu.so/MG-1452155820775-韩版百搭皮裙3.png
   * buyer_message :
   * trade_type : 0
   * created : 2016-01-11T13:28:31
   * pay_time : null
   * consign_time : null
   * out_sid :
   * logistics_company : null
   * receiver_name : 梅生
   * receiver_state : 上海
   * receiver_city : 徐汇区
   * receiver_district : 内环以内
   * receiver_address : 平凉路　988号
   * receiver_mobile : 18621623915
   * receiver_phone :
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public void setCount(int count) {
    this.mCount = count;
  }

  public void setNext(String next) {
    this.mNext = next;
  }

  public void setPrevious(Object previous) {
    this.mPrevious = previous;
  }

  public void setResults(List<ResultsEntity> results) {
    this.mResults = results;
  }

  public int getCount() {
    return mCount;
  }

  public String getNext() {
    return mNext;
  }

  public Object getPrevious() {
    return mPrevious;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public static class ResultsEntity {
    @SerializedName("id") private int mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("tid") private String mTid;
    @SerializedName("buyer_nick") private String mBuyerNick;
    @SerializedName("buyer_id") private int mBuyerId;
    @SerializedName("channel") private String mChannel;
    @SerializedName("payment") private double mPayment;
    @SerializedName("post_fee") private int mPostFee;
    @SerializedName("total_fee") private double mTotalFee;
    @SerializedName("discount_fee") private int mDiscountFee;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("order_pic") private String mOrderPic;
    @SerializedName("buyer_message") private String mBuyerMessage;
    @SerializedName("trade_type") private int mTradeType;
    @SerializedName("created") private String mCreated;
    @SerializedName("pay_time") private Object mPayTime;
    @SerializedName("consign_time") private Object mConsignTime;
    @SerializedName("out_sid") private String mOutSid;
    @SerializedName("logistics_company") private Object mLogisticsCompany;
    @SerializedName("receiver_name") private String mReceiverName;
    @SerializedName("receiver_state") private String mReceiverState;
    @SerializedName("receiver_city") private String mReceiverCity;
    @SerializedName("receiver_district") private String mReceiverDistrict;
    @SerializedName("receiver_address") private String mReceiverAddress;
    @SerializedName("receiver_mobile") private String mReceiverMobile;
    @SerializedName("receiver_phone") private String mReceiverPhone;
    /**
     * id : 302107
     * oid : xo16011156933d7f55da7
     * item_id : 30786
     * title : 韩版百搭皮裙/黑色
     * sku_id : 120694
     * num : 1
     * outer_id : 821277480051
     * total_fee : 49.9
     * payment : 49.9
     * discount_fee : 0
     * sku_name : XL
     * pic_path : http://image.xiaolu.so/MG-1452155820775-韩版百搭皮裙3.png
     * status : 7
     * status_display : 交易关闭
     * refund_status : 0
     * refund_status_display : 没有退款
     * refund_id : null
     * kill_title : false
     */

    @SerializedName("orders") private List<OrdersEntity> mOrders;

    public void setId(int id) {
      this.mId = id;
    }

    public void setUrl(String url) {
      this.mUrl = url;
    }

    public void setTid(String tid) {
      this.mTid = tid;
    }

    public void setBuyerNick(String buyerNick) {
      this.mBuyerNick = buyerNick;
    }

    public void setBuyerId(int buyerId) {
      this.mBuyerId = buyerId;
    }

    public void setChannel(String channel) {
      this.mChannel = channel;
    }

    public void setPayment(double payment) {
      this.mPayment = payment;
    }

    public void setPostFee(int postFee) {
      this.mPostFee = postFee;
    }

    public void setTotalFee(double totalFee) {
      this.mTotalFee = totalFee;
    }

    public void setDiscountFee(int discountFee) {
      this.mDiscountFee = discountFee;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public void setStatusDisplay(String statusDisplay) {
      this.mStatusDisplay = statusDisplay;
    }

    public void setOrderPic(String orderPic) {
      this.mOrderPic = orderPic;
    }

    public void setBuyerMessage(String buyerMessage) {
      this.mBuyerMessage = buyerMessage;
    }

    public void setTradeType(int tradeType) {
      this.mTradeType = tradeType;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public void setPayTime(Object payTime) {
      this.mPayTime = payTime;
    }

    public void setConsignTime(Object consignTime) {
      this.mConsignTime = consignTime;
    }

    public void setOutSid(String outSid) {
      this.mOutSid = outSid;
    }

    public void setLogisticsCompany(Object logisticsCompany) {
      this.mLogisticsCompany = logisticsCompany;
    }

    public void setReceiverName(String receiverName) {
      this.mReceiverName = receiverName;
    }

    public void setReceiverState(String receiverState) {
      this.mReceiverState = receiverState;
    }

    public void setReceiverCity(String receiverCity) {
      this.mReceiverCity = receiverCity;
    }

    public void setReceiverDistrict(String receiverDistrict) {
      this.mReceiverDistrict = receiverDistrict;
    }

    public void setReceiverAddress(String receiverAddress) {
      this.mReceiverAddress = receiverAddress;
    }

    public void setReceiverMobile(String receiverMobile) {
      this.mReceiverMobile = receiverMobile;
    }

    public void setReceiverPhone(String receiverPhone) {
      this.mReceiverPhone = receiverPhone;
    }

    public void setOrders(List<OrdersEntity> orders) {
      this.mOrders = orders;
    }

    public int getId() {
      return mId;
    }

    public String getUrl() {
      return mUrl;
    }

    public String getTid() {
      return mTid;
    }

    public String getBuyerNick() {
      return mBuyerNick;
    }

    public int getBuyerId() {
      return mBuyerId;
    }

    public String getChannel() {
      return mChannel;
    }

    public double getPayment() {
      return mPayment;
    }

    public int getPostFee() {
      return mPostFee;
    }

    public double getTotalFee() {
      return mTotalFee;
    }

    public int getDiscountFee() {
      return mDiscountFee;
    }

    public int getStatus() {
      return mStatus;
    }

    public String getStatusDisplay() {
      return mStatusDisplay;
    }

    public String getOrderPic() {
      return mOrderPic;
    }

    public String getBuyerMessage() {
      return mBuyerMessage;
    }

    public int getTradeType() {
      return mTradeType;
    }

    public String getCreated() {
      return mCreated;
    }

    public Object getPayTime() {
      return mPayTime;
    }

    public Object getConsignTime() {
      return mConsignTime;
    }

    public String getOutSid() {
      return mOutSid;
    }

    public Object getLogisticsCompany() {
      return mLogisticsCompany;
    }

    public String getReceiverName() {
      return mReceiverName;
    }

    public String getReceiverState() {
      return mReceiverState;
    }

    public String getReceiverCity() {
      return mReceiverCity;
    }

    public String getReceiverDistrict() {
      return mReceiverDistrict;
    }

    public String getReceiverAddress() {
      return mReceiverAddress;
    }

    public String getReceiverMobile() {
      return mReceiverMobile;
    }

    public String getReceiverPhone() {
      return mReceiverPhone;
    }

    public List<OrdersEntity> getOrders() {
      return mOrders;
    }

    public static class OrdersEntity {
      @SerializedName("id") private int mId;
      @SerializedName("oid") private String mOid;
      @SerializedName("item_id") private String mItemId;
      @SerializedName("title") private String mTitle;
      @SerializedName("sku_id") private String mSkuId;
      @SerializedName("num") private int mNum;
      @SerializedName("outer_id") private String mOuterId;
      @SerializedName("total_fee") private double mTotalFee;
      @SerializedName("payment") private double mPayment;
      @SerializedName("discount_fee") private int mDiscountFee;
      @SerializedName("sku_name") private String mSkuName;
      @SerializedName("pic_path") private String mPicPath;
      @SerializedName("status") private int mStatus;
      @SerializedName("status_display") private String mStatusDisplay;
      @SerializedName("refund_status") private int mRefundStatus;
      @SerializedName("refund_status_display") private String mRefundStatusDisplay;
      @SerializedName("refund_id") private Object mRefundId;
      @SerializedName("kill_title") private boolean mKillTitle;

      public void setId(int id) {
        this.mId = id;
      }

      public void setOid(String oid) {
        this.mOid = oid;
      }

      public void setItemId(String itemId) {
        this.mItemId = itemId;
      }

      public void setTitle(String title) {
        this.mTitle = title;
      }

      public void setSkuId(String skuId) {
        this.mSkuId = skuId;
      }

      public void setNum(int num) {
        this.mNum = num;
      }

      public void setOuterId(String outerId) {
        this.mOuterId = outerId;
      }

      public void setTotalFee(double totalFee) {
        this.mTotalFee = totalFee;
      }

      public void setPayment(double payment) {
        this.mPayment = payment;
      }

      public void setDiscountFee(int discountFee) {
        this.mDiscountFee = discountFee;
      }

      public void setSkuName(String skuName) {
        this.mSkuName = skuName;
      }

      public void setPicPath(String picPath) {
        this.mPicPath = picPath;
      }

      public void setStatus(int status) {
        this.mStatus = status;
      }

      public void setStatusDisplay(String statusDisplay) {
        this.mStatusDisplay = statusDisplay;
      }

      public void setRefundStatus(int refundStatus) {
        this.mRefundStatus = refundStatus;
      }

      public void setRefundStatusDisplay(String refundStatusDisplay) {
        this.mRefundStatusDisplay = refundStatusDisplay;
      }

      public void setRefundId(Object refundId) {
        this.mRefundId = refundId;
      }

      public void setKillTitle(boolean killTitle) {
        this.mKillTitle = killTitle;
      }

      public int getId() {
        return mId;
      }

      public String getOid() {
        return mOid;
      }

      public String getItemId() {
        return mItemId;
      }

      public String getTitle() {
        return mTitle;
      }

      public String getSkuId() {
        return mSkuId;
      }

      public int getNum() {
        return mNum;
      }

      public String getOuterId() {
        return mOuterId;
      }

      public double getTotalFee() {
        return mTotalFee;
      }

      public double getPayment() {
        return mPayment;
      }

      public int getDiscountFee() {
        return mDiscountFee;
      }

      public String getSkuName() {
        return mSkuName;
      }

      public String getPicPath() {
        return mPicPath;
      }

      public int getStatus() {
        return mStatus;
      }

      public String getStatusDisplay() {
        return mStatusDisplay;
      }

      public int getRefundStatus() {
        return mRefundStatus;
      }

      public String getRefundStatusDisplay() {
        return mRefundStatusDisplay;
      }

      public Object getRefundId() {
        return mRefundId;
      }

      public boolean isKillTitle() {
        return mKillTitle;
      }
    }
  }
}
