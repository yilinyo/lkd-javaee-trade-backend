package com.yilin.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yilin.trade.model.domain.User;


import javax.servlet.http.HttpServletRequest;

/**
* @author 13227
* @description 针对表【user】的数据库操作Service
* @createDate 2022-11-09 17:12:44
*/
public interface UserService extends IService<User> {
    /**
     * 登录状态
     */

    public static final String USER_LOGIN_STATE = "userLoginState";
    /**
     * 用户注册
     * @param userName 用户账户
     * @param passWord 用户密码
     * @param checkPassWord 确认密码
     * @param phone 手机号
     * @return userId 用户id
     */
    long userRegister(String userName,String passWord,String checkPassWord,String phone);

    /**
     * 用户登录
     * @param userName 用户账户
     * @param passWord 用户密码
     * @param request
     * @return
     */

    User userLogin(String userName, String passWord, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user 用户脱敏
     * @return
     */
    public User getSafetyUser(User user);

    /**
     * 用户注销
     * @param request
     * @return
     */

    public int userLogout (HttpServletRequest request);

    /**
     * 根据用户id查用户信息
     * @param uid
     * @return
     */
    public User getUserById(Long uid);


    /**
     * 设置用户 权限
     * @param role
     * @return
     */
    public void setUserRole(Long uid,Integer role);



    /**
     * 举报减少用户信用值
     * @param uid
     * @return
     */
    public void decreaseCredit(Long uid);

    /**
     * 增加用户信用值
     * @param uid
     * @return
     */
    public void increaseCredit(Long uid);

    /**
     * 编辑用户
     * @param user uid
     * @return
     */
    public void editUser(User user,Long uid);

    /**
     * 逻辑删除用户
     * @param uid
     * @return
     */
    public void delUser(Long uid);
}
