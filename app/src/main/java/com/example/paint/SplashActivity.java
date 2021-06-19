package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private View googleSignIn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        googleSignIn = findViewById(R.id.sign_in_button);
        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

            splash_loged();
       // Splash_Time();

    }

   void createnewuser(){

   }

   void Splash_Time(){

       Thread thread = new Thread(){
           @Override
           public void run() {

               try {
                   sleep(4000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   startActivity(new Intent(SplashActivity.this,MainActivity.class));
                   finish();
               }

           }
       };

       thread.start();

   }

   void splash_loged(){
        googleSignIn.setVisibility(View.GONE);
       Thread thread = new Thread(){
           @Override
           public void run() {
               try {
                   sleep(4000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   startActivity(new Intent(SplashActivity.this,MainActivity.class));
                   finish();
               }

           }
       };
   }

}