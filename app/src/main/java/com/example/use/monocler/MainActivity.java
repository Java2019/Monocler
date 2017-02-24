package com.example.use.monocler;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView drawerList;
    private ArrayList<Title> titles = new ArrayList<>();
    private UpdateTitles mt;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition;
    public String link;
    private ArrayAdapter<Title> adapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //
        drawerList = (ListView)findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mt = new UpdateTitles();
        mt.execute();
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        //Создание ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open_drawer, R.string.close_drawer) {
            //Вызывается при переходе выдвижной панели в полностью закрытое состояние.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            //Вызывается при переходе выдвижной панели в полностью открытое состояние.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            };
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                         drawerList.setItemChecked(0, true);
                    }
                }
        );
        //
        UpdateMainActivity upMainActivity = new UpdateMainActivity();
        upMainActivity.execute();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener, View.OnClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(drawerList);
            link = titles.get(position).getTitleLink();
            Fragment fragment = new TitleMaterialFragment();
            currentPosition = position;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "visible_fragment");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }

        @Override
        public void onClick(View view) {

        }
    }

    private class UpdateTitles extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {

            adapter = new ArrayAdapter<Title>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, titles);
            adapter.notifyDataSetChanged();
            drawerList.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document document = null;
            try {
                document = Jsoup.connect("https://monocler.ru/karta-sayta").get();

            } catch (IOException e) {
                //e.printStackTrace();
            }

            Elements elements = document.getElementsByClass("wsp-category-title");
            for (Element element : elements) {
                String title = element.text().replaceAll("Рубрики: ", "");
                String titleName = title.toUpperCase().substring(0,1) + title.toLowerCase().substring(1);
                String titleLink = element.getElementsByAttribute("href").attr("href");
                titles.add(new Title(titleName, titleLink));
            }
            return null;
        }

    }

    private class UpdateMainActivity extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
            link = "https://monocler.ru/";
            Fragment fragment = new TitleMaterialFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, "visible_fragment");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            } catch (Exception e){

            }
            return null;
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){

        }
        return true;
    }

    public String getLink() {
        return link;
    }
}
