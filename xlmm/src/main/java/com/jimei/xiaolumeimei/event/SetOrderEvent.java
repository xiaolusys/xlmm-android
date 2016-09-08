package com.jimei.xiaolumeimei.event;

import com.jimei.xiaolumeimei.entities.MiPushOrderCarryBean;

/**
 * Created by wisdom on 16/9/7.
 */
public class SetOrderEvent {

    public SetOrderEvent(MiPushOrderCarryBean bean) {
        this.bean = bean;
    }

    private MiPushOrderCarryBean bean;

    public MiPushOrderCarryBean getBean() {
        return bean;
    }

    public void setBean(MiPushOrderCarryBean bean) {
        this.bean = bean;
    }
}
