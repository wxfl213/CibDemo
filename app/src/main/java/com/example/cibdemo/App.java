package com.example.cibdemo;

public class App {

    static App sharedApp = new App();

    public String serverUrl;

    public String token;

    public static App sharedApp() {
        return sharedApp;
    }

    public String configUrl() {
        return serverUrl + "/dc/appinsight/config?token=" + token + "&platform=0";
    }
}
