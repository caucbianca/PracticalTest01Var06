package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class ProcessingThread extends Thread {
    Context context;
    private boolean isRunning = true;

    public ProcessingThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Log.d("CLICK", "Thread has started!");
        while (isRunning) {
            int generated = new Random().nextInt() % 10;
            while (generated < 0 || generated >= 10)
                generated = new Random().nextInt() % 10;

            sleep();

            sendMessage(String.valueOf(generated));
        }
        Log.d("CLICK", "Thread has stopped!");
    }

    private void sendMessage(String message) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_TIME);
        intent.putExtra(Constant.DATA, message);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
