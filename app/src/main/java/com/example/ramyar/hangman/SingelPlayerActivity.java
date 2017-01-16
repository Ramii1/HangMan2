package com.example.ramyar.hangman;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.TimeUnit;

public class SingelPlayerActivity extends AppCompatActivity {

    private int life = 0;
    private Button gætBtn;
    private TextView textView;
    private EditText editText;
    private ImageView imageView;
    public TextView multiplayer_text;
    Chronometer chronometer;


    Logic logik;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        logik = new Logic();
        logik.logStatus();

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        multiplayer_text = (TextView) findViewById(R.id.multiplayer_text);
        imageView = (ImageView) findViewById(R.id.imageView);
        gætBtn = (Button) findViewById(R.id.gætBtn);
        textView = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText);
        textView.setText("Du skal gætte dette ord: " + logik.getSynligtOrd() +
                "\nSkriv et bogstav og tryk 'Gæt'.\n");


        if (Constants.gameType.equals("SingelPlayer")) {
            multiplayer_text.setVisibility(View.INVISIBLE);
        } else {
            multiplayer_text.setVisibility(View.VISIBLE);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_genstart) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Vil du genstarte spillet?");

            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "JA", then he is allowed to exit from application
                    finish();

//                    restart();
                }
            });

            builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "nej", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void restart() {

        logik.nulstil();
        imageView.setImageResource(R.drawable.galge);
        editText.setText("");
        life = 0;
        editText.setHint("Indsæt Bogstav");
        textView.setText("Du skal gætte dette ord: " + logik.getSynligtOrd() +
                "\nSkriv et bogstav og tryk 'Gæt'.\n");

    }


    private void updateScreen() {

        textView.setText("Gæt ordet: " + logik.getSynligtOrd());
        textView.append("\n\nDu har " + logik.getAntalForkerteBogstaver() + " forkerte:" + logik.getBrugteBogstaver());

        if (!logik.erSidsteBogstavKorrekt()) {
            life++;
            if (life >= 6) {
                life = 6;
            }

            switch (life) {
                case 1:
                    imageView.setImageResource(R.drawable.forkert1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.forkert2);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.forkert3);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.forkert4);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.forkert5);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.forkert6);
                    break;
                default:
                    break;
            }
        }

        if (logik.erSpilletVundet()) {
            chronometer.stop();

            textView.append("\nDu har vundet");
            editText.setHint("Tryk Genstart for at spile igen");

            saveHighScore();


        }
        if (logik.erSpilletTabt()) {
            chronometer.stop();
            textView.setText("Du har tabt, det rigtige ord er : " + logik.getOrdet());
            editText.setHint("Tryk Genstart for at spile igen");
            saveHighScore();
        }
    }

    public void onClickKill(View v) {
        String bogstav = editText.getText().toString();

        if (bogstav.length() != 1) {
            editText.setError("Skriv ét bogstav");
            return;
        }
        logik.gætBogstav(bogstav);
        editText.setText("");
        editText.setError(null);
        updateScreen();

    }

    public void saveHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", Constants.name);
        editor.putString("time", getElapsedTime());
        editor.commit();
    }

    public String getElapsedTime() {

        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        String time = Long.toString(elapsedMillis);
        time = time.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(elapsedMillis),
                TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedMillis))
        );

        return time;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SingelPlayer Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}

