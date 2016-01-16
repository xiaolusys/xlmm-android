package so.xiaolu.xiaolu.jsonbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wl on 15-12-16.
 */

public class RefundDetailBean implements Parcelable {
	public AllRefundsBean.RefundBaseInfo refund_base_info;
	public ArrayList<OrderDetailBean.Goods> goods_list;

	/**
	 * 默认构造方法
	 */
	public RefundDetailBean() {
		// TODO Auto-generated constructor stub
	}

	public RefundDetailBean(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/** 对象 写入 **/
		dest.writeParcelable(refund_base_info, flags);
		/** list 写入 **/
		dest.writeList(goods_list);
	}
	private void readFromParcel(Parcel in) {

		/** 对象 读出 */
		refund_base_info = in.readParcelable(AllOrdersBean.OrderBaseInfo.class.getClassLoader());
		/** list 读出 */
		goods_list = in.readArrayList(OrderDetailBean.Goods.class.getClassLoader());

	}

	public static final Parcelable.Creator<OrderDetailBean> CREATOR = new Parcelable.Creator<OrderDetailBean>() {
		public OrderDetailBean createFromParcel(Parcel in) {
			return new OrderDetailBean(in);
		}

		public OrderDetailBean[] newArray(int size) {
			return new OrderDetailBean[size];
		}
	};

	public AllRefundsBean.RefundBaseInfo getOrder_info() {
		return refund_base_info;
	}

	public void setOrder_info(AllRefundsBean.RefundBaseInfo bean) {
		this.refund_base_info = bean;
	}

	public ArrayList<OrderDetailBean.Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(ArrayList<OrderDetailBean.Goods> listBeans) {
		this.goods_list = listBeans;
	}

}