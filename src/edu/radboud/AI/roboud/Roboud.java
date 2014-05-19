package edu.radboud.AI.roboud;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.SurfaceView;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import com.wowwee.robome.RoboMe;
        import com.wowwee.robome.RoboMeCommands;

        import java.util.Timer;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class Roboud extends Activity {

    private RoboMeRobot robot;
    private Handler handler;
    private TextView logView;
    private ScrollView logScrollView;
    private ImageView imageView;

    AndroidCamera cam;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        logView = (TextView) findViewById(R.id.output);
        logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
        imageView = (ImageView) findViewById(R.id.imageView);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        Button button = (Button) findViewById(R.id.button);

        // handler to display received event
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // display the received event
                if (msg.what == 0x99 )
                    logView.append(msg.obj + "\n");
                if (msg.what == 0x98 )
                    imageView.setImageBitmap((Bitmap) msg.obj);
                logScrollView.smoothScrollTo(0, logView.getHeight());
            }
        };

        robot = new RoboMeRobot(this, handler);

        // show version
        logView.append("Version " + robot.getLibVersion() + "\n");

        cam = new AndroidCamera(imageView, surfaceView, button);
    }

    /** Start listening to events from the gun when the app starts or resumes from background */
    @Override
    public void onResume(){
        super.onResume();
        robot.start();
    }

    /** Stop listening to events from the gun when the app goes into the background */
    @Override
    public void onStop(){
        super.onStop();

        // stop listening to events from the headset
        robot.stop();
    }

    public void showText(String text) {
        Message msg = new Message();
        msg.what = 0x99;
        msg.obj = text;
        handler.sendMessage(msg);
    }
}