package com.ddk.karthi.server;

public class StartServer {
    public static void main(String[] args) {
        try {
            new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
