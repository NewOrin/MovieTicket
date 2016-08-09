package com.etc.movieticket.biz.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.biz.IActorBiz;
import com.etc.movieticket.entity.Actor;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;

import java.util.List;


/**
 * Created by NewOrin Zhang on 2016/8/9.
 * E-mail: NewOrin@163.com
 */
public class IActorBizImpl implements IActorBiz {
    private String TAG = "IActorBizImpl";

    @Override
    public void getActorInfo(final String webid, final OnGetActorInfoListener onGetActorInfoListener) {
        new Thread() {
            @Override
            public void run() {
                String result = OkHttpClientManager.getInstance().doHttpGet(Constants.SERVER_URL + Constants.DO_GET_ACTOR_INFO + webid);
                if (result.equals("error")) {
                    onGetActorInfoListener.OnGetActorInfoFailed(result);
                } else {
                    Actor actor = JSON.parseObject(result, Actor.class);
                    List<String> albumList = JSON.parseArray(actor.getAc_album(), String.class);
                    List<String> workList = JSON.parseArray(actor.getAc_work(), String.class);
                    onGetActorInfoListener.OnGetActorInfoSuccess(actor, albumList, workList);
                }
            }
        }.start();

    }

    public interface OnGetActorInfoListener {
        void OnGetActorInfoSuccess(Actor actor, List<String> actorAlbumList, java.util.List<String> actorWorkList);

        void OnGetActorInfoFailed(String errorMsg);
    }

}
