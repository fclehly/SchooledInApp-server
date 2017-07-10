package cn.edu.nju.cs.seg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fwz on 2017/5/29.
 * 全局配置类
 */
public class ServerConfig {


    public static Properties properties = new Properties();

    static {
        InputStream in = ServerConfig.class.getClassLoader()
                .getResourceAsStream("server.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static final String SERVER_BASE_PORT =
            properties.getProperty("server.port");

    public static final String SERVER_BASE_PROTOCOL =
            properties.getProperty("server.protocol");

    public static final String SERVER_BASE_HOME =
            properties.getProperty("server.ip");

    public static final String SERVER_BASE_PATH =
            properties.getProperty("server.base");

    public static final String SERVER_BASE_URL =
            SERVER_BASE_PROTOCOL + "://" + SERVER_BASE_HOME + ":"
                    + SERVER_BASE_PORT + SERVER_BASE_PATH;

    public static final String IMAGE_DIR =
            SERVER_BASE_PROTOCOL + "://"
                    + SERVER_BASE_HOME + ":" + SERVER_BASE_PORT + "/image/";

    public static final String AUDIO_DIR =
            SERVER_BASE_PROTOCOL + "://"
                    + SERVER_BASE_HOME + ":" + SERVER_BASE_PORT + "/audio/";

    public static final String LUCENE_INDEX_DIRECTORY = "lucene/index";

    public static final boolean INIT_DATA = true;

    public static boolean NOTIFICATION = true;

    public static final String MASTER_SECRET =
            properties.getProperty("server.masterSecret");
    public static final String APP_KEY =
            properties.getProperty("server.appKey");
}
