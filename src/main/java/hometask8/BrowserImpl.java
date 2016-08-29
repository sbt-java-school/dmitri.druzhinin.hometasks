package hometask8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserImpl implements Browser {
    @Override
    public String getPage(String url){
        String page;
        try {
            URL targetUrl = new URL(url);
            page=getPage(targetUrl);
        }catch (MalformedURLException e) {
            throw new RuntimeException("Possible such URL does not exist", e);
        }
        return page;
    }

    @Override
    public String getPage(URL url) {
//        StringBuilder builder=new StringBuilder();
//        try (InputStream in = url.openStream();
//             InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
//             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
//            String line;
//            while((line=bufferedReader.readLine())!=null)
//                builder.append(line);
//        }catch(IOException e){
//            throw new RuntimeException("Check your internet connection", e);
//        }
//        return builder.toString();
        return url.toString();
    }
}
