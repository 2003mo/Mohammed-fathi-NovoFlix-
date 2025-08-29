package com.example.project.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText et_email=findViewById(R.id.et_email);
        EditText et_password=findViewById(R.id.et_password);
        TextView btn_login=findViewById(R.id.btn_login);
        TextView tv_forget=findViewById(R.id.tv_forget);
        TextView tv_register=findViewById(R.id.tv_register);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=et_email.getText().toString();
                String password=et_password.getText().toString();
                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(login.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences prefs = getSharedPreferences("MyEmail", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_email", email);
                    editor.apply();

                    Toast.makeText(login.this, "Login successfull!", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(login.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_register = new Intent(login.this , signup.class);
                startActivity(i_register);
            }
        });
    }
}