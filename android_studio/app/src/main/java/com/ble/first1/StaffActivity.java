package com.ble.first1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ble.first1.model.Member;
import com.ble.first1.model.MemberRes;
import com.ble.first1.retrofit.MemberApi;
import com.ble.first1.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        MaterialButton goJoin = findViewById(R.id.joinBtn2);
        goJoin.setOnClickListener(view -> {
            Intent intent=new Intent(StaffActivity.this, JoinActivity.class);
            startActivity(intent);
        });
        initializeComponents();
    }
    private void initializeComponents() {
        TextInputEditText inputId = findViewById(R.id.form_textFieldID2);
        TextInputEditText inputPw = findViewById(R.id.form_textFieldPassword2);
        MaterialButton btnLogin = findViewById(R.id.loginBtn);

        RetrofitService retrofitService = new RetrofitService();
        MemberApi memberApi = retrofitService.getRetrofit().create(MemberApi.class);



        btnLogin.setOnClickListener(view -> {
            String id = String.valueOf(inputId.getText());
            String password = String.valueOf(inputPw.getText());

            Member member = new Member();
            member.setId(id);
            member.setPassword(password);

            if (id.trim().length() == 0 || password.trim().length() == 0 || id == null || password == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
                builder.setTitle("알림")
                        .setMessage("로그인 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else{
                Call<MemberRes> call = memberApi.login(member);
                call.enqueue(new Callback<MemberRes>() {
                    @Override
                    public void onResponse(Call<MemberRes> call, Response<MemberRes> response) {
                        Log.d("retrofit", "Data fetch success");
                        if(response.isSuccessful() && response.body() != null){
                            MemberRes result=response.body(); //받은데이터 저장
                            String resCode=result.getResultCode();

                            if(resCode.equals("200")){
                                Toast.makeText(StaffActivity.this, id + "님 환영합니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(StaffActivity.this, NoticeActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
                                builder.setTitle("알림")
                                        .setMessage("아이디 또는 비밀번호가 틀렸습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberRes> call, Throwable t) {
                        Toast.makeText(StaffActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(JoinActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);

                        AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
                        builder.setTitle("알림")
                                .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                });
            }

        });
    }
}