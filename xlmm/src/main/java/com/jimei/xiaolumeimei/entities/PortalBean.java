package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye on 16/4/28.
 */
public class PortalBean {
  @Override public String toString() {
    return "PortalBean{" +
        "id=" + id +
        ", active_time='" + active_time + '\'' +
        ", posters=" + posters +
        ", categorys=" + categorys +
        ", activitys=" + activitys +
        ", promotion_brands=" + promotion_brands +
        '}';
  }

  private int id;
  private String active_time;

  private List<PostersBean> posters;
  private List<CategorysBean> categorys;

  private List<ActivitysBean> activitys;

  private List<PromotionBrandsBean> promotion_brands;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getActive_time() {
    return active_time;
  }

  public void setActive_time(String active_time) {
    this.active_time = active_time;
  }

  public List<PostersBean> getPosters() {
    return posters;
  }

  public void setPosters(List<PostersBean> posters) {
    this.posters = posters;
  }

  public List<CategorysBean> getCategorys() {
    return categorys;
  }

  public void setCategorys(List<CategorysBean> categorys) {
    this.categorys = categorys;
  }

  public List<ActivitysBean> getActivitys() {
    return activitys;
  }

  public void setActivitys(List<ActivitysBean> activitys) {
    this.activitys = activitys;
  }

  public List<PromotionBrandsBean> getPromotion_brands() {
    return promotion_brands;
  }

  public void setPromotion_brands(List<PromotionBrandsBean> promotion_brands) {
    this.promotion_brands = promotion_brands;
  }

  public static class PostersBean {
    @Override public String toString() {
      return "PostersBean{" +
          "item_link='" + item_link + '\'' +
          ", pic_link='" + pic_link + '\'' +
          ", app_link='" + app_link + '\'' +
          ", subject=" + subject +
          '}';
    }

    private String item_link;
    private String pic_link;
    private String app_link;
    private List<String> subject;

    public String getItem_link() {
      return item_link;
    }

    public void setItem_link(String item_link) {
      this.item_link = item_link;
    }

    public String getPic_link() {
      return pic_link;
    }

    public void setPic_link(String pic_link) {
      this.pic_link = pic_link;
    }

    public String getApp_link() {
      return app_link;
    }

    public void setApp_link(String app_link) {
      this.app_link = app_link;
    }

    public List<String> getSubject() {
      return subject;
    }

    public void setSubject(List<String> subject) {
      this.subject = subject;
    }
  }

  public static class CategorysBean {
    @Override
    public String toString() {
      return "CategorysBean{" +
              "cat_link='" + cat_link + '\'' +
              ", cat_img='" + cat_img + '\'' +
              ", id=" + id +
              ", name='" + name + '\'' +
              ", app_link='" + app_link + '\'' +
              '}';
    }

    private String cat_link;
    private String cat_img;
    private int id;
    private String name;
    private String app_link;

    public String getCat_link() {
      return cat_link;
    }

    public void setCat_link(String cat_link) {
      this.cat_link = cat_link;
    }

    public String getCat_img() {
      return cat_img;
    }

    public void setCat_img(String cat_img) {
      this.cat_img = cat_img;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getApp_link() {
      return app_link;
    }

    public void setApp_link(String app_link) {
      this.app_link = app_link;
    }
  }

  public static class ActivitysBean {
    @Override public String toString() {
      return "ActivitysBean{" +
          "id=" + id +
          ", title='" + title + '\'' +
          ", login_required=" + login_required +
          ", act_desc='" + act_desc + '\'' +
          ", act_img='" + act_img + '\'' +
          ", mask_link='" + mask_link + '\'' +
          ", act_link='" + act_link + '\'' +
          ", act_type='" + act_type + '\'' +
          ", act_applink='" + act_applink + '\'' +
          ", start_time='" + start_time + '\'' +
          ", end_time='" + end_time + '\'' +
          ", order_val=" + order_val +
          ", extras=" + extras +
          ", total_member_num=" + total_member_num +
          ", friend_member_num=" + friend_member_num +
          ", is_active=" + is_active +
          '}';
    }

