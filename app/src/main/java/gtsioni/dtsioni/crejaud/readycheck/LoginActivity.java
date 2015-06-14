package gtsioni.dtsioni.crejaud.readycheck;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import java.util.Set;

public class LoginActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener,
                                            GoogleApiClient.ServerAuthCodeCallbacks,
                                            View.OnClickListener{
    private SignInButton googleSignInButton;
    private boolean isSignInClicked;
    private boolean isIntentInProgress;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient googleApiClient;
    private final String SERVER_CLIENT_ID = "readycheck-969";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_login);


        // googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).requestServerAuthCode(SERVER_CLIENT_ID, this).build();
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API).addScope(Scopes.PLUS_ME).addScope(Scopes.PLUS_LOGIN).build();
        googleSignInButton = (SignInButton)findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(this);

    }


    @Override
    public void onConnected(Bundle bundle) {
        isSignInClicked = false;
        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.google_sign_in_button && !googleApiClient.isConnecting()){
            isSignInClicked = true;
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        int erCode = connectionResult.getErrorCode();
        Toast.makeText(this, "Connection failed: Error Code " + erCode, Toast.LENGTH_LONG).show();
        if(!isIntentInProgress){
            if(isSignInClicked && connectionResult.hasResolution()){
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    isIntentInProgress = true;
                }catch(IntentSender.SendIntentException e) {
                    isIntentInProgress = false;
                    googleApiClient.connect();
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent){
        if(requestCode == RC_SIGN_IN){
            if(responseCode != RESULT_OK){
                isSignInClicked = false;
            }
            isIntentInProgress = false;
            if(!googleApiClient.isConnected()){
                googleApiClient.reconnect();
            }
        }
    }

    @Override
    public CheckResult onCheckServerAuthorization(String s, Set<Scope> set) {
        return null;
    }

    @Override
    public boolean onUploadServerAuthCode(String s, String s1) {
        return false;
    }
}
