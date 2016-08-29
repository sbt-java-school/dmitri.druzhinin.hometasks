package hometask8;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        Browser browser=ProxyUtils.makeCached(new BrowserImpl());
        System.out.println(browser.getPage("https://www.yandex.ru/"));
        System.out.println(browser.getPage("https://www.google.ru/#hl=ru"));
        Thread.sleep(1000);
        System.out.println(browser.getPage("https://www.yandex.ru/"));
        System.out.println(browser.getPage("https://www.google.ru/#hl=ru"));
        Thread.sleep(2000);
        System.out.println(browser.getPage("https://www.yandex.ru/"));
        System.out.println(browser.getPage("https://www.google.ru/#hl=ru"));
    }
}