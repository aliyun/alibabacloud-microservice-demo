/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibabacloud.mse.demo.c.sql;

import java.util.List;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Eric Zhao
 */
@Mapper
public interface UserDao {

    @Select("SELECT * FROM user_test WHERE username = #{username}")
    User findUserByName(@Param("username") String username);

    @Select("SELECT * FROM user_test")
    List<User> findAllUsers();

    @Select("SELECT * FROM user_test WHERE id = #{id}")
    User findUserById(@Param("id") Long id);

    @Insert("INSERT INTO user_test(username, email) VALUES(#{username}, #{email})")
    int addUser(@Param("username") String username, @Param("email") String email);

    @Update("UPDATE user_test SET username = #{username} WHERE id = #{id}")
    int updateUsername(@Param("id") Long id, @Param("username") String username);

    @Delete("DELETE from user_test WHERE id = #{id}")
    void deleteUser(@Param("id") Long id);
}
