package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
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
import com.jimei.xiaolumeimei.entities.ProductNinePicBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.MamaService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 * Created by wisdom on 17/3/1.
 */

public class VipInteractorImpl implements VipInteractor {
    private final MamaService service;

    @Inject
    public VipInteractorImpl(MamaService service) {
        this.service = service;
    }

    @Override
    public Subscription getMamaFans(int page, ServiceResponse<MamaFansBean> response) {
        return service.getMamaFans(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getMamaVisitor(int page, ServiceResponse<MMVisitorsBean> response) {
        return service.getMamaVisitor(14, page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Observable<MamaFortune> getMamaFortune() {
        return service.getMamaFortune()
            .compose(new DefaultTransform<>());
    }

    @Override
    public Subscription getCategory(ServiceResponse<List<CategoryBean>> response) {
        return service.getCategory()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getChooseList(int page, String sort_field, String cid, int reverse,
                                      ServiceResponse<ChooseListBean> response) {
        if ("".equals(sort_field) && "".equals(cid)) {
            return service.getChooseList(page)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else if ("".equals(sort_field)) {
            return service.getChooseListByCid(page, cid)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else if ("".equals(cid)) {
            return service.getChooseListBySort(page, sort_field, reverse)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            return service.getChooseList(page, sort_field, cid, reverse)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }
    }

    @Override
    public Observable<MMShoppingBean> getShareShopping() {
        return service.getShareShopping()
            .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<MamaUrl> getMamaUrl() {
        return service.getMamaUrl("1.0")
            .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<MamaSelfListBean> getMaMaSelfList() {
        return service.getMaMaSelfList()
            .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<RecentCarryBean> getRecentCarry(String from, String days) {
        return service.getRecentCarry(from, days)
            .compose(new DefaultTransform<>());
    }

    @Override
    public Subscription getNinePic(int sale_category, ServiceResponse<List<NinePicBean>> response) {
        if (sale_category == -1) {
            return service.getNinePic()
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            return service.getNinePic(sale_category)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }
    }

    @Override
    public Subscription getNinePicByModelId(int model_id,int page, ServiceResponse<ProductNinePicBean> response) {
        return service.getNinePicByModelId(model_id,page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getMamaAllCarryLogs(int page, ServiceResponse<CarryLogListBean> response) {
        return service.getMamaAllCarryLogs(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getMamaAllOderCarryLogs(int page, ServiceResponse<OderCarryBean> response) {
        return service.getMamaAllOderCarryLogs(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getMamaAllOder(int page, ServiceResponse<OderCarryBean> response) {
        return service.getMamaAllOder("direct", page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getMamaAllAwardCarryLogs(int page, ServiceResponse<AwardCarryBean> response) {
        return service.getMamaAllAwardCarryLogs(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getMamaAllClickCarryLogs(int page, ServiceResponse<ClickCarryBean> response) {
        return service.getMamaAllClickCarryLogs(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getWxCode(ServiceResponse<WxQrcode> response) {
        return service.getWxCode()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription saveTime(int id, int save_times, ServiceResponse<SaveTimeBean> response) {
        return service.saveTime(id, save_times)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
