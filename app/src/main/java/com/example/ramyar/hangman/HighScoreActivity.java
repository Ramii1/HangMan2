package com.example.ramyar.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HighScoreActivity extends AppCompatActivity
{

    private ListView name_label, score_label;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        name_label = (ListView) findViewById(R.id.listview2);
        score_label = (ListView) findViewById(R.id.listview3);

        HighScoreLogic highScoreLogic = new HighScoreLogic(this);

        ArrayAdapter nameAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, highScoreLogic.name );
        ArrayAdapter pointsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, highScoreLogic.points);

        name_label.setAdapter(nameAdapter);
        score_label.setAdapter(pointsAdapter);

    }
}
