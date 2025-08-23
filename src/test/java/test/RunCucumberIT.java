package test;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import test.utilities.Upload;

import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber-report/cucumber.json",
        "rerun:target/failure/rerun.txt"}
        ,features = "src/test/features",
        glue = {"test.steps"}
)
public class RunCucumberIT {
    @AfterClass
    public static void test1() throws Exception{
        List<String> files= Upload.fetchFiles(System.getProperty("user.dir")+"/target/cucumber-reports/","cucumber/json");
        for(String file:files){
            System.out.println(files);
        Upload.uploadfile(file ,"CUCUMBER", "", "", "Selenium", "", "", "cos", "", "");
        }

    }
}
