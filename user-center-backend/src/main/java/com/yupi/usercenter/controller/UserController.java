package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * User Interface
 *
 * @author Zilin Xu
 */
@RestController
@RequestMapping("/user") //定义请求路径
public class UserController {

    @Resource
    private UserService userService;

    /**
     * User register
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        //check request is null or not
        if (userRegisterRequest == null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String SerialNumber = userRegisterRequest.getSerialNumber();

        //check any information is null
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, SerialNumber)){
            return null;
        }
        return userService.userRegister (userAccount, userPassword, checkPassword,SerialNumber);


    }

    /**
     * User login
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        //check request is null or not
        if (userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        //check any information is null
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        return userService.userLogin (userAccount, userPassword, request);


    }

    /**
     * User Logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request){
        //check request is null or not
        if (request == null){
            return null;
        }
        return userService.userLogout (request);


    }

    /**
     * Get Current User
     * @param request
     * @return
     */
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        //把user object强转为user类型
        User currentUser = (User) userObj;
        //如果user等于空
        if(currentUser == null) {
            return null;
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法

        //查数据库来获取用户信息
        User user = userService.getById(userId);
        return userService.getSafetyUser(user);
    }

    /**
     * Search user
     * @param username
     * @return
     */
    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request){
        //鉴权
        if(!isAdmin(request)){
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());


}

    /**
     * Delete user
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request){
        //鉴权
        if(!isAdmin(request)){
            return false;
        }

        if(id<=0){
            return false;
        }

        return userService.removeById(id);
    }

    /**
     * 鉴权
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request)
    {
        //鉴权
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
