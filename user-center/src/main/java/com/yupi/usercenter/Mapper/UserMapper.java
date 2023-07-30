package com.yupi.usercenter.Mapper;

import com.yupi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mac
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-07-26 18:27:28
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




