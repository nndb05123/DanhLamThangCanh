package com.example.appfoody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfoody.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener{
    public static int KIEMTRA_PROVIDER_DANGNHAP=0;
    public static int REQUESTCODE_DANGNHAP_GOOGLE = 9;
    public static int RESULT_LOGIN_SUCCESS=10;
    public static int RESULT_LOGIN_FALDED=11;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    CallbackManager callbackManager;
    LoginManager loginManager;
    List<String> permissionFB = Arrays.asList("email","public_profile");

    Button btnLogin, btnLoginGG, btnLoginFB;
    TextView edEmail, edPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        loginManager=LoginManager.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        btnLogin = findViewById(R.id.btnLogin);
        btnLoginGG = findViewById(R.id.btnLoginGG);
        btnLoginFB = findViewById(R.id.btnLoginFB);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);

        callbackManager = CallbackManager.Factory.create();

        btnLogin.setOnClickListener(this);
        btnLoginGG.setOnClickListener(this);
        btnLoginFB.setOnClickListener(this);

        taoClientDangNhapGG();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnLogin:
                dangNhap();
                break;
            case R.id.btnLoginGG:
                dangNhapGG();
                break;
            case R.id.btnLoginFB:
                dangNhapFB();
                break;
        }
    }

    private void dangNhap() {
        String email =edEmail.getText().toString();
        String password=edPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Log.d("appcheck","dang nhap thanh cong");
                    Toast.makeText(getApplicationContext(),getString(R.string.thongbaodangnhapthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void taoClientDangNhapGG() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void dangNhapGG() {
        KIEMTRA_PROVIDER_DANGNHAP = 1;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUESTCODE_DANGNHAP_GOOGLE);
    }

    private void dangNhapFB() {
        loginManager.logInWithReadPermissions(this,permissionFB);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("appcheck", "login fb success");
                KIEMTRA_PROVIDER_DANGNHAP=2;
                String tokenID=loginResult.getAccessToken().getToken();
                chungThucDangNhapFireBase(tokenID);
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUESTCODE_DANGNHAP_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String tokenID = account.getIdToken();
                chungThucDangNhapFireBase(tokenID);
            } catch (ApiException e) {
                Log.w("loi", "Google sign in failed", e);
                // ...
            }
        }
        else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void chungThucDangNhapFireBase(String tokenID)
    {
        if(KIEMTRA_PROVIDER_DANGNHAP==1)
        {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            firebaseAuth.signInWithCredential(authCredential);
        }
        else if(KIEMTRA_PROVIDER_DANGNHAP==2)
        {
            AuthCredential authCredential= FacebookAuthProvider.getCredential(tokenID);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

            Log.d("appcheck","login success");
            Intent returnIntent = new Intent();
            setResult(RESULT_LOGIN_SUCCESS,returnIntent);
            finish();

        }

    }
}
