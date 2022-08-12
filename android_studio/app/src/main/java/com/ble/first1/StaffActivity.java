package com.ble.first1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Button join_btn=(Button) findViewById(R.id.joinBtn);
        Button login_btn=(Button) findViewById(R.id.loginBtn);

        //EditText id_edit = (EditText)findViewById(R.id.id_edit);
        //EditText pw_edit = (EditText)findViewById(R.id.pw_edit);

        join_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(StaffActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(StaffActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }
}