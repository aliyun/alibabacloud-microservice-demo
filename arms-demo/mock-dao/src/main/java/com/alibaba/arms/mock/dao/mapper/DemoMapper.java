package com.alibaba.arms.mock.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author aliyun
 * @date 2020/06/16
 */
@Mapper
public interface DemoMapper {

    @Select("select a from unknown_db.unknown_tb where id=123 limit 1")
    public String badSql();

    @Select("select '1' from dummy_record where id=#{id} limit 1")
    public String goodSql(Integer id);


    @Select("select sleep(#{sleepSeconds}) from dummy_record limit 1")
    public String sleepSql(String sleepSeconds);

    @Select("select content from dummy_record where content=#{content}")
    List<String> selectContent(String content);
}
