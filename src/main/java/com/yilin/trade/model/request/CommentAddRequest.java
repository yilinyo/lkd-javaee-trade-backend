package com.yilin.trade.model.request;

import lombok.Data;

/**
 * Title: CommentAddRequest
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-18
 */
@Data
public class CommentAddRequest {

    private String gid;

    private String uid;

    private String userName;

    private String content;

}
