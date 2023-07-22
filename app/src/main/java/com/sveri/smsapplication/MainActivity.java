package com.sveri.smsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etMob, etMsg;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseWidgets();
        eventHandler();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestSmsPermission();
        } else {
            // Granted, you can start you async task here
            eventHandler();
        }
    }

    //initialise
    protected void initialiseWidgets(){
        etMob = findViewById(R.id.editTextMobile);
        etMsg = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.buttonSend);

    }

    protected void eventHandler(){

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = etMob.getText().toString().trim();
                String msg = etMsg.getText().toString().trim();

                if (phone.isEmpty() || msg.isEmpty()){

                    Toast.makeText(MainActivity.this, "Fields can't be empty",
                            Toast.LENGTH_SHORT).show();
                }else {

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phone,null,msg,null,null);
                }
                etMob.setText(null);
                etMsg.setText(null);
            }
        });
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
}