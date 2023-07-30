package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author mac
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-07-26 18:27:28
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




