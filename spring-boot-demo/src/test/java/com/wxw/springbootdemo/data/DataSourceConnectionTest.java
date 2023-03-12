package com.wxw.springbootdemo.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        System.out.println("dataSource Test: " + dataSource.getConnection());
    }
}
