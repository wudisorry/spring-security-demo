package com.arh.springsecuritydemo.mapper;

import com.arh.springsecuritydemo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User selectUserById(Integer id);

    User selectUserByUsername(String username);

    User selectUserWithRoleAndMenuByUsername(String username);

    @Insert("insert into t_user(f_name,f_phone,f_remark,f_username,f_password,f_enabled) values(#{name},#{phone},#{remark},#{username},#{password},#{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "f_id")
    int insert(User user);
}
