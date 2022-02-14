package com.example.webpractice;

public class CodiDTO {
    private String title;
    private String spec;
    private String codiUrl;

    public CodiDTO() {
        this.title = title;
        this.spec = spec;
        this.codiUrl = codiUrl;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setSpec(String imageUrl) {
        this.spec = spec;
    }

    public void setCodiUrl(String codiUrl) {
        this.codiUrl = codiUrl;
    }

    public String getTitle() {
        return title;
    }


    public String getSpec() {
        return spec;
    }

    public String getCodiUrl() {
        return codiUrl;
    }
}
