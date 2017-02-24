package com.example.use.monocler;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TitleMaterialFragment extends Fragment {

    private Title_Open title_open;
    private String link;

    public TitleMaterialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView pizzaRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_title_material, container, false);
        Context context = inflater.getContext();
        link = ((MainActivity) context).link.toString();
        title_open = new Title_Open(link);
        final String[] pizzaNames = new String[title_open.title_opens.size()];
        for (int i = 0; i < pizzaNames.length; i++) {
            pizzaNames[i] = title_open.title_opens.get(i).getLinkText();
        }
        final String[] pizzaImages = new String[title_open.title_opens.size()];
        for (int i = 0; i < pizzaImages.length; i++) {
            pizzaImages[i] = title_open.title_opens.get(i).getLinkPhoto();
            //pizzaImages[i] = R.drawable.logo;
        }
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);
        pizzaRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pizzaRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), TitleText.class);
                intent.putExtra("link", title_open.title_opens.get(position).getLinkTitle());
                intent.putExtra("photo", pizzaImages[position]);
                startActivity(intent);
            }
        });
        return pizzaRecycler;
    }
}
