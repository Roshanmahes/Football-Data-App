package nl.mprog.a10973710.pset6_roshanmahes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String email;
    String password;

    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        setAuthListener();
    }

    public void setAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createUser(View view) {

        Log.d("CREATE", "IN CREATE USER");
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // check whether email and password are long enough
        isValid(email, password);

        if (password.length() >= 6 && !email.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("create user", "createUserWithEmail:onComplete:"
                                    + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "created user: " + email,
                                        Toast.LENGTH_SHORT).show();

                                loadData();
                            }
                        }
                    });

            // remove password text
            passwordEditText.getText().clear();
        }
    }

    public void login(View view) {

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // check whether email and password are long enough
        isValid(email, password);

        if (password.length() >= 6 && !email.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("sign in", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("email", "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Logged in user: " + email,
                                        Toast.LENGTH_SHORT).show();

                                loadData();
                            }
                        }
                    });

            // remove password text
            passwordEditText.getText().clear();
        }
    }

    public void isValid(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Your password is too short!", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData() {
        CompetitionsAsyncTask asyncTask = new CompetitionsAsyncTask(this);
        asyncTask.execute("");
    }

    // start CompetitionsActivity
    public void competitionStartIntent(String result) {
        Intent intent = new Intent(this, CompetitionsActivity.class);
        intent.putExtra("data", result);
        this.startActivity(intent);
    }
}
