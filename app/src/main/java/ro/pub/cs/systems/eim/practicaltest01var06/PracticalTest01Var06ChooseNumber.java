package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PracticalTest01Var06ChooseNumber extends AppCompatActivity {
    public final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;


    PlayListener playListener = new PlayListener();
    private class PlayListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            TextView text = findViewById(R.id.number);
            if (text.getText() != null && !text.getText().toString().isEmpty()) {
                String numberText = text.getText().toString();
                int number = Integer.parseInt(numberText);

                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02PlayActivity.class);

                intent.putExtra("number", text.getText());
                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_choose_number);

        Button playButton = findViewById(R.id.play);
        playButton.setOnClickListener(playListener);

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        TextView text = findViewById(R.id.number);
        savedInstanceState.putString("number", String.valueOf(text.getText()));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        if (savedInstanceState != null) {
            TextView text = findViewById(R.id.number);
            if (savedInstanceState.get("number") != null) {
                text.setText(savedInstanceState.get("number").toString());
            }
        }
    }




}
