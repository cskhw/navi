package com.seoul.ddroad.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.seoul.ddroad.MainActivity;
import com.seoul.ddroad.R;

public class IntroActivity extends Activity  {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 3000);
    }
    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            IntroActivity.this.finish();
        }
    }
    @Override
    public void onBackPressed(){

    }
}

