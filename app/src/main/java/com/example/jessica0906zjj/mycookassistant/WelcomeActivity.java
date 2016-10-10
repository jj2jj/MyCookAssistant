package com.example.jessica0906zjj.mycookassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jessica0906zjj on 2016-09-26.
 */

public class WelcomeActivity extends AppCompatActivity{
    private Button sign_in;
    private Button register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sign_in = (Button) findViewById(R.id.login_welcome);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        register = (Button) findViewById(R.id.register_welcome);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}
