package hometask5;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Application {
    public static void main(String[] args){
        try(InputStream in = System.in;
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            while (true) {
                try {
                    System.out.print("Enter URL: ");
                    String urlAddress = bufferedReader.readLine();
                    readContent(urlAddress);
                    break;
                } catch (BusinessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }catch (IOException e){
            System.out.println("Terminal is broken. Sorry");
        }catch (Exception e){
            System.out.println("Unhandled exception. Sorry");
        }
    }

    public static boolean readContent(String url) throws BusinessException {
        try {
            URL targetUrl = new URL(url);
            try (InputStream in = targetUrl.openStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                while (true) {
                    String contentLine = bufferedReader.readLine();
                    if (contentLine != null) {
                        System.out.println(contentLine);
                    } else {
                        return true;
                    }
                }
            }
        } catch (MalformedURLException e) {
            throw new BusinessException("Incorrect URL-address", e);
        } catch (IOException e) {
            throw new BusinessException("Check the network connection.Possible such URL does not exist", e);
        }
    }
}
