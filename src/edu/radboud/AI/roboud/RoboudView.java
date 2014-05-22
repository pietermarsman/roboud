package edu.radboud.AI.roboud;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.content.pm.ResolveInfo;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.speech.RecognizerIntent;
        import android.view.SurfaceView;
        import android.widget.*;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Observable;
        import java.util.Observer;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class RoboudView extends Activity implements Observer {

    private TextView logView;
    private ScrollView logScrollView;
    private SurfaceView surfaceView;
    private Button button;

    RoboudController controller;
    private RoboudModel model;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // display the received event
            if (msg.what == 0x99 )
                logView.setText((String) msg.obj);
            logScrollView.smoothScrollTo(0, logView.getHeight());
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        logView = (TextView) findViewById(R.id.output);
        logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);

        controller = new RoboudController(this, surfaceView, handler);

        button.setOnClickListener(controller);
        model = controller.getModel();
        model.addObserver(this);

        // show version
        logView.append("Version " + model.getLibVersion() + "\n");
    }

    /** Start listening to events from the gun when the app starts or resumes from background */
    @Override
    public void onResume(){
        super.onResume();
        controller.startListeners();
    }

    /** Stop listening to events from the gun when the app goes into the background */
    @Override
    public void onStop(){
        super.onStop();
        controller.stopListeners();
    }

    @Override
    public void update(Observable observable, Object data) {
        controller.showText(model.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Send speech data to the controller
        if (requestCode == AndroidMicrophone.REQUEST_CODE) {
            controller.speechReceived(requestCode, resultCode, data);
        }
    }
}