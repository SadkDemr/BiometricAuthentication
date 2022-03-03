package com.info.biometricauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;
import android.annotation.SuppressLint;
import android.hardware.biometrics.BiometricManager;

import android.hardware.biometrics.BiometricPrompt.AuthenticationCallback;
import com.info.biometricauthentication.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

import static android.hardware.biometrics.BiometricPrompt.*;

public class MainActivity extends AppCompatActivity {
    //UI Views
    private TextView faceText;
    private Button faceButton;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //içidekiler UI views
        faceText=findViewById(R.id.faceText);
        faceButton=findViewById(R.id.faceButton);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode,@NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //hatalı giriş ve durdurma
                faceText.setText("Hata:"+errString);
                Toast.makeText(MainActivity.this,"Hatalı Giriş:"+errString, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //giriş başarılı
                faceText.setText("Giriş Başarılı!");
                Toast.makeText(MainActivity.this,"Giriş Başarılı!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //daoğralama başarısız
                faceText.setText("Giriş Hatalı!");
                Toast.makeText(MainActivity.this,"Giriş Hatalı!",Toast.LENGTH_SHORT).show();
            }
        });

        //
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Kimlik Doğrulama")
                .setSubtitle("Yüz ya da parmak izi ile giriş yapın")
                .setNegativeButtonText("Kullanıcı Şifersi")
                .build();

        //Doğrulamaya basınca, doğrulamayı başlatma
        faceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //yetkilendirme iletişim kutusu
                biometricPrompt.authenticate(promptInfo);



            }
        });
    }
}