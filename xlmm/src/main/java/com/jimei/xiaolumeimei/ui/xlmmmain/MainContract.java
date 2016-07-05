package com.jimei.xiaolumeimei.ui.xlmmmain;

import com.jimei.xiaolumeimei.base.BaseModel;
import com.jimei.xiaolumeimei.base.BasePresenter;
import com.jimei.xiaolumeimei.base.BaseView;

/**
 * Created by itxuye on 2016/7/4.
 */
public interface MainContract {
  interface Model extends BaseModel {
  }

  interface View extends BaseView {
  }

  abstract class Presenter extends BasePresenter<Model, View> {
  }
}
