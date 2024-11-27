package com.alibabacloud.mse.demo.c.utils;

import com.alibabacloud.mse.demo.c.jedis.Jedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DBExecutor {

    @Autowired
    private Jedis jedis;

    @Autowired
    private DataSource dataSource;

    public void doDBExecute() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT name FROM test WHERE test.a = ?");
            preparedStatement.setString(1, "1");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            jedis.set("1" + ThreadLocalRandom.current().nextDouble(), resultSet.getString("name"));
            resultSet.getString("name");
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

}
