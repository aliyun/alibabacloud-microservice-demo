package com.alibabacloud.mse.demo.c.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return new SimulateConnection();
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (url.startsWith("jdbc:mysql")) {
            return true;
        }
        return false;
    }

    @Override public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override public int getMajorVersion() {
        return 0;
    }

    @Override public int getMinorVersion() {
        return 0;
    }

    @Override public boolean jdbcCompliant() {
        return false;
    }

    @Override public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
