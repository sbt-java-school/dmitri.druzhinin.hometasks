package hometask8;

import java.net.URL;

public interface Browser {
    @Cache(2000)
    String getPage(String url);
    @Cache(2000)
    String getPage(URL url);
}
