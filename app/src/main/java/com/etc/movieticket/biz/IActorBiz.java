package com.etc.movieticket.biz;

import com.etc.movieticket.biz.impl.IActorBizImpl;

/**
 * Created by NewOrin Zhang on 2016/8/9.
 * E-mail: NewOrin@163.com
 */
public interface IActorBiz {
    void getActorInfo(String webid, IActorBizImpl.OnGetActorInfoListener onGetActorInfoListener);
}
