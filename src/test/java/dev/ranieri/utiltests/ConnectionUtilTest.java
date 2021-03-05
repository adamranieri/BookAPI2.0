package dev.ranieri.utiltests;

import dev.ranieri.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ConnectionUtilTest {


    @Test
    void establishConnection(){
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }

        Assertions.assertNotNull(ConnectionUtil.createConnection());
    }

}
