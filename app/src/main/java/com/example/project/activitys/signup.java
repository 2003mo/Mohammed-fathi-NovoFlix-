package com.example.project.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView btn_sign_up = findViewById(R.id.btn_sign_up);
        ImageView iv_icon_google = findViewById(R.id.iv_icon_google);
        ImageView iv_icon_facebooke = findViewById(R.id.iv_icon_facebooke);
        TextView login_link = findViewById(R.id.login_link);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_btn_sign_up = new Intent(signup.this , login.class);
                startActivity(i_btn_sign_up);

            }
        });
        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_link = new Intent(signup.this , login.class);
                startActivity(login_link);
            }
        });



    }
}