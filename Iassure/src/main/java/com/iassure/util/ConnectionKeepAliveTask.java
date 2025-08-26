package com.iassure.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Log4j2
public class ConnectionKeepAliveTask {

    @Autowired
    private DataSource dataSource;

    @Scheduled(fixedDelay = 240000) // 4 minutes in milliseconds
    public void keepConnectionsAlive() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Execute a lightweight query to keep the connection alive
            statement.execute("SELECT 1");
            log.info("Connection reestablished");

        } catch (SQLException e) {
            log.error("Exception occurred in keepConnectionsAlive method: {}", e.getMessage());
        }
    }
}
