package com.yilin.trade.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yilin.trade.model.websocket.Message;
import com.yilin.trade.model.websocket.ResultMessage;

import javax.print.DocFlavor;

/**
 * Title: MessageUtils
 * Description: TODO
 * json工具类
 * @author Yilin
 * @version V1.0
 * @date 2022-11-13
 */
public class MessageUtils {

    public static String getMessage(Boolean isSystemMessage,String fromName,Long fromId,String fromPhone,Object message){


        ResultMessage result = new ResultMessage();

        result.setIsSystem(isSystemMessage);
        result.setMessage(message);

        //不是系统消息，来自客户转发
        if(fromName!=null){

            result.setFromName(fromName);
            result.setFromId(fromId);
            result.setFromPhone(fromPhone);

        }


        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  null;

    }






    public static Message getChatMessage(String toUserName,String toUid,String date,String message){

    Message mess = new Message();
    mess.setDate(date);
    mess.setToUserName(toUserName);
    mess.setToUid(toUid);
    mess.setMessage(message);


    return mess;


    }






}
