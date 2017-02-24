package com.example.use.monocler;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by use on 23.02.17.
 */
public class Title_Open {
    private String linkTitle;
    private String linkPhoto;
    private String linkText;
    private UpdateTitles mt;
    public static ArrayList<Title_Open> title_opens= new ArrayList<>();
    private String link;

    public Title_Open(String link) {
        setLink(link);
        mt = new UpdateTitles();
        mt.execute();
        try {
            mt.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void setTitle_opens() {
        Title_Open.title_opens = new ArrayList<>();
    }

    public Title_Open(String linkTitle, String linkPhoto, String linkText) {
        this.linkTitle = linkTitle;
        this.linkPhoto = linkPhoto;
        this.linkText = linkText;
    }

    private class UpdateTitles extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Document document = null;
            setTitle_opens();
            try {
                document = Jsoup.connect(link).get();

            } catch (IOException e) {
                //e.printStackTrace();
            }

            Elements elements = document.getElementsByClass("et-main-image");
            for (Element element : elements) {
                String linkTitle = element.getElementsByAttribute("href").attr("href");
                String linkPhoto = element.getElementsByAttribute("src").attr("src");
                String linkText  = element.getElementsByAttribute("alt").attr("alt");

                title_opens.add(new Title_Open(linkTitle, linkPhoto, linkText));
            }
            return null;
        }

    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
