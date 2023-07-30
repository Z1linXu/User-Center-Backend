package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * user service test
 *
 * @author Zilin xu
 */
@SpringBootTest

class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("ZhuLin");
        user.setUserAccount("123");
        user.setAvatarURL("https://zilinxu.com/COMP353GP/img/logo.png");
        user.setGender(0);
        user.setUserPassword("12345678");
        user.setPhone("13843292320");
        user.setEmail("zilinxu666@gmail.com");
        boolean result =userService.save(user);
        System.out.println(user.getId());
        //断言，测试用的，测试的结果是否等于你希望的结果
        Assertions.assertTrue(result);

    }

}