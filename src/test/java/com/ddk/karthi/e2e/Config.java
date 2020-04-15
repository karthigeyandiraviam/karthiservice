package com.ddk.karthi.e2e;

public enum Config {
    INSTANCE;

    public final String baseUri;

    public String getBaseUri() {
        return this.baseUri;
    }

    Config() {
        StringBuilder sb = new StringBuilder("http://");
        sb.append(System.getProperty("server", "localhost")).append(":");
        sb.append(System.getProperty("port", "2222")).append("/ddk/v1/");
        this.baseUri = sb.toString();
    }
}