    private int id;
    private String title;
    private boolean login_required;
    private String act_desc;
    private String act_img;
    private String mask_link;
    private String act_link;
    private String act_type;
    private String act_applink;
    private String start_time;
    private String end_time;
    private int order_val;
    /**
     * html : {"apply":"/mActivity/post/index.html"}
     * data : {}
     */

    private ExtrasBean extras;
    private int total_member_num;
    private int friend_member_num;
    private boolean is_active;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public boolean isLogin_required() {
      return login_required;
    }

    public void setLogin_required(boolean login_required) {
      this.login_required = login_required;
    }

    public String getAct_desc() {
      return act_desc;
    }

    public void setAct_desc(String act_desc) {
      this.act_desc = act_desc;
    }

    public String getAct_img() {
      return act_img;
    }

    public void setAct_img(String act_img) {
      this.act_img = act_img;
    }

    public String getMask_link() {
      return mask_link;
    }

    public void setMask_link(String mask_link) {
      this.mask_link = mask_link;
    }

    public String getAct_link() {
      return act_link;
    }

    public void setAct_link(String act_link) {
      this.act_link = act_link;
    }

    public String getAct_type() {
      return act_type;
    }

    public void setAct_type(String act_type) {
      this.act_type = act_type;
    }

    public String getAct_applink() {
      return act_applink;
    }

    public void setAct_applink(String act_applink) {
      this.act_applink = act_applink;
    }

    public String getStart_time() {
      return start_time;
    }

    public void setStart_time(String start_time) {
      this.start_time = start_time;
    }

    public String getEnd_time() {
      return end_time;
    }

    public void setEnd_time(String end_time) {
      this.end_time = end_time;
    }

    public int getOrder_val() {
      return order_val;
    }

    public void setOrder_val(int order_val) {
      this.order_val = order_val;
    }

    public ExtrasBean getExtras() {
      return extras;
    }

    public void setExtras(ExtrasBean extras) {
      this.extras = extras;
    }

    public int getTotal_member_num() {
      return total_member_num;
    }

    public void setTotal_member_num(int total_member_num) {
      this.total_member_num = total_member_num;
    }

    public int getFriend_member_num() {
      return friend_member_num;
    }

    public void setFriend_member_num(int friend_member_num) {
      this.friend_member_num = friend_member_num;
    }

    public boolean isIs_active() {
      return is_active;
    }

    public void setIs_active(boolean is_active) {
      this.is_active = is_active;
    }

    public static class ExtrasBean {
      /**
       * apply : /mActivity/post/index.html
       */

      private HtmlBean html;
      private DataBean data;

      public HtmlBean getHtml() {
        return html;
      }

      public void setHtml(HtmlBean html) {
        this.html = html;
      }

      public DataBean getData() {
        return data;
      }

      public void setData(DataBean data) {
        this.data = data;
      }

      public static class HtmlBean {
      }

      public static class DataBean {
      }

      @SerializedName("template_id") private String mTemplateId;

      public String getTemplateId() {
        return mTemplateId;
      }

      public void setTemplateId(String templateId) {
        mTemplateId = templateId;
      }
    }
  }

  public static class PromotionBrandsBean {
    @Override public String toString() {
      return "PromotionBrandsBean{" +
          "id=" + id +
          ", title='" + title + '\'' +
          ", loginRequired=" + loginRequired +
          ", actDesc='" + actDesc + '\'' +
          ", actImg='" + actImg + '\'' +
          ", actLogo='" + actLogo + '\'' +
          ", maskLink='" + maskLink + '\'' +
          ", actLink='" + actLink + '\'' +
          ", actType='" + actType + '\'' +
          ", actApplink='" + actApplink + '\'' +
          ", startTime='" + startTime + '\'' +
          ", endTime='" + endTime + '\'' +
          ", orderVal=" + orderVal +
          ", totalMemberNum=" + totalMemberNum +
          ", friendMemberNum=" + friendMemberNum +
          ", isActive=" + isActive +
          ", extras=" + extras +
          '}';
    }

