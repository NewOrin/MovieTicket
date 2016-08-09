package com.etc.movieticket.ui.i;

import com.etc.movieticket.entity.Actor;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/9.
 * E-mail: NewOrin@163.com
 */
public interface IAcotrInfoView {
    void getActorInfoSucceed(Actor actor, List<String> actorAlbumList, List<String> actorWorkList);

    void getActorInfoFailed(String errorMsg);
}
