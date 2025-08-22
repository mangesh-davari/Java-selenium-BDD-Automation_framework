package test.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public final class ObjectRepository implements WebDriverFactoryConstants {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Properties properties;
    private ObjectRepository(){

    }

    static {
        Properties defaultProps =new Properties();
        File folder=new File(BROSER_PATH + "\\object_repo\\");

        File[] listOfFiles=folder.listFiles();
        for(int i=0;i<listOfFiles.length;i++){
            if(listOfFiles[i].isFile()){
                try(FileInputStream in=new FileInputStream(BROWSER_PATH + "\\object_repo\\" + listOfFiles[i].getName())){
                    defaultProps.load(in);
                    LOG.info("Test will use repository: " + listOfFiles[i].getName());
                } catch (Exception e){
                    LOG.error("Error whilst loading properties: {}", e.getMessage(), e);
                }
            }
        }
        properties = defaultProps;
    }
    public static String get(String key){ return properties.getProperty(key);}
    public static String get(String key,String defaultValue){ return properties.getProperty(key,defaultValue);}
}
