package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.exception.BusinessException;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        //check request is null or not
        if (userRegisterRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "register request is null");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String serialNumber = userRegisterRequest.getSerialNumber();

        //check any information is null
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, serialNumber)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Missing Information");
        }
        long result = userService.userRegister (userAccount, userPassword, checkPassword,serialNumber);
        //return new BaseResponse<>(0,result,"ok");
        return ResultUtils.success(result);


    }

    /**
     * User login
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        //check request is null or not
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "login request is null");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        //check any information is null
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,  "Missing Information");
        }
        User user =  userService.userLogin (userAccount, userPassword, request);
        //return new BaseResponse<>(0, user, "ok");
        return ResultUtils.success(user);



    }

    /**
     * User Logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        //check request is null or not
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "logout request is null");
        }
        Integer result= userService.userLogout (request);
        return ResultUtils.success(result);



    }

    /**
     * Get Current User
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        //把user object强转为user类型
        User currentUser = (User) userObj;
        //如果user等于空
        if(currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "user is null");
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法

        //查数据库来获取用户信息
        User user = userService.getById(userId);
        User safetyUser =  userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * Search user
     * @param username
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request){
        //鉴权
        if(!isAdmin(request)){
            //return new ArrayList<>();
            throw new BusinessException(ErrorCode.NO_AUTH,"No permission.");
        }

        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list =  userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);


}

    /**
     * Delete user
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
        //鉴权
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"No permission.");
        }

        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"ID can not smaller than 0");
        }

        boolean b =  userService.removeById(id);
        return ResultUtils.success(b);
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
