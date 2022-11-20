package com.yilin.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yilin.trade.model.domain.Comment;
import com.yilin.trade.model.domain.Goods;

import java.util.List;

/**
* @author 13227
* @description 针对表【comment】的数据库操作Service
* @createDate 2022-11-18 16:54:02
*/
public interface CommentService extends IService<Comment> {

    /**
     * 根据 gid 获取 评论列表
     * @param gid
     * @return List<Comment>
     */
        List<Comment> getCommentsList(String gid);

    /**
     * 添加评论
     * @param comment
     * @return  boolean
     */

    boolean addComment(Comment comment);

}
