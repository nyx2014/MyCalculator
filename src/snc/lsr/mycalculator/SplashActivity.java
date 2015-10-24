package snc.lsr.mycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import snc.lsr.util.DrawView;

/**
 * Created by nyx2015 on 2015/5/20.
 */
public class SplashActivity extends Activity {
    private int DelayTime = 400;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView mView=new DrawView(this);
        setContentView(mView);
//        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent mIntent = new Intent(SplashActivity.this,Main.class);
                SplashActivity.this.startActivity(mIntent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        }, DelayTime);
    }
}