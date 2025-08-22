package test.utilities;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonParser;
import io.cucumber.core.internal.com.fasterxml.jackson.core.util.BufferRecycler;
import jdk.jfr.ContentType;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Upload implements WebDriverFactoryConstants{
    public static void main(String[] args) throws Exception{
        List<String> files=fetchFiles(System.getProperty("user.dir")+"/target/surefire-reports/","cucumber/json");
        for(String file:files){
            Upload.uploadfile(file ,"CUCUMBER", "", "", "Karate", "", "", "coj", "", "");
        }
    }
    public static  List<String> fetchFiles(String filepath,String format) throws FileNotFoundException{

        String extension;
        if(format.equals("junit/xml") || format.equals("testng/xml")  || format.equals("hpuft/xml"))
            extension=".xml";
        else if(format.equals("cucuber/json"))
            extension=".json";
        else
            return null;
        List<String> list=new ArrayList<~>();
        File file=new File(filepath);
        if(!file.exists()){
            throw new FileNotFoundException("Cannot find file : "+file.getAbsolutePath());
        }
        File[] farray=file.listFiles();
        String path;
        if(farray!=null){
            for(File f:farray){
                path=f.getPath();
                if(path.endsWith(extension)){
                    list.add(path);
                }
            }
            return list;
        }
        return null;
    }
    public static String uploadfile(String filepath,String format,String automationHierarchy,String testsuitekey,String testsuiteName,String platform,String cycle
    ,String project,String release,String build) throws Exception{
        String res;
        System.out.println("Upload file to Qmetry "+filepath + " project "+project);
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpHost proxy=new HttpHost(PROXY_HOST,PROXY_PORT,"http");
        RequestConfig config= RequestConfig.custom()
                .setProxy(proxy)
                .build();
        HttpPost uploadFile=new HttpPost(QMETRY_URL+"/rest/import/createandscheduletestresults/1");
        uploadFile.setConfig(config);

        uploadFile.addHeader("Accept","application/json");
        uploadFile.addHeader("apiKey",QMETRY_API_KEY);
        uploadFile.addHeader("scope","default");

        MultipartEntityBuilder builder=MultipartEntityBuilder.create();
        builder.addTextBody("entityType",format, ContentType.TEXT_PLAIN);

        if(automationHierarchy!=null && !automationHierarchy.isEmpty())
            builder.addTextBody("automationHierarchy",automationHierarchy,ContentType.TEXT_PLAIN);

        if(testsuitekey!=null && !testsuitekey.isEmpty())
            builder.addTextBody("testsuitekey",testsuitekey,ContentType.TEXT_PLAIN);

        if(testsuiteName!=null && !testsuiteName.isEmpty())
            builder.addTextBody("testsuiteName",testsuiteName,ContentType.TEXT_PLAIN);

        if(cycle!=null && !cycle.isEmpty())
            builder.addTextBody("cycleID",cycle,ContentType.TEXT_PLAIN);

        if(platform!=null && !platform.isEmpty())
            builder.addTextBody("platformID",platform,ContentType.TEXT_PLAIN);

        if(project!=null && !project.isEmpty())
            builder.addTextBody("projectID",project,ContentType.TEXT_PLAIN);

        if(release!=null && !release.isEmpty())
            builder.addTextBody("releaseID",release,ContentType.TEXT_PLAIN);

        if(build!=null && !build.isEmpty())
            builder.addTextBody("buildID",build,ContentType.TEXT_PLAIN);

        File f=new File(filepath);
        builder.addPart("file",new FileBody(f));

        HttpEntity multipart=builder.build();
        uploadFile.setEntity(multipart);

        CloseableHttpResponse response=httpClient.execute(uploadFile);
        int code=response.getStatusLine().getStatusCode();
        if(code!=200){
            System.out.println("--------Status Code: "+code+"--------");
            if(code==400){
                HttpEntity entity=response.getEntity();
                if(entity!=null){
                    InputStream content=entity.getContent();
                    StringBuilder builder1=new StringBuilder();
                    Reader read=new InputStreamReader(content, StandardCharsets.UTF_8);
                    BufferedReader reader=new BufferedReader(read);
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            builder1.append(line);
                        }
                    }
                    finally {
                        reader.close();
                        content.close();
                    }
                    System.out.println("Enter Response--->"+builder1.toString());

                }
            }
            return "false";
        }
        else {
            HttpEntity entity=response.getEntity();
            if(entity!=null){
                InputStream content=entity.getContent();
                StringBuilder builder1=new StringBuilder();
                Reader read=new InputStreamReader(content,StandardCharsets.UTF_8);
                BufferedReader reader=new BufferedReader(read);
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        builder1.append(line);
                    }
                }
                finally {
                    reader.close();
                    content.close();
                }
                JsonParser parser=new JsonParser();
                JsonObject responsejson=(JsonObject)parser.parse(builder1.toString());
                return responsejson.toString().replace("\\/","/");

            }
        }
        res=EntityUtils.toString(response.getEntity());
        httpClient.close();
        return res;

    }
}
