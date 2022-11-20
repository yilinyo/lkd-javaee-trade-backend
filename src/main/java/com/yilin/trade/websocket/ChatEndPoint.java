package com.yilin.trade.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.model.domain.User;
import com.yilin.trade.model.websocket.Message;
import com.yilin.trade.model.websocket.ResultMessage;
import com.yilin.trade.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


import static com.yilin.trade.service.UserService.USER_LOGIN_STATE;

/**
 * Title: ChatEndPoint
 * Description: TODO
 * 类似 http 的servlet
 * @author Yilin
 * @version V1.0
 * @date 2022-11-13
 */
@Component
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)

public class ChatEndPoint {


    /**
     * 用来存储每个客户端对象对应的ChatEndpoint对象 key 是uid
     */
    private static Map<String,ChatEndPoint> users = new ConcurrentHashMap<>();

    /**
     *  用来存储每个客户端对象对应的聊天记录 key 是uid value 是json
     */

    private static Map<String, ArrayList<String>> chats = new ConcurrentHashMap<>();

    /**
     * websocket session
     */

    private  Session session;


    /**
     * httpsession
     */
    private HttpSession httpSession;



    /**
     *  建立连接被调
     */

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){

        this.session = session;

        //获取Httpssion

       HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

       this.httpSession = httpSession;


       User user = (User)httpSession.getAttribute(USER_LOGIN_STATE);



        // 将当前对象存储在 容器中 key为uid

        String uid = String.valueOf(user.getUid());

       users.put(uid,this);

       // 判断并建立 暂存 聊天记录的数据结构

       if(!chats.containsKey(uid)) {

           ArrayList<String> arr = new ArrayList<>();
           System.out.println(uid+"调用了一次");
           chats.put(uid,arr);

       }
        String message = MessageUtils.getMessage(true, null,null,null, getUsers());

        System.out.println(message);
        //连接就一条广播
        broadcastAllUsers(MessageUtils.getMessage(true, null, null,null,"当前在线用户人数："+users.size()+"人"));
        broadcastAllUsers(message);

        //获取该用户暂存离线消息并推送

        ArrayList<String> chatCache = chats.get(uid);


        for(String chat:chatCache){
//            ChatEndPoint chatEndPoint = users.get(uid);
//            System.out.println(chat);
            try {
                this.session.getBasicRemote().sendText(chat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        chats.remove(uid);


    }

    /**
     * 获取容器里的用户
     * @return
     */
    private Set<String> getUsers(){


        return ChatEndPoint.users.keySet();


    }

    /**
     * 推送所有客户端
     */

    private void broadcastAllUsers(String message){

        Set<String> usersSet = users.keySet();
            try {


                for (String user : usersSet) {

                    ChatEndPoint chatEndPoint = users.get(user);
                    chatEndPoint.session.getBasicRemote().sendText(message);
                }} catch(IOException e){
                    e.printStackTrace();
                }


            }




    /**
     * 接收数据被调用
     */

    @OnMessage
    public void onMessage(String message,Session session){


        ObjectMapper mapper = new ObjectMapper();
        try {
            Message mess = mapper.readValue(message, Message.class);

//            System.out.println(mess);
// 消息的接收者
            String toUid = mess.getToUid();

            ChatEndPoint chatEndPoint = users.get(toUid);

            User user = (User)httpSession.getAttribute(USER_LOGIN_STATE);



            if(chatEndPoint!=null){

                // 接收用户在线直接转发



                if(user==null){

                    throw new BusinessException(ErrorCode.LOGIN_ERROR);
                }

                String message1 = MessageUtils.getMessage(false, user.getUserName(), user.getUid(),user.getPhone(), mess);

                chatEndPoint.session.getBasicRemote().sendText(message1);



            }else{

                //接收用户不在线 先暂时存储消息

                ArrayList<String> messages = chats.get(toUid);

                if(messages == null){
                    messages = new ArrayList<>();
                    chats.put(toUid,messages);

                }

                //存的就是json
                messages.add(MessageUtils.getMessage(false, user.getUserName(), user.getUid(),user.getPhone(), mess));




            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 关闭连接调用
     */
    @OnClose
    public void onClose(Session session){


        User user = (User)httpSession.getAttribute(USER_LOGIN_STATE);

        users.remove(user.getUid().toString());

        System.out.println(user.getUserName()+"下线了，当前在线人数："+users.size()+"人");



    }

    /**
     * 获取当前时间戳，秒
     */
    private String getUnix(){

        long time = System.currentTimeMillis();


        time = time / 1000;


        return String.valueOf(time);


    }






}
