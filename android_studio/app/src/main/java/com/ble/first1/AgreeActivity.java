package com.ble.first1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AgreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);

        Button btn1=(Button) findViewById(R.id.AgreeBtn);
        Button btn2=(Button) findViewById(R.id.DisagreeBtn);

        TextView textView1 = (TextView)findViewById(R.id.agreeDetail);
        textView1.setText(getString(R.string.agree_string));

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(AgreeActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(AgreeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}