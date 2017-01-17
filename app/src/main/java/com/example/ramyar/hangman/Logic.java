package com.example.ramyar.hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Logic {
    //    private ArrayList<String> muligeOrd = new ArrayList<String>();
    private ArrayList<String> brugteBogstaver = new ArrayList<String>();
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;


    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }

    public String getSynligtOrd() {
        return synligtOrd;
    }

    public String getOrdet() {
        return Constants.ordet;
    }
    public void setOrdet(String ord){
        Constants.ordet = ord;
    }

    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }

    public boolean erSidsteBogstavKorrekt() {
        return sidsteBogstavVarKorrekt;
    }

    public boolean erSpilletVundet() {
        return spilletErVundet;
    }

    public boolean erSpilletTabt() {
        return spilletErTabt;
    }

    public boolean erSpilletSlut() {
        return spilletErTabt || spilletErVundet;
    }

    public void isGameLost(boolean booleanGameState){

        spilletErTabt = booleanGameState;
    }


    public Logic() {
//        muligeOrd.add("bil");
//        muligeOrd.add("computer");

        nulstil();
    }

    public void nulstil()
    {
        brugteBogstaver.clear();
        antalForkerteBogstaver = 0;
        spilletErVundet = false;
        spilletErTabt = false;

        if (Constants.gameType.equals("SingelPlayer"))
        {
            Constants.ordet = Constants.wordList.get(new Random().nextInt(Constants.wordList.size()));
        }
        opdaterSynligtOrd();
    }


    private void opdaterSynligtOrd()
    {
        synligtOrd = "";
        spilletErVundet = true;
        for (int n = 0; n < Constants.ordet.length(); n++)
        {
            String bogstav = Constants.ordet.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav))
            {
                synligtOrd = synligtOrd + bogstav;
            }
            else
            {
                synligtOrd = synligtOrd + "*";
                spilletErVundet = false;
            }
        }
    }

    public void gætBogstav(String bogstav)
    {
        if (bogstav.length() != 1) return;
        System.out.println("Der gÃ¦ttes pÃ¥ bogstavet: " + bogstav);
        if (brugteBogstaver.contains(bogstav)) return;
        if (spilletErVundet || spilletErTabt) return;

        brugteBogstaver.add(bogstav);

        if (Constants.ordet.contains(bogstav))
        {
            sidsteBogstavVarKorrekt = true;
            System.out.println("Bogstavet var korrekt: " + bogstav);
        }
        else
        {
            sidsteBogstavVarKorrekt = false;
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            antalForkerteBogstaver = antalForkerteBogstaver + 1;
            if (antalForkerteBogstaver > 6)
            {
                spilletErTabt = true;
            }
        }
        opdaterSynligtOrd();
    }

    public void logStatus()
    {
        System.out.println("---------- ");
        System.out.println("- ordet (skult) = " + Constants.ordet);
        System.out.println("- synligtOrd = " + synligtOrd);
        System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
        System.out.println("- brugeBogstaver = " + brugteBogstaver);
        if (spilletErTabt) System.out.println("- SPILLET ER TABT");
        if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
        System.out.println("---------- ");
    }


    public static String hentUrl(String url) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null)
        {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }
}
