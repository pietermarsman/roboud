package edu.radboud.AI.roboud;

        import android.app.Activity;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import com.wowwee.robome.RoboMe;
        import com.wowwee.robome.RoboMeCommands;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class Roboud extends Activity {

    private RoboMeRobot robot;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        robot = new RoboMeRobot(this);
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
}