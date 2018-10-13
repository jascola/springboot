package com.jascola.model.dao;

import com.jascola.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/*@Mapper*/
@Repository
public interface UserDao {
    int insert(UserEntity user);

    UserEntity selectByPhone(String phone);

    int delete(String phone);

    int update(UserEntity user);

    UserEntity selectById(Integer id);

    /*@Select(value = "select * from user")*/
    List<UserEntity> selectAll();
}
