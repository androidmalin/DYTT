package com.bzh.dytt.game;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;
import com.bzh.dytt.variety.BaseVarietyInfoPresenter;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　旧版综艺<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class GamePresenter extends BaseGameInfoPresenter {

    public GamePresenter(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        super(baseActivity, baseFragment, iView);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getGame(Integer.valueOf(nextPage));
    }

    @Override
    public String getMaxPage() {
        return 369 + "";
    }
}
