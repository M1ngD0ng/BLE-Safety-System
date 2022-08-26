package com.ble.first1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ble.first1.model.Member;
import com.ble.first1.retrofit.MemberApi;
import com.ble.first1.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        
        initializeComponents();
    }

    private void initializeComponents() {
        TextInputEditText inputEditId = findViewById(R.id.form_textFieldID);
        TextInputEditText inputEditPassword = findViewById(R.id.form_textFieldPassword);
        MaterialButton buttonJoin = findViewById(R.id.form_btnJoin);

        RetrofitService retrofitService = new RetrofitService();
        MemberApi memberApi = retrofitService.getRetrofit().create(MemberApi.class);

        buttonJoin.setOnClickListener(view -> {
            String id = String.valueOf(inputEditId.getText());
            String password = String.valueOf(inputEditPassword.getText());

            Member member = new Member();
            member.setId(id);
            member.setPassword(password);

            memberApi.join(member)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(JoinActivity.this, "Save successful!!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JoinActivity.this, NoticeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(JoinActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(JoinActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });
        });
    }
}