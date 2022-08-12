package com.ble.first1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.content.pm.PackageManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ble.first1.model.Member;
import com.ble.first1.model.Sos;
import com.ble.first1.retrofit.MemberApi;
import com.ble.first1.retrofit.RetrofitService;
import com.ble.first1.retrofit.SosApi;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION=1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION=99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initializeComponents();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.d("ERROR","블루투스를 지원하지 않는 기기입니다.");
        }

        TextView textView1 = (TextView)findViewById(R.id.noticeDetail);
        textView1.setText(getString(R.string.notice_string));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("블루투스에 대한 액세스가 필요합니다");
                builder.setMessage("어플리케이션이 비콘을 감지 할 수 있도록 위치 정보 액세스 권한을 부여하십시오.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            requestPermissions(
                                    new String[]{
                                            Manifest.permission.BLUETOOTH,
                                            Manifest.permission.BLUETOOTH_SCAN,
                                            Manifest.permission.BLUETOOTH_ADVERTISE,
                                            Manifest.permission.BLUETOOTH_CONNECT
                                    },
                                    1);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                    new String[]{
                                            Manifest.permission.BLUETOOTH

                                    },
                                    1);
                        }
                        //requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION );
                    }
                });
                builder.show();
            }
            else{
                Log.d("OK","블루투스 권한이 이미 부여되었습니다.");
            }
        }
    }

    private void initializeComponents() {
        MaterialButton buttonSos = findViewById(R.id.form_btnSos);

        RetrofitService retrofitService = new RetrofitService();
        SosApi sosApi = retrofitService.getRetrofit().create(SosApi.class);

        buttonSos.setOnClickListener(view -> {
            Sos sos = new Sos();
            sos.setNow(1);

            sosApi.sign(sos)
                    .enqueue(new Callback<Sos>() {
                        @Override
                        public void onResponse(Call<Sos> call, Response<Sos> response) {
                            Toast.makeText(NoticeActivity.this, "Sign successful!!!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Sos> call, Throwable t) {
                            Toast.makeText(NoticeActivity.this, "Sign failed!!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(NoticeActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("디버깅", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한 제한");
                    builder.setMessage("위치 정보 및 액세스 권한이 허용되지 않았으므로 블루투스를 검색 및 연결할수 없습니다.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

}