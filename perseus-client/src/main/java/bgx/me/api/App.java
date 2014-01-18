package bgx.me.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple app to call the perseus routing API
 * 
 */
public class App {

    private static final String SERVER = "http://perseus.bgx.me";
    private static final String ROUTE_API_URL = "action/route/route.jsp";
    private static final String user = "test-user2";
    private static final String apiKey = "8a828e41354abf2801354ace6d180001";
    private static final String cityCode = "76001";
    private String fileName;

    //
    public static void main(String[] args) {
        System.out.println("Hello World!");
        App app = new App();
        FileOutputStream fos = null;
        try {
            File f1 = new File(".");
            System.out.println(f1.getAbsolutePath());
            fos = new FileOutputStream(new File("result/response.json"));
            app.execute(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // To change body of catch statement use File |
                                 // Settings | File Templates.
        }
    }

    public void execute(OutputStream os) {
        try {
            Map<String, String> parameters = buildParameters(new File(fileName));
            postData(parameters, SERVER + "/" + ROUTE_API_URL, os);
        } catch (IOException e) {
            e.printStackTrace(); // To change body of catch statement use File |
                                 // Settings | File Templates.
        }
    }

    private Map<String, String> buildParameters(File file) throws IOException {
        HashMap parameters = new HashMap();
        parameters.put("user", user);
        parameters.put("apiKey", apiKey);
        parameters.put("cityCode", cityCode);
        String data = readFile(file);
        //
        parameters.put("data", data);
        return parameters;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected String readFile(File f) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        return sb.substring(0);
    }

    public void postData(Map<String, String> parameters, String url,
            OutputStream os) throws ClientProtocolException, IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> parts = new ArrayList<NameValuePair>();
        for (String key : parameters.keySet()) {
            NameValuePair part = new BasicNameValuePair(key, parameters
                    .get(key));
            parts.add(part);
        }
        httpost.setEntity(new UrlEncodedFormEntity(parts, HTTP.UTF_8));
        HttpResponse response = client.execute(httpost);
        HttpEntity entity = response.getEntity();
        entity.writeTo(os);
        os.close();
    }
}
