package com.yilin.trade.controller;

import com.yilin.trade.common.BaseResponse;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.common.ResultUtils;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.model.domain.Comment;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.request.CommentAddRequest;
import com.yilin.trade.model.request.UserLoginRequest;
import com.yilin.trade.service.CommentService;
import com.yilin.trade.service.GoodsService;
import com.yilin.trade.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * Title: CommentController
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-18
 */

@RestController //适用于restful风格 json
@RequestMapping("/comments")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 根据 gid 获取 评论列表
     * @param gid
     * @return
     */
    @GetMapping("/getList")
    public BaseResponse<List<Comment>> getCommentsList(@RequestParam(value = "gid",defaultValue = "21")String gid){

        List<Comment> commentsList = commentService.getCommentsList(gid);
        return ResultUtils.success(commentsList);



    }


    @PostMapping("/add")
    public BaseResponse<String> addComment(@RequestBody CommentAddRequest commentAddRequest, HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(UserService.USER_LOGIN_STATE) == null){
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }

        //获取所有的数据

        String gid = commentAddRequest.getGid();

        String uid = commentAddRequest.getUid();

        String content = commentAddRequest.getContent();

        String userName = commentAddRequest.getUserName();


        Comment comment = new Comment();

        comment.setGid(Long.valueOf(gid));
        comment.setUid(Long.valueOf(uid));
        comment.setUserName(userName);
        comment.setContent(content);
        comment.setTime(String.valueOf(System.currentTimeMillis()));


        boolean isOk = commentService.addComment(comment);
        if(!isOk){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtils.success("over");



    }


}
