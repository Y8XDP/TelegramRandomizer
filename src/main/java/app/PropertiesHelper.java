package app;

import java.io.*;
import java.util.Properties;

public class PropertiesHelper {
    public static final String botTokenProp = "bot_token";
    public static final String apiIdProp = "api_id";
    public static final String apiHashProp = "api_hash";

    private static final String propFilePath = "config.properties";



    public static Properties getProperties() {
        File propertiesFile = new File(propFilePath);
        Properties properties = new Properties();

        if (!propertiesFile.exists()) {
            return createProperties(propertiesFile);
        }

        try{
            InputStream inStream = new FileInputStream(propertiesFile);
            properties.load(inStream);
            inStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return properties;
    }

    private static Properties createProperties(File propertiesFile)
    {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(propertiesFile);

            prop.setProperty(botTokenProp, "bot_token");
            prop.setProperty(apiIdProp, "api_id");
            prop.setProperty(apiHashProp, "api_hash");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }
}
