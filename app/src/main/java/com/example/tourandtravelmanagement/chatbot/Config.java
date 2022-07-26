package com.example.tourandtravelmanagement.chatbot;

/**
 * Created by dhiogoboza on 25/04/16.
 */
public class Config {
   // public static String serverurl="https://augmentreality.herokuapp.com/";
    public static String serverurl="http://192.168.1.100:5000/";
    public static String usersession=serverurl+"usersession";
    public static String outputoflocation=serverurl+"gettingserverhit";
    public static String displayreviews=serverurl+"displayreviewsid";
    public static String gettingsubjectchapetr=serverurl+"gettingsubjectchapetr";
    public static String Webviewurl=serverurl;
    private String username;
    private int serverPort = 0;
    private String serverAddress;

    public Config() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

}
