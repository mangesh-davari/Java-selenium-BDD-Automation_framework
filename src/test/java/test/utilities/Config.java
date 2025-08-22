package test.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public final class Config implements WebDriverFactoryConstants{
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String ENV_VAR="TEST_ENVIRONMENT";
    public static String environmentRun="test";
    private static Properties properties;
    public String username;
    public String password;

    private Config(){

    }
    static {
        File BROWSER_PATH=new File(System.getProperty("user.dir")+ "/browser");

        Properties defaultProps=new Properties();
        properties=new Properties(defaultProps);
        String environment =System.getProperty(ENV_VAR);
        LOG.info("Test will run against environment: "+environment);

        if(environment!=null && environment.trim().length()>0){
            Config.environmentRun=environment;
            try(FileInputStream input=new FileInputStream(BROWSER_PATH +"\\env-" + environment + ".properties")){
                properties.load(input);
            } catch (Exception e){
                LOG.error("Error whilst loading properties: {}", e.getMessage(), e);
            }

        }


    }
    public static String get(String key){ return properties.getProperty(key);}
    public static String get(String key,String defaultValue){ return properties.getProperty(key,defaultValue);}
}
