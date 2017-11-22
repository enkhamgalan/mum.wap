package usa.edu.mum.wap.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-22
 * Time: 09:55
 */
public class Config {

    private static Properties properties;

    public static void loadConfig() {
        try {
            File configDir = new File(System.getProperty("catalina.base"), "conf");
            File configFile = new File(configDir, "myconfig.properties");
            InputStream stream = new FileInputStream(configFile);
            properties = new Properties();
            properties.load(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static Integer getInt(String key) {
        if (properties.getProperty(key) == null) {
            return null;
        } else {
            return Integer.parseInt(properties.getProperty(key));
        }
    }
}
