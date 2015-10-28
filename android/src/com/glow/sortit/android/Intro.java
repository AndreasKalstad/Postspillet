package com.glow.sortit.android;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Intro extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
	public static Button start;
	public static Button leaderboard;
	private GoogleApiClient mGoogleApiClient;
	private int REQUEST_LEADERBOARD = 100;
	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInFlow = true;
	private boolean mSignInClicked = false;
	private Button credit;
    private Button exit;
	private static int RC_SIGN_IN = 9001;
    private static final int REQUEST_CODE_RESOLVE_ERR = 7;
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
	private boolean mIntentInProgress;

	@SuppressLint("InlinedApi")
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);      
	      setContentView(R.layout.intro);

	      mGoogleApiClient = new GoogleApiClient.Builder(this)
	      .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
          .addApi(Games.API).addScope(Games.SCOPE_GAMES)
          .addApi(Drive.API).addScope(Drive.SCOPE_APPFOLDER)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .build();

	      start = (Button) findViewById(R.id.startgame);
	      start.setOnClickListener(new OnClickListener() {
	    	  public void onClick(View v) {
					startGame();
				}
			});
	      leaderboard = (Button) findViewById(R.id.leaderboard);
	      leaderboard.setOnClickListener(new OnClickListener() {	
	    	  public void onClick(View v) {
                  mGoogleApiClient.connect();
                  System.out.println(mGoogleApiClient.isConnected());
                  if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                      startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getResources().getString(R.string.event_leaderboard)), REQUEST_LEADERBOARD);
                  }
				}
			});
	      
	      credit = (Button) findViewById(R.id.credit);
	      credit.setOnClickListener(new OnClickListener() {	
	    	  public void onClick(View v) {
	    		  signInClicked();
				}
			});
	      
	    /*  achivements = (Button) findViewById(R.id.achivements);
	      achivements.setOnClickListener(new OnClickListener() {	
	    	  public void onClick(View v) {
	    		  startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), REQUEST_ACHIEVEMENTS);
				}
			}); */

           exit = (Button) findViewById(R.id.exitgame);
           exit.setOnClickListener(new OnClickListener() {
               public void onClick(View v) {
                   System.exit(0);
               }
           });
	      
	      if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
	    	    // Call a Play Games services API method, for example:
	    	    Games.Achievements.unlock(mGoogleApiClient, getResources().getString(R.string.achievement_score_100_points));
	    	} else {
	    	    // Alternative implementation (or warn user that they must
	    	    // sign in to use this feature)
	    	}
	   }
	   
	   public void startGame(){
		   Intent i = new Intent(this, GameLauncher.class);
		   startActivity(i);
	   }
	   
	   @Override
	   protected void onStart() {
	       super.onStart();
	       mGoogleApiClient.connect();
	   }

	   @Override
	   protected void onStop() {
	       super.onStop();
	       if (mGoogleApiClient.isConnected()) {
	           mGoogleApiClient.disconnect();
	       }
	   }
	
	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionSuspended(int cause) {
		 mGoogleApiClient.connect();
		
	}
	   
	// Call when the sign-in button is clicked
	private void signInClicked() {
	    mSignInClicked = true;
	    mGoogleApiClient.connect();
	}

	// Call when the sign-out button is clicked
	private void signOutclicked() {
	    mSignInClicked = false;
	    Games.signOut(mGoogleApiClient);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}

    /*@Override
    public void onConnectionFailed(ConnectionResult result) {
		if (!mIntentInProgress && result.hasResolution()) {
			try {
				System.out.println(result.getErrorCode() + " " +result.getResolution());
				mIntentInProgress = true;
				result.startResolutionForResult(this, // your activity
						RC_SIGN_IN);
			} catch (IntentSender.SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				System.out.println("Intentsender");
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
    }

	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		System.out.println("Intentsender2");
		if (requestCode == RC_SIGN_IN) {
			mIntentInProgress = false;
			System.out.println("Intentsender3");
			if (!mGoogleApiClient.isConnecting()) {
				System.out.println("Intentsender4");
				mGoogleApiClient.connect();
			}
		}
	} */
}

