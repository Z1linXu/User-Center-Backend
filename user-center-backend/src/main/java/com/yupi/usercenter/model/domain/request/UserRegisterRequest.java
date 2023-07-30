package com.yupi.usercenter.model.domain.request;
import lombok.Data;
import java.io.Serializable;
/**
 * 用户注册请求体
 *
 * @author Zilin Xu
 */
@Data //Lombok注解，生成getset方法
public class UserRegisterRequest implements Serializable{

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;


}

