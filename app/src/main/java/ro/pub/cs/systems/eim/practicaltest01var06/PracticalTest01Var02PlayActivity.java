package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.file.Files;
import java.util.Random;


public class PracticalTest01Var02PlayActivity extends AppCompatActivity {
    int serviceStatus = Constant.STOPPED_SERVICE;

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView text = findViewById(R.id.guess);
            String s = intent.getStringExtra(Constant.DATA);
            text.setText(s);

            Log.d("[Message]", intent.getStringExtra(Constant.DATA));
        }
    }


    GenerateListener generateListener = new GenerateListener();
    private class GenerateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int generated = new Random().nextInt() % 10;
            while (generated < 0 || generated >= 10)
                generated = new Random().nextInt() % 10;

            Log.d("TAG", String.valueOf(generated));
            TextView text = findViewById(R.id.guess);
            text.setText(String.valueOf(generated));
        }
    }

    CheckListener checkListener = new CheckListener();
    private class CheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            if (intent != null && intent.getExtras().containsKey("number")) {
                int number = Integer.parseInt(intent.getExtras().get("number").toString());

                TextView guess = findViewById(R.id.guess);
                int generated = Integer.parseInt(guess.getText().toString());

                if (number == generated) {
                    TextView score = findViewById(R.id.score);
                    int oldScore = Integer.parseInt(score.getText().toString());
                    int newScore = oldScore + 1;
                    score.setText(String.valueOf(newScore));
                }

            }
        }
    }

    BackListener backListener = new BackListener();
    private class BackListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06ChooseNumber.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);

        Button generateButton = findViewById(R.id.generate);
        generateButton.setOnClickListener(generateListener);

        Button checkButton = findViewById(R.id.check);
        checkButton.setOnClickListener(checkListener);

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(backListener);

        TextView guess = findViewById(R.id.guess);
        TextView score = findViewById(R.id.score);

        if (savedInstanceState != null) {
            if (savedInstanceState.get("guess") != null) {
                guess.setText(savedInstanceState.get("guess").toString());
            }

            if (savedInstanceState.get("score") != null) {
                score.setText(savedInstanceState.get("score").toString());
            }
        }

        intentFilter.addAction(Constant.ACTION_TIME);

        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().startService(intent);

        serviceStatus = Constant.STARTED_SERVICE;
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        TextView guess = findViewById(R.id.guess);
        TextView score = findViewById(R.id.score);
        savedInstanceState.putString("guess", String.valueOf(guess.getText()));
        savedInstanceState.putString("score", String.valueOf(score.getText()));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        TextView guess = findViewById(R.id.guess);
        TextView score = findViewById(R.id.score);

        if (savedInstanceState != null) {
            if (savedInstanceState.get("guess") != null) {
                guess.setText(savedInstanceState.get("guess").toString());
            }

            if (savedInstanceState.get("score") != null) {
                guess.setText(savedInstanceState.get("score").toString());
            }
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
