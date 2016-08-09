package com.etc.movieticket.presenter;

import android.os.Handler;

import com.etc.movieticket.biz.IActorBiz;
import com.etc.movieticket.biz.impl.IActorBizImpl;
import com.etc.movieticket.entity.Actor;
import com.etc.movieticket.ui.i.IAcotrInfoView;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/9.
 * E-mail: NewOrin@163.com
 */
public class ActorPresenter {
    IAcotrInfoView iAcotrInfoView;
    private IActorBiz iActorBiz;
    private Handler mHandler = new Handler();

    public ActorPresenter(IAcotrInfoView iAcotrInfoView) {
        this.iAcotrInfoView = iAcotrInfoView;
        this.iActorBiz = new IActorBizImpl();
    }

    public void getActorInfo(final String webid) {

        iActorBiz.getActorInfo(webid, new IActorBizImpl.OnGetActorInfoListener() {
            @Override
            public void OnGetActorInfoSuccess(final Actor actor, final List<String> actorAlbumList, final List<String> actorWorkList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iAcotrInfoView.getActorInfoSucceed(actor, actorAlbumList, actorWorkList);
                    }
                });
            }

            @Override
            public void OnGetActorInfoFailed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iAcotrInfoView.getActorInfoFailed(errorMsg);
                    }
                });
            }
        });
    }
}
