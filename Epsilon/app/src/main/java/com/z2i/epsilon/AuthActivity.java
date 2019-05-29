package com.z2i.epsilon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.z2i.epsilon.Utils.NetworkState;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity {

    private Button verify;
    private EditText number;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken token;
    private String verificationid;

    Timer timer;
    TextView authTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportActionBar().hide();

        FirebaseApp.initializeApp(getApplicationContext());

        auth = FirebaseAuth.getInstance();

        verify = (Button) findViewById(R.id.btnverify);
        number = (EditText) findViewById(R.id.etnumber);
        progressBar = (ProgressBar) findViewById(R.id.pbsignin);
        authTimer=(TextView)findViewById(R.id.authtimer);
        authTimer.setVisibility(View.INVISIBLE);



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pnumber = "+91" + number.getText().toString().trim();

                if (NetworkState.getInstance(v.getContext()).isOnline()) {
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            pnumber,
                            60,
                            TimeUnit.SECONDS,
                            AuthActivity.this,
                            callbacks
                    );
                    Toast.makeText(AuthActivity.this, "Sending OTP", Toast.LENGTH_SHORT).show();
                starttimer();


                }else{
                    Toast.makeText(AuthActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(AuthActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
                authTimer.setVisibility(View.GONE);
                signin(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                authTimer.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AuthActivity.this, "Code Sent failed", Toast.LENGTH_LONG).show();
                verify.setClickable(true);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationid = s;
                token = forceResendingToken;

            }
        };


    }

    private void signin(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    authTimer.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    verify.setClickable(true);
                    Toast.makeText(AuthActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void starttimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            int second = 60;

            @Override
            public void run() {
                if (second <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //authTimer.setText("RESEND CODE");
                            verify.setText("ReSend OTP");
                            verify.setClickable(true);
                            timer.cancel();
                            authTimer.setVisibility(View.INVISIBLE);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            authTimer.setText("00:" + second--);
                            verify.setClickable(false);
                            authTimer.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        }, 0, 1000);
    }

}
