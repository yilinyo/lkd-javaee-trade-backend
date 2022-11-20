package com.yilin.trade.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcloud.cos.model.ciModel.auditing.UserInfo;
import com.yilin.trade.common.BaseResponse;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.common.ResultUtils;
import com.yilin.trade.constant.UserConstant;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.model.domain.User;
import com.yilin.trade.model.request.UserLoginRequest;
import com.yilin.trade.model.request.UserRegisterRequest;
import com.yilin.trade.model.request.UserSearchRequest;
import com.yilin.trade.model.response.UserInfoResponse;
import com.yilin.trade.service.GoodsService;
import com.yilin.trade.service.OrderService;
import com.yilin.trade.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Title: UserController
 * Description: TODO
 *
 * @author Yilin
 * @version V1.0
 * @date 2022-11-09
 */


    @RestController //适用于restful风格 json
    @RequestMapping("/user")

    public class UserController {
        @Resource
        private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    private GoodsService goodsService;

        @PostMapping("/register")
        public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest,HttpServletRequest request){
            if(userRegisterRequest == null){

                throw new BusinessException(ErrorCode.PARAMS_ERROR);

            }
            String userName = userRegisterRequest.getUserName();
            String passWord = userRegisterRequest.getPassWord();
            String checkPassword = userRegisterRequest.getCheckPassWord();
            String phone = userRegisterRequest.getPhone();

            if(StringUtils.isAnyBlank(userName,passWord,checkPassword,phone)) {

                throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
            }

            long id = userService.userRegister(userName, passWord, checkPassword,phone);
            return ResultUtils.success(id);

        }
        @GetMapping("/test")

        public String test(){

            return "test success";


        }

        @PostMapping("/test1")
        public String test1( String name ,String age){
            System.out.println(name);
            System.out.println(age);
            System.out.println("------");
            return "test1 success";
        }

        //当前登录态
        @GetMapping("/current")
        public BaseResponse<User> getCurrentUser(HttpServletRequest request){

            User currentUser = (User)request.getSession().getAttribute(UserService.USER_LOGIN_STATE);

            if(currentUser == null){
                throw new BusinessException(ErrorCode.LOGIN_ERROR,"未登录！");
            }

            //更新数据
            long userId = currentUser.getUid();

            // TODO 校验用户 是否合法

            User user = userService.getById(userId);


            User endUser = userService.getSafetyUser(user);

            return ResultUtils.success(endUser);


        }


        @PostMapping("/login")
        public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
            if(userLoginRequest == null){

                throw new BusinessException(ErrorCode.PARAMS_ERROR);

            }
            String userName = userLoginRequest.getUserName();
            String passWord = userLoginRequest.getPassWord();

            if(StringUtils.isAnyBlank(userName,passWord)) {

                throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空！");
            }

            User user = userService.userLogin(userName, passWord, request);
            return ResultUtils.success(user);

        }


        @PostMapping("/logout")
        public BaseResponse<Integer> userLogout( HttpServletRequest request){

            if(request==null){

                throw new BusinessException(ErrorCode.LOGIN_ERROR,"未登录！");
            }


            int res = userService.userLogout(request);

            return ResultUtils.success(res);

        }


    @PostMapping("/getMyInfo")
    public BaseResponse<UserInfoResponse> searchInfo(HttpServletRequest request){

        if(request==null){

            throw new BusinessException(ErrorCode.LOGIN_ERROR,"未登录！");
        }

        //

        User user =(User) request.getSession().getAttribute(UserService.USER_LOGIN_STATE);


        Long uid = user.getUid();

        //最新状态的 user
        User newUser = userService.getUserById(uid);

        Integer userRole = newUser.getUserRole();

        String role = "游客";

        if(userRole == 0){
            role = "普通用户";
        }
        if(userRole == 1){

            role = "VIP用户";
        }
        if(userRole == 2){

            role = "管理员";
        }



        int goodsNum = goodsService.getGoodsNum(uid);
        // 作为卖家的订单数
        Integer soldOrderNum = orderService.getSoldOrderNum(uid);

        // 作为买家的订单数
        Integer boughtOrderNum = orderService.getBoughtOrderNum(uid);

        UserInfoResponse userInfo = new UserInfoResponse();
        userInfo.setUserName(newUser.getUserName());
        userInfo.setUserRole(role);
        userInfo.setUserCredit(newUser.getUserCredit());
        userInfo.setPhone(newUser.getPhone());
        userInfo.setSellingNum(goodsNum);
        userInfo.setBoughtNum(boughtOrderNum);
        userInfo.setSoldNum(soldOrderNum);

        return ResultUtils.success(userInfo);



    }




        //    HttpServletRequest request
        @PostMapping("/search")
        public BaseResponse<List<User>> searchUsers(@RequestBody UserSearchRequest userSearchRequest, HttpServletRequest request){
            // 管理员权限
            if(!isAdmin(request)){

                throw new BusinessException(ErrorCode.AUTH_ERROR,"无权限！");
            }




            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            List<User> list = userService.list();

            //反射
            Field[] fields = userSearchRequest.getClass().getDeclaredFields();

            for(int i = 0 ; i < fields.length ; i++) {
                //设置是否允许访问，不是修改原来的访问权限修饰词。
                //爆破
                fields[i].setAccessible(true);

                try {
                    // 是否为null 或空

                    if(fields[i].get(userSearchRequest)!=null && StringUtils.isNotBlank(fields[i].get(userSearchRequest).toString())){

                        // 模糊查询 拼接

                        queryWrapper.like(fields[i].getName(),fields[i].get(userSearchRequest));
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }



            List<User> users = userService.list(queryWrapper);


            List<User> lists = users.stream().map(user ->
                    userService.getSafetyUser(user)).collect(Collectors.toList());

            return ResultUtils.success(lists);
        }



//    HttpServletRequest request

    /**
     * 减少信用值的 接口
     * @param uid
     * @param request
     * @return
     */
         @GetMapping("/decreaseCredit")
        public BaseResponse<String> searchUsers(@RequestParam("uid") String uid, HttpServletRequest request){
             if(request==null||request.getSession()==null||request.getSession().getAttribute(UserService.USER_LOGIN_STATE)==null){

                 throw new BusinessException(ErrorCode.LOGIN_ERROR,"未登录！");
             }


             userService.decreaseCredit(Long.valueOf(uid));

             return ResultUtils.success("finish");


         }

    //    HttpServletRequest request
    @PostMapping("/edit.do")
    public BaseResponse<String> editUser(@RequestBody User user, HttpServletRequest request){
        if(request==null||request.getSession()==null||request.getSession().getAttribute(UserService.USER_LOGIN_STATE)==null){

            throw new BusinessException(ErrorCode.LOGIN_ERROR,"未登录！");
        }


        Long uid = user.getUid();







        userService.editUser(user,uid);




        return ResultUtils.success("finish");


    }



        @PostMapping("/del.do")
        public BaseResponse<String>  deleteUser(@RequestBody User user,HttpServletRequest request){
            //管理员权限

            Long id = user.getUid();
            if(!isAdmin(request)){

                throw new BusinessException(ErrorCode.AUTH_ERROR,"无权限！");
            }

            if(id<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误！");
            }

            //框架会逻辑删除
            boolean b = userService.removeById(id);

            return  ResultUtils.success("finish");
        }





        /**
         * 是否为管理员
         * @param request request
         * @return
         */

        private boolean isAdmin(HttpServletRequest request){

            //管理员权限
            Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
            User user= (User) obj;
            return  !(user == null || user.getUserRole() != UserConstant.ADMIN_ROLE) ;

        }





    }






