package com.example.ramyar.hangman;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogHighScoreAddName_frag extends Fragment implements View.OnClickListener
{

    Button saveBtn;
    Button dontSaveBtn;
    TextView message;
    EditText addName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_dialog_high_score_add_name, container, false);

        saveBtn = (Button) view.findViewById(R.id.highScoreSave);
        dontSaveBtn = (Button) view.findViewById(R.id.highScoreDontSave);
        message = (TextView) view.findViewById(R.id.highScoreName);
        addName = (EditText) view.findViewById(R.id.highScoreAddName);

        saveBtn.setOnClickListener(this);
        dontSaveBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        if (v == saveBtn)
        {
            Constants.name = addName.getText().toString();

            if (Constants.gameType.equals("SingelPlayer"))
            {
                Intent optionsIntent = new Intent(getActivity(), SingelPlayerActivity.class);
                startActivity(optionsIntent);
            }
            else if (Constants.gameType.equals("multiPlayer"))
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container , new MulitPlayerChooseWord_frag());
                ft.addToBackStack(null);
                ft.commit();
            }
        }
        else if (v == dontSaveBtn)
        {
            getActivity().onBackPressed();
        }
    }
}
