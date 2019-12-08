package com.seoul.ddroad.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seoul.ddroad.MainActivity;
import com.seoul.ddroad.R;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout RelativeLayout_login;
    TextView TextView1;
    String hello = "Hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RelativeLayout_login = findViewById(R.id.RelativeLayout_login);
        RelativeLayout_login.setClickable(true);
        RelativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
