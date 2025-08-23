package test.utilities;

import java.io.File;

public interface WebDriverFactoryConstants {
    File WEB_DRIVER_PATH=new File(System.getProperty("user.dir") + "/webdriver");
    File BROWSER_PATH=new File(System.getProperty("user.dir") + "/browser");
    String PROXY_HOST=" ";
    Integer PROXY_PORT= 8090;
    public String QMETRY_URL="https://qmetry.app";
    public String QMETRY_API_KEY=" iuryekwufjhoeirfj";
    String PROXY_URL="http://"+PROXY_HOST+":"+PROXY_PORT;
    String TEST=Config.get("opel_prospect.url");
    String TEST_FOR=ObjectRepository.get("test_for");
}
