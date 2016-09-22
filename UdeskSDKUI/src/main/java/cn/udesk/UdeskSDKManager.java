package cn.udesk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.udesk.activity.OptionsAgentGroupActivity;
import cn.udesk.activity.UdeskChatActivity;
import cn.udesk.activity.UdeskFormActivity;
import cn.udesk.activity.UdeskHelperActivity;
import cn.udesk.activity.UdeskRobotActivity;
import cn.udesk.db.UdeskDBManager;
import cn.udesk.model.UdeskCommodityItem;
import cn.udesk.widget.UdeskDialog;
import cn.udesk.messagemanager.UdeskMessageManager;
import rx.Subscriber;
import udesk.core.UdeskCallBack;
import udesk.core.UdeskCoreConst;
import udesk.core.UdeskHttpFacade;
import udesk.core.model.MessageInfo;
import udesk.core.model.RobotInfo;
import udesk.core.utils.UdeskUtils;


public class UdeskSDKManager {



	/**
	 * 用户唯一的标识
	 */
	private String sdkToken = null;
	/**
	 * 用户的基本信息
	 */
	private Map<String, String> userinfo = null;
	/**
	 * 用户自定义字段文本信息
	 */
	private Map<String, String> textField = null;
	
	/**
	 * 用户自定义字段的列表信息
	 */
	private Map<String, String> roplist = null;


	/**
	 * 用户需要更新的基本信息
	 */
	private Map<String, String> updateUserinfo = null;
	/**
	 * 用户需要更新自定义字段文本信息
	 */
	private Map<String, String> updateTextField = null;

	/**
	 * 用户需要更新自定义字段的列表信息
	 */
	private Map<String, String> updateRoplist = null;

	private String domain = null;
	private String secretKey = null;
	private String userId = null;
	private String isBolcked =null;
	private String transfer = null;
	private String h5Url = null;
	private UdeskCommodityItem commodity = null;
	private UdeskDialog dialog;
	private static UdeskSDKManager instance;

	private String customerUrl = null;

	private boolean isNeedMsgNotice = true;
	private UdeskSDKManager() {
	}
	public static UdeskSDKManager getInstance() {
		if (instance == null) {
			synchronized (UdeskSDKManager.class) {
				if (instance == null) {
					instance = new UdeskSDKManager();
				}
			}
		}
		return instance;
	}



	/**
	 *
	 * @param context
	 * @param domain
	 * @param secretKey
	 */
	public void initApiKey(Context context,String domain, String secretKey){
		this.domain = domain;
		this.secretKey = secretKey;
		UdeskUtil.initImageLoaderConfig(context);
	}

	public void setUserInfo(Context context,String sdkToken,Map<String, String> info){
		this.setUserInfo(context, sdkToken, info, null);
	}

	public void setUserInfo(Context context,String sdkToken,Map<String, String> info,Map<String, String> textField){
		this.setUserInfo(context, sdkToken, info, textField, null);
	}

