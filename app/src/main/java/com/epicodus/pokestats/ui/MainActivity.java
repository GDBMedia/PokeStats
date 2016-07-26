package com.epicodus.pokestats.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.epicodus.pokestats.Constants;
import com.epicodus.pokestats.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String encryptedMsg = "";
        try {
            encryptedMsg = AESCrypt.encrypt(Constants.PASSKEY, "fuck");
        }catch (GeneralSecurityException e){
            //handle error
        }
        Log.d(TAG, "onCreateEncrypt: " + encryptedMsg);
        String messageAfterDecrypt = "";
        try {
            messageAfterDecrypt = AESCrypt.decrypt(Constants.PASSKEY, encryptedMsg);
        }catch (GeneralSecurityException e){
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
        Log.d(TAG, "onCreateDecrypt: " + messageAfterDecrypt);

        try {
            encryptedMsg = AESCrypt.encrypt(Constants.EMAILKEY, "fuck");
        }catch (GeneralSecurityException e){
            //handle error
        }
        Log.d(TAG, "onCreateEncrypt: " + encryptedMsg);

        Log.d(TAG, "onCreateDecrypt: " + messageAfterDecrypt);
    }
}
