package com.example.ramyar.hangman;

import android.content.Context;
import android.content.SharedPreferences;


public class HighScoreLogic
{
    String[] name;
    Integer[] points;
    SharedPreferences sharedPreferences;

    public HighScoreLogic(Context context)
    {
        sharedPreferences  = context.getSharedPreferences("highScore", Context.MODE_PRIVATE );
        name = new String[20];
        points = new Integer[20];

        for (int i = 0; i < 20; i++)
        {
            name[i] = sharedPreferences.getString("name"+i,"-");
            points[i] = sharedPreferences.getInt("points"+i, 0);
        }
    }

    public boolean setHighScore(String name , int points)
    {
        int index;
        for (index = 0; index < 20 && this.points[index] > points; index++);
        if (index == 20)
        {
            return false;
        }

        for (int i = 19; i > index; i-- )
        {
            this.name[i] = this.name[i-1];
            this.points[i] = this.points[i-1];
        }

        this.name[index] = new String(name);
        this.points[index] = points;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int j = 0; j < 20; j++)
        {
            editor.putString("name" + j , this.name[j]);
            editor.putInt("points" + j , this.points[j]);
        }
        editor.commit();
        return true;
    }
}
