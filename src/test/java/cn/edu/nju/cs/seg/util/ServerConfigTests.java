package cn.edu.nju.cs.seg.util;

import cn.edu.nju.cs.seg.ServerConfig;
import org.junit.Test;

/**
 * Created by fwz on 2017/7/10.
 */
public class ServerConfigTests {
    @Test
    public void testGetProperties() throws Exception {
        System.out.println(ServerConfig.APP_KEY);
        System.out.println(ServerConfig.SERVER_BASE_URL);
    }
}