	public void setUserInfo(final  Context context,String token,Map<String, String> info,Map<String, String> textField,Map<String, String> roplist){

		String cacheToken = getSdkToken(context);
		if ((cacheToken == null)|| (cacheToken != null && ! cacheToken.equals(token))){
			clean(context);
			disConnectXmpp();
		}
		this.sdkToken = token;
		initDB(context,sdkToken);
		PreferenceHelper.write(context, UdeskConst.SharePreParams.Udesk_Sharepre_Name,
				UdeskConst.SharePreParams.Udesk_SdkToken, sdkToken);
		if(info == null){
			info = new HashMap<String,String>();
		}
		this.userinfo = info;
		userinfo.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, sdkToken);
		this.textField = textField;
		this.roplist = roplist;
	}


	public void toLanuchChatAcitvity(Context context){
		Intent intent = new Intent(context, UdeskChatActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}


	public void toLanuchHelperAcitivty(Context context){
		Intent intent = new Intent(context, UdeskHelperActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public void showRobot(final Context context) {

		showLoading(context);
		UdeskHttpFacade.getInstance().setUserInfo(context,getDomain(context),
				getSecretKey(context), getSdkToken(context),
				getUserinfo(), getTextField(),
				UdeskSDKManager.getInstance().getRoplist(), new UdeskCallBack() {

					@Override
					public void onSuccess(String string) {
						JsonUtils.parserCustomersJson(context, string);
						dismiss();
						if (!TextUtils.isEmpty(getH5Url(context))) {
							toLanuchRobotAcitivty(context, getH5Url(context), getTransfer(context));
						} else {
							UdeskUtils.showToast(context, context.getString(R.string.udesk_has_not_open_robot));
						}
					}
					@Override
					public void onFail(String string) {
						getRobotJsonApi(context);
					}
				});
	}


	private void getRobotJsonApi(final Context context){
		UdeskHttpFacade.getInstance().getRobotJsonApi(getDomain(context), getSecretKey(context), new UdeskCallBack() {

			@Override
			public void onSuccess(String message) {
				dismiss();
				RobotInfo item = JsonUtils.parseRobotJsonResult(message);
				if (item != null && !TextUtils.isEmpty(item.h5_url)) {
					PreferenceHelper.write(context, UdeskConst.SharePreParams.Udesk_Sharepre_Name,
							UdeskConst.SharePreParams.Udesk_Transfer, item.transfer);
					PreferenceHelper.write(context, UdeskConst.SharePreParams.Udesk_Sharepre_Name,
							UdeskConst.SharePreParams.Udesk_h5url, item.h5_url);
					toLanuchRobotAcitivty(context, item.h5_url, item.transfer);
				} else {
					UdeskUtils.showToast(context, context.getString(R.string.udesk_has_not_open_robot));
				}
			}

			@Override
			public void onFail(String message) {
				dismiss();
				UdeskUtils.showToast(context, message);
			}
		});
	}

	public void showRobotOrConversation(final Context context) {
		showLoading(context);
		UdeskHttpFacade.getInstance().setUserInfo(context,getDomain(context),
				getSecretKey(context), getSdkToken(context),
				getUserinfo(), getTextField(),
				UdeskSDKManager.getInstance().getRoplist(), new UdeskCallBack() {

					@Override
					public void onSuccess(String string) {
						JsonUtils.parserCustomersJson(context, string);
						dismiss();
						if (!TextUtils.isEmpty(getH5Url(context))) {
							toLanuchRobotAcitivty(context, getH5Url(context), getTransfer(context));
						} else {
							toLanuchChatAcitvity(context);
						}
					}

					@Override
					public void onFail(String string) {
						dismiss();
						toLanuchChatAcitvity(context);
					}
				});
	}

	/**
	 * 指引客户选择客服组
	 * @param context
	 */
	public void showConversationByImGroup( Context context) {
		Intent intent = new Intent(context, OptionsAgentGroupActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}


	/**
	 * 指定客服 id 进行分配 进行会话
	 * @param context
	 * @param agentId  客服ID
	 */
	public void lanuchChatByAgentId(Context context,String agentId){
		lanuchChatByConfirmId(context, "", agentId);
	}

	/**
	 *
	 * @param context
	 * @param groupId 指定客服组 id 进行分配
	 */
	public void lanuchChatByGroupId(Context context, String groupId){
		lanuchChatByConfirmId(context, groupId, "");
	}

	/**
	 * 控制控制台日志的开关
	 * @param isShow
	 */
	public void isShowLog(boolean isShow){
		UdeskCoreConst.xmppDebug =isShow;
		UdeskCoreConst.isDebug = isShow;
	}

	/**
	 * 断开xmpp连接
	 */
	public void disConnectXmpp(){
		UdeskMessageManager.getInstance().cancelXmppConnect().subscribe(new Subscriber<Boolean>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Boolean aBoolean) {

			}
		});
	}

	/**
	 * 获取当前会话未读消息的记录  setUserInfo调用后才有效
	 */
	public int  getCurrentConnectUnReadMsgCount(){
		return	UdeskDBManager.getInstance().getUnReadMessageCount();
	}

	public List<MessageInfo> getUnReadMessages(){
		return	UdeskDBManager.getInstance().getUnReadMessages();
	}
	/**
	 * 删除聊天数据
	 */
	public void deleteMsg(){
		UdeskDBManager.getInstance().deleteAllMsg();
	}

	public Map<String, String> getUserinfo() {
		return userinfo;
	}

	public Map<String, String> getTextField() {
		return textField;
	}

	public Map<String, String> getRoplist() {
		return roplist;
	}
	
	public String getSdkToken(Context context) {
		if(!TextUtils.isEmpty(sdkToken)){
			return sdkToken;
		}
		return PreferenceHelper.readString(context, UdeskConst.SharePreParams.Udesk_Sharepre_Name, UdeskConst.SharePreParams.Udesk_SdkToken);
	}

	public String getDomain(Context context) {
		if(!TextUtils.isEmpty(domain)){
			return domain;
		}
		return "";
	}

	public String getSecretKey(Context context) {
		if(!TextUtils.isEmpty(secretKey)){
			return secretKey;
		}
		return "";
	}
	
	public String getUserId(Context context) {
		if(!TextUtils.isEmpty(userId)){
			return userId;
		}
		return "";
	}

	public String getIsBolcked() {
		return isBolcked;
	}

	public String getTransfer(Context context) {
		
		if(!TextUtils.isEmpty(transfer)){
			return transfer;
		}
		return PreferenceHelper.readString(context, UdeskConst.SharePreParams.Udesk_Sharepre_Name, UdeskConst.SharePreParams.Udesk_Transfer);
	}

	public String getH5Url(Context context) {
		if(!TextUtils.isEmpty(h5Url)){
			return h5Url;
		}
		return PreferenceHelper.readString(context, UdeskConst.SharePreParams.Udesk_Sharepre_Name, UdeskConst.SharePreParams.Udesk_h5url);
	}

	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public void setIsBolcked(String isBolcked) {
		this.isBolcked = isBolcked;
	}

	public UdeskCommodityItem getCommodity() {
		return commodity;
	}

	public void setCommodity(UdeskCommodityItem commodity) {
		this.commodity = commodity;
	}

	private void showLoading(Context context) {
		try {
			dialog = new UdeskDialog(context, R.style.udesk_dialog);
			dialog.show();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private void toLanuchRobotAcitivty(Context context,String url,String tranfer){
		Intent intent = new Intent(context, UdeskRobotActivity.class);
		intent.putExtra(UdeskConst.UDESKTRANSFER, tranfer);
		intent.putExtra(UdeskConst.UDESKHTMLURL, url);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	private void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	private void lanuchChatByConfirmId(Context context,String groupId,String agentId){
		Intent intent = new Intent(context, UdeskChatActivity.class);
		intent.putExtra(UdeskConst.UDESKGROUPID,groupId);
		intent.putExtra(UdeskConst.UDESKAGENTID, agentId);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	//启动留言界面
	public void goToForm(Context context) {
		Intent intent = new Intent(context,
				UdeskFormActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 初始话DB
	 * @param context
	 * @param sdkToken
	 */
	private void initDB(Context context , String sdkToken) {
		releaseDB();
		UdeskDBManager.getInstance().init(context, sdkToken);
	}

	/**
	 * 销毁DB
	 */
	public void releaseDB(){
		UdeskDBManager.getInstance().release();
	}



	private void clean(Context context){
		this.userId = null;
		this.transfer = null;
		this.h5Url = null;
		this.sdkToken = null;
		this.userinfo = null;
		this.textField = null;
		this. roplist = null;
		this.customerUrl = null;
	}

	public boolean isNeedMsgNotice() {
		return isNeedMsgNotice;
	}

	public void setIsNeedMsgNotice(boolean isNeedMsgNotice) {
		this.isNeedMsgNotice = isNeedMsgNotice;
	}

	public Map<String, String> getUpdateUserinfo() {
		return updateUserinfo;
	}

	public void setUpdateUserinfo(Map<String, String> updateUserinfo) {
		this.updateUserinfo = updateUserinfo;
	}

	public Map<String, String> getUpdateTextField() {
		return updateTextField;
	}

	public void setUpdateTextField(Map<String, String> updateTextField) {
		this.updateTextField = updateTextField;
	}

	public Map<String, String> getUpdateRoplist() {
		return updateRoplist;
	}

	public void setUpdateRoplist(Map<String, String> updateRoplist) {
		this.updateRoplist = updateRoplist;
	}

	public void logoutUdesk(){
		releaseDB();
		disConnectXmpp();
	}

	public void setCustomerUrl(String url){
		this.customerUrl = url;
	}

	public String getCustomerUrl() {
		return customerUrl;
	}
}
