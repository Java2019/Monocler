package com.example.use.monocler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TitleText extends Activity {

    private String link;
    private String photo;
    private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_text);
        //
        ImageView imageView = (ImageView) findViewById(R.id.id_image);
        WebView webView = (WebView)  findViewById(R.id.id_web);
        TextView textView = (TextView) findViewById(R.id.id_text);
        //
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        photo = intent.getStringExtra("photo");
        //
        UpdateTitlesText mt = new UpdateTitlesText();
        mt.execute();
        try {
            mt.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        webView.loadDataWithBaseURL(null, text,"text/html", "UTF-8", null);//setText(Html.fromHtml(text));
        webView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        Picasso.with(this).load(photo).fit().into(imageView);
        textView.setText(Html.fromHtml(text));
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
    private class UpdateTitlesText extends AsyncTask<Void, Void, Void> {

        private int index = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            Document document = null;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                //e.printStackTrace();
            }

            Elements elements = document.getElementById("left-area").getElementsByTag("p");
            for (Element element : elements) {
                index++;
                if (index == 1 )
                    text = text + element.toString();
                else if (element.getAllElements().size() == 1)
                    text = text + element.toString();
            }
            text = text.replaceAll("<p>&nbsp;</p>", "");
            return null;
        }

    }

}
