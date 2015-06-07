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
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener,
                                            View.OnClickListener{
    private Button googleSignInButton;
    private boolean isSignInClicked;
    private boolean isIntentInProgress;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_login);

        findViewById(R.id.google_sign_in_button).setOnClickListener(this);
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
        if(!isIntentInProgress){
            if(isSignInClicked && connectionResult.hasResolution()){
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    isIntentInProgress = true;
                }catch(SendIntentException e) {
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
}
