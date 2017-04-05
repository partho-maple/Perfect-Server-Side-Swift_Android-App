package com.example.parthobiswas.familytree.conf;

/**
 * Created by parthobiswas on 3/21/17.
 */

public final class ServerConfig {

    private ServerConfig() {
    }

    public static final String IP_ADDRESS = "192.168.0.103";
    public static final String HOSTNAME = "localhost";
    public static final String PORT = "8181";
    public static final String BASE_URL = "http://" + IP_ADDRESS + ":" + PORT;
}
