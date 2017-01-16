package com.example.ramyar.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Ramyar on 14-01-2017.
 */

public class MulitPlayerChooseWord_frag extends Fragment {

    private ListView listView;
    public TextView multiplayer_text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_multi_player_choose_word, container, false);

        return view;
    }


    public void onViewCreated(View view , Bundle savedInstantceState){

        multiplayer_text = (TextView) view.findViewById(R.id.multiplayer_text);

        listView = (ListView) view.findViewById(R.id.listView1);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, Constants.wordList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = listView.getItemAtPosition(position);
                String selectedWord = object.toString();
                Constants.ordet = selectedWord;

                Intent intent = new Intent(getActivity(), SingelPlayerActivity.class);

                startActivity(intent);



            }
        });
    }
}
