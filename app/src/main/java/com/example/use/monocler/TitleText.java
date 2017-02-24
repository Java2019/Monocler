package com.example.use.monocler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TitleText extends Activity {

    private String link;
    private String photo;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_text);
        //
        ImageView imageView = (ImageView) findViewById(R.id.id_image);
        WebView webView = (WebView)  findViewById(R.id.id_web);
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
        //webView.setSelected(true);
        Picasso.with(this).load(photo).fit().into(imageView);
    }
    private class UpdateTitlesText extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Document document = null;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                //e.printStackTrace();
            }

            text = document.getElementById("left-area").before("</div> \t<!-- end #left-area -->").toString();

            return null;
        }

    }

}
