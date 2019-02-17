package com.elite.firebaseyt;

import android.support.design.widget.TextInputEditText;

public class Data {

    String name;
    String url;
    String category;
    String view;
    public Data(TextInputEditText name, TextInputEditText url) {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
