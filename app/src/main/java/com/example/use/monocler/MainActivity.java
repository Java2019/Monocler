package com.example.use.monocler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Document document = null;
        try {
        document = Jsoup.connect("https://monocler.ru/karta-sayta").get();

    } catch (IOException e) {
        e.printStackTrace();
    }

    Elements elements = document.getElementsByClass("wsp-category-title");
        for (Element element : elements) {
        System.out.println(element);
    }*/
}