    @SerializedName("id") private int id;
    @SerializedName("title") private String title;
    @SerializedName("login_required") private boolean loginRequired;
    @SerializedName("act_desc") private String actDesc;
    @SerializedName("act_img") private String actImg;
    @SerializedName("act_logo") private String actLogo;
    @SerializedName("mask_link") private String maskLink;
    @SerializedName("act_link") private String actLink;
    @SerializedName("act_type") private String actType;
    @SerializedName("act_applink") private String actApplink;
    @SerializedName("start_time") private String startTime;
    @SerializedName("end_time") private String endTime;
    @SerializedName("order_val") private int orderVal;
    @SerializedName("total_member_num") private int totalMemberNum;
    @SerializedName("friend_member_num") private int friendMemberNum;
    @SerializedName("is_active") private boolean isActive;
    @SerializedName("extras") private ExtrasBean extras;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public boolean isLoginRequired() {
      return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
      this.loginRequired = loginRequired;
    }

    public String getActDesc() {
      return actDesc;
    }

    public void setActDesc(String actDesc) {
      this.actDesc = actDesc;
    }

    public String getActImg() {
      return actImg;
    }

    public void setActImg(String actImg) {
      this.actImg = actImg;
    }

    public String getActLogo() {
      return actLogo;
    }

    public void setActLogo(String actLogo) {
      this.actLogo = actLogo;
    }

    public String getMaskLink() {
      return maskLink;
    }

    public void setMaskLink(String maskLink) {
      this.maskLink = maskLink;
    }

    public String getActLink() {
      return actLink;
    }

    public void setActLink(String actLink) {
      this.actLink = actLink;
    }

    public String getActType() {
      return actType;
    }

    public void setActType(String actType) {
      this.actType = actType;
    }

    public String getActApplink() {
      return actApplink;
    }

    public void setActApplink(String actApplink) {
      this.actApplink = actApplink;
    }

    public String getStartTime() {
      return startTime;
    }

    public void setStartTime(String startTime) {
      this.startTime = startTime;
    }

    public String getEndTime() {
      return endTime;
    }

    public void setEndTime(String endTime) {
      this.endTime = endTime;
    }

    public int getOrderVal() {
      return orderVal;
    }

    public void setOrderVal(int orderVal) {
      this.orderVal = orderVal;
    }

    public int getTotalMemberNum() {
      return totalMemberNum;
    }

    public void setTotalMemberNum(int totalMemberNum) {
      this.totalMemberNum = totalMemberNum;
    }

    public int getFriendMemberNum() {
      return friendMemberNum;
    }

    public void setFriendMemberNum(int friendMemberNum) {
      this.friendMemberNum = friendMemberNum;
    }

    public boolean isIsActive() {
      return isActive;
    }

    public void setIsActive(boolean isActive) {
      this.isActive = isActive;
    }

    public ExtrasBean getExtras() {
      return extras;
    }

    public void setExtras(ExtrasBean extras) {
      this.extras = extras;
    }

    public static class ExtrasBean {
      /**
       * tail_title : 2折起
       */

      @SerializedName("brandinfo") private BrandinfoBean brandinfo;

      public BrandinfoBean getBrandinfo() {
        return brandinfo;
      }

      public void setBrandinfo(BrandinfoBean brandinfo) {
        this.brandinfo = brandinfo;
      }

      public static class BrandinfoBean {
        @SerializedName("tail_title") private String tailTitle;

        public String getTailTitle() {
          return tailTitle;
        }

        public void setTailTitle(String tailTitle) {
          this.tailTitle = tailTitle;
        }
      }
    }
  }
}
