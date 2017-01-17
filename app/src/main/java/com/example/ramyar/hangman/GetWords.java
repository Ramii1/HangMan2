package com.example.ramyar.hangman;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class GetWords extends AsyncTask<Void, Void, ArrayList<String>>
{

    private Logic logic;

    @Override
    protected ArrayList<String> doInBackground(Void... params)
    {
        ArrayList<String> downloadedWordList = new ArrayList<String>();

        try
        {
            String data = logic.hentUrl("http://dr.dk");
            data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
            downloadedWordList.clear();
            downloadedWordList.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

            System.out.println("doInBackground: Mulige ord efter de er tilføjet til listen = " + downloadedWordList);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return downloadedWordList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> downloadedWordList)
    {
        Constants.wordList = downloadedWordList;
    }
}
