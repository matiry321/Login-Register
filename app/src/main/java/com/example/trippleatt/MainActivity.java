package com.example.trippleatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mfirebaseauth;
    private FirebaseAuth.AuthStateListener mauthstatelistener;
    public static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );
        mfirebaseauth = FirebaseAuth.getInstance();
        mauthstatelistener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Toast.makeText(MainActivity.this, "You are now signed in", Toast.LENGTH_SHORT).show();
                   // onSignedInIntialised(user.getDisplayName());
                }
                else
                {
                    //user is signed out
                    //onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers )
                                    .build(),RC_SIGN_IN);

                }
            }
           /* private void onSignedOutCleanup() //this method unsets the username clear msg list and detach listener i.e tear down the ui
            {
                mUsername= ANONYMOUS;
                mMessageAdapter.clear();
                detachDatabaseReadListener();
            }*/
        };

    }
}