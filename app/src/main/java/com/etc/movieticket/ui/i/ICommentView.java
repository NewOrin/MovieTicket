package com.etc.movieticket.ui.i;

import com.etc.movieticket.entity.Comment;

/**
 * Created by NewOrin Zhang on 2016/8/9.
 * E-mail: NewOrin@163.com
 */
public interface ICommentView {

    Comment getComment();

    void movieCommentSuccess();

    void movieCommentFailed();
}
