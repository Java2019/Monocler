package com.example.use.monocler;

/**
 * Created by use on 23.02.17.
 */
public class Title {
    String titleName;
    String titleLink;

    public Title(String titleName, String titleLink) {
        this.titleName = titleName;
        this.titleLink = titleLink;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setTitleLink(String titleLink) {
        this.titleLink = titleLink;
    }

    public String getTitleName() {

        return titleName;
    }

    public String getTitleLink() {
        return titleLink;
    }

    @Override
    public String toString() {
        return titleName;
    }
}
