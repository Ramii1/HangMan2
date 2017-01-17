package com.example.ramyar.hangman;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.net.Uri;
        import android.os.CountDownTimer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.appindexing.Thing;
        import com.google.android.gms.common.api.GoogleApiClient;


public class SingelPlayerActivity extends AppCompatActivity implements View.OnClickListener
{

    private int life = 0;
    private Button gætBtn;
    private TextView textView;
    private EditText editText;
    private ImageView imageView;
    public TextView multiplayer_text;
    private GoogleApiClient client;
    CountDownTimer countDownTimer;
    int points = 3000;
    TextView timer;
    TextView timerpoints;
    Logic logik;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_player);

        timer = (TextView) findViewById(R.id.Timer);
        timerpoints = (TextView) findViewById(R.id.timerpoints);

        gætBtn = (Button) findViewById(R.id.gætBtn);
        gætBtn.setOnClickListener(this);

        logik = new Logic();
        logik.logStatus();
        pointTimer(180, timer);

        multiplayer_text = (TextView) findViewById(R.id.multiplayer_text);
        imageView = (ImageView) findViewById(R.id.imageView);
        gætBtn = (Button) findViewById(R.id.gætBtn);
        textView = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText);
        textView.setText("Du skal gætte dette ord: " + logik.getSynligtOrd() +
                "\nSkriv et bogstav og tryk 'Gæt'.\n");


        if (Constants.gameType.equals("SingelPlayer"))
        {
            multiplayer_text.setVisibility(View.INVISIBLE);
        }
        else
        {
            multiplayer_text.setVisibility(View.VISIBLE);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_genstart)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Vil du genstarte spillet?");

            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                    restart();
                }
            });

            builder.setNegativeButton("Nej", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void restart()
    {
        logik.nulstil();
        imageView.setImageResource(R.drawable.galge);
        editText.setText("");
        life = 0;
        editText.setHint("Indsæt Bogstav");
        textView.setText("Du skal gætte dette ord: " + logik.getSynligtOrd() +
                "\nSkriv et bogstav og tryk 'Gæt'.\n");
    }


    private void updateScreen()
    {
        textView.setText("Gæt ordet: " + logik.getSynligtOrd());
        textView.append("\n\nDu har " + logik.getAntalForkerteBogstaver() + " forkerte:" + logik.getBrugteBogstaver());

        if (!logik.erSidsteBogstavKorrekt())
        {
            life++;
            if (life >= 6)
            {
                life = 6;
            }

            switch (life)
            {
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

        if (logik.erSpilletVundet())
        {
            textView.append("\nDu har vundet");
            editText.setHint("Tryk Genstart for at spile igen");
            countDownTimer.cancel();
            saveHighScore();
        }
        if (logik.erSpilletTabt())
        {
            textView.setText("Du har tabt, det rigtige ord er : " + logik.getOrdet());
            editText.setHint("Tryk Genstart for at spile igen");
            countDownTimer.cancel();
        }
    }


    public void saveHighScore()
    {
        HighScoreLogic highScoreLogic = new HighScoreLogic(this);
        highScoreLogic.setHighScore(Constants.name, points);
        System.out.println(Constants.name +" " + points );
    }



    public Action getIndexApiAction()
    {
        Thing object = new Thing.Builder()
                .setName("SingelPlayer Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop()
    {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public void pointTimer(int Seconds,final TextView tv)
    {
        countDownTimer = new CountDownTimer(Seconds* 1000+1000, 500)
        {
            public void onTick(long millisUntilFinished)
            {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                points -= 10;
                timerpoints.setText("Point tilbage: " + points);
            }

            public void onFinish()
            {
                logik.isGameLost(true);
                textView.setText("Du har tabt, det rigtige ord er : " + logik.getOrdet());
                editText.setHint("Tryk Genstart for at spile igen");
            }

        }.start();
    }


    @Override
    public void onClick(View v)
    {
        if (v == gætBtn)
        {
            String bogstav = editText.getText().toString();

            if (bogstav.length() > 1)
            {
                editText.setError("Skriv ét bogstav");
                return;
            }

            logik.gætBogstav(bogstav);
            editText.setText("");
            editText.setError(null);
            updateScreen();
        }
    }
}

