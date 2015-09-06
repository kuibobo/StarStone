package apollo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import apollo.core.R;

/**
 * Created by Texel on 2015/7/28.
 */

public class CollapseActivity extends Activity {
    private Button mBtnRestart;
    private Button mBtnExit;

    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, CollapseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse);
        mBtnRestart = (Button) findViewById(R.id.collapse_restart);
        mBtnRestart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        mBtnExit = (Button) findViewById(R.id.collapse_exit);
        mBtnExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}