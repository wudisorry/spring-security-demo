package com.arh.springsecuritydemo.mapper;

import com.arh.springsecuritydemo.entity.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {

    List<Menu> selectAllMenus();

    List<Menu> selectAllMenusWithRole();

    @Insert("insert into t_menu(f_url,f_name) values(#{url},#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "f_id")
    int insert(Menu menu);
}
