package com.example.ramyar.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public Button spilBtn;
    public Button multiBtn;
    public Button highScoreBtn;
    public TextView multiplayer_text;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetWords().execute();
        multiplayer_text = (TextView) findViewById(R.id.multiplayer_text);
        spilBtn = (Button) findViewById(R.id.spilBtn);
        spilBtn.setOnClickListener(this);

        multiBtn = (Button) findViewById(R.id.multiBtn);
        multiBtn.setOnClickListener(this);

        highScoreBtn = (Button) findViewById(R.id.highScoreBtn);
        highScoreBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {
        if (v == spilBtn)
        {
            Constants.gameType = "SingelPlayer";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container , new DialogHighScoreAddName_frag());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (v == multiBtn)
        {
            Constants.gameType = "multiPlayer";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container , new DialogHighScoreAddName_frag());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(v == highScoreBtn )
        {
            Intent intent = new Intent(this , HighScoreActivity.class);
            startActivity(intent);
        }
    }
}
