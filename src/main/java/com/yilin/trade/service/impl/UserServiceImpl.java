package com.yilin.trade.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.mapper.UserMapper;
import com.yilin.trade.model.domain.User;
import com.yilin.trade.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 13227
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-11-09 17:12:44
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    /**
     * 加密盐
     */
    private static final String SALT = "yilin";



    @Resource
    private UserMapper userMapper;
    /**
     * 注册 不能非空
     * 账号不小于4位
     * 密码不小于8位
     * 用户名不能重复
     * 不能有关键字符
     * 两次密码是否相同
     *
     */
    @Override

    public long userRegister(String userName, String passWord, String checkPassWord,String phone) {
        //1. 校验
        if(StringUtils.isAnyBlank(userName,passWord,checkPassWord)){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");

        }
        if(userName.length() < 4){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度小于4");
        }
        if(passWord.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于8");

        }

        //不能包含特殊字符
        String validPattern =  "[`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？']";
        Matcher matcher = Pattern.compile(validPattern).matcher(userName);

        if(matcher.find()){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能包含特殊字符");
        }
        // 两个密码不一样
        if(!passWord.equals(checkPassWord)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不一样");
        }



        //账号不能重复

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        userQueryWrapper.eq("userName",userName);

        long count = userMapper.selectCount(userQueryWrapper);

        if(count > 0){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号已存在");

        }

        //2.加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+passWord).getBytes());

        User user = new User();

        user.setUserName(userName);
        user.setPassWord(encryptPassword);
        user.setPhone(phone);

        //初始状态用户名 账号同名
        user.setUserName(userName);
        user.setCreateTime(String.valueOf(System.currentTimeMillis()));
        user.setUpdateTime(String.valueOf(System.currentTimeMillis()));



        boolean save = this.save(user);



        if(!save){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }




        return user.getUid();

    }

    @Override
    public User userLogin(String userName, String passWord, HttpServletRequest request) {

        //1. 校验
        if(StringUtils.isAnyBlank(userName,passWord)){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");

        }
        if(userName.length() < 4){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码错误");
        }
        if(passWord.length() < 8){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码错误");
        }

        //不能包含特殊字符
        String validPattern =  "[`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？']";
        Matcher matcher = Pattern.compile(validPattern).matcher(userName);

        if(matcher.find()){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号 或 密码错误");
        }

        //2.加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+passWord).getBytes());
        //3.数据库查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        userQueryWrapper.eq("userName",userName);
        userQueryWrapper.eq("passWord",encryptPassword);
        User user = userMapper.selectOne(userQueryWrapper);

        if(user == null){
            log.info("Failed,can not find user in database!");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号 或 密码错误");

        }


        //4.. 用户脱敏
        User safetyUser = getSafetyUser(user);






        //5.记录用户登录态
        HttpSession session = request.getSession();

        session.setAttribute(USER_LOGIN_STATE,safetyUser);
        //        System.out.println(session.getId());




        return  safetyUser;
    }

    /**
     * 用户脱敏
     * @param user 用户
     * @return
     */
    @Override
    public User getSafetyUser(User user) {



        if(user == null){

            return  null;
        }

        User safetyUser = new User();
        safetyUser.setUid(user.getUid());
        safetyUser.setUserName(user.getUserName());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setUserCredit(user.getUserCredit());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());
        safetyUser.setIsDeleted(user.getIsDeleted());



        return safetyUser;

    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;

    }

    /**
     * 根据id获取用户信息
     * @param uid
     * @return
     */

    @Override
    public User getUserById(Long uid) {

        if(uid<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("uid",uid);

        User user = userMapper.selectOne(userQueryWrapper);

        return getSafetyUser(user);

    }

    @Override
    public void setUserRole(Long uid,Integer role) {

        User user = userMapper.selectById(uid);
        // 设置role
        user.setUserRole(role);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("uid",uid);

        int update = userMapper.update(user, userQueryWrapper);

        if(update<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }




    }

    @Override
    public void decreaseCredit(Long uid) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = userMapper.selectById(uid);

        user.setUserCredit(user.getUserCredit()-1);

        userQueryWrapper.eq("uid",uid);

        int update = userMapper.update(user, userQueryWrapper);

        if(update<=0){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }



    }

    @Override
    public void increaseCredit(Long uid) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = userMapper.selectById(uid);

        user.setUserCredit(user.getUserCredit()+1);
        user.setUpdateTime(String.valueOf(System.currentTimeMillis()));

        userQueryWrapper.eq("uid",uid);

        int update = userMapper.update(user, userQueryWrapper);

        if(update<=0){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }


    }

    @Override
    public void editUser(User user,Long uid) {


        User user1 = userMapper.selectById(uid);

        user1.setUserRole(user.getUserRole());
        user1.setUserCredit(user.getUserCredit());
        user1.setUpdateTime(String.valueOf(System.currentTimeMillis()));


        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        userQueryWrapper.eq("uid",uid);

        int update = userMapper.update(user1, userQueryWrapper);
        if(update<=0){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }


    }

    @Override
    public void delUser(Long uid) {

        userMapper.deleteById(uid);

    }
}




