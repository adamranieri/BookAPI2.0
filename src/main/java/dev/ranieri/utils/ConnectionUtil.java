package dev.ranieri.utils;

import dev.ranieri.daos.PostgresBookDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    static Logger logger = Logger.getLogger(ConnectionUtil.class.getName());

    public static Connection createConnection() {

        try {
            //jdbc:postgresql://35.226.135.224:5432/ToDoDB?user=user&password=password
            Connection conn = DriverManager.getConnection(System.getenv("CONN_CRED"));
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.fatal("Connection could not be established",e);
            return null;
        }

    }
}
