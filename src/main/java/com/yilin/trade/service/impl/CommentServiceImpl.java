package com.yilin.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yilin.trade.mapper.CommentMapper;

import com.yilin.trade.model.domain.Comment;

import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
* @author 13227
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2022-11-18 16:54:02
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Override
    public List<Comment> getCommentsList(String gid) {

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gid",gid);

        List<Comment> comments = commentMapper.selectList(queryWrapper);
        Collections.sort(comments);
        return  comments;



    }

    @Override
    public boolean addComment(Comment comment) {
        int r = commentMapper.insert(comment);

        if(r>0){
            return true;
        }else{
            return false;
        }
    }
}




