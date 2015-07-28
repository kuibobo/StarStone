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
    private Button btnRestart, btnExit;

    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, CollapseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse);
        setTitle("FC");
        btnRestart = (Button) findViewById(R.id.collapse_restart);
        btnExit = (Button) findViewById(R.id.collapse_exit);
        btnRestart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}