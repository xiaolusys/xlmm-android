package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.entities.ClickCarryBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by wisdom on 17/3/1.
 */

public interface VipInteractor {

    Subscription getMamaFans(int page, ServiceResponse<MamaFansBean> response);

    Subscription getMamaVisitor(int page, ServiceResponse<MMVisitorsBean> response);

    Observable<MamaFortune> getMamaFortune();

    Subscription getCategory(ServiceResponse<List<CategoryBean>> response);

    Subscription getChooseList(int page, String sort_field, String cid, int reverse,
                               ServiceResponse<ChooseListBean> response);

    Observable<MMShoppingBean> getShareShopping();

    Observable<MamaUrl> getMamaUrl();

    Observable<MamaSelfListBean> getMaMaSelfList();

    Observable<RecentCarryBean> getRecentCarry(String from, String days);

    Subscription getNinePic(int sale_category, ServiceResponse<List<NinePicBean>> response);

    Subscription getNinePicByModelId(int model_id, ServiceResponse<List<NinePicBean>> response);

    Subscription getNinePicByOrdering(ServiceResponse<List<NinePicBean>> response);

    Subscription getMamaAllCarryLogs(int page, ServiceResponse<CarryLogListBean> response);

    Subscription getMamaAllOderCarryLogs(int page, ServiceResponse<OderCarryBean> response);

    Subscription getMamaAllOder(int page, ServiceResponse<OderCarryBean> response);

    Subscription getMamaAllAwardCarryLogs(int page, ServiceResponse<AwardCarryBean> response);

    Subscription getMamaAllClickCarryLogs(int page, ServiceResponse<ClickCarryBean> response);

    Subscription getWxCode(ServiceResponse<WxQrcode> response);

    Subscription saveTime(int id, int save_times, ServiceResponse<SaveTimeBean> response);

    Subscription getBoutiqueList(int page, ServiceResponse<ProductListBean> response);

}
