package com.google.android.gcm.demo.app;

import static com.google.android.gcm.demo.app.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.google.android.gcm.demo.app.CommonUtilities.CLIENT_ID_MESSAGE;
import static com.google.android.gcm.demo.app.CommonUtilities.SCOPE_MESSAGE;

import java.util.Map;

import com.google.android.gcm.GCMRegistrar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class AskAuthzActivity extends Activity {
	
	TextView authDisplay;
	
	String client_role;
	String client_id;
	String scope;
	private static final String TAG = "GCMIntentService";
	AsyncTask<Void, Void, Void> mHttpTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_authz);
		
		authDisplay = (TextView) findViewById(R.id.displayAuth);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
		
		Intent intent = getIntent();
	    client_role = intent.getStringExtra("scope");
	    client_id = intent.getStringExtra("client_id");
	    
	    //scope = intent.getStringExtra("scope");
	    
	    authDisplay.setText("你願意授權給榮民總醫院的" + client_role + "嗎?");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ask_authz, menu);
		return true;
	}
	
    public void sendAuthz(View view){
    	Log.i(TAG, "client_role_on_send: " + client_role);
    	mHttpTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
            	Map map = new HttpGetQuery().HTTPGetQuery("http://140.113.216.109:8080/OAuthAuthzServ/AndroidAuthzServer?resource_owner=yihao&client_id=" + client_id + "&client_role=" + client_role);
            	//authDisplay.append(map.get("status").toString() + ": " + map.get("content").toString());
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            	mHttpTask = null;
            }

        };
        mHttpTask.execute(null, null, null);
        
        Intent intent = new Intent(this, ShowAuthzActivity.class);
		startActivity(intent);
        
    	
    }
    
    public void cancelAuth(View view){
    	
    }
    
    
	
	private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String client_role = intent.getExtras().getString(SCOPE_MESSAGE);
            String client_id = intent.getExtras().getString(CLIENT_ID_MESSAGE);
            authDisplay.setText("你願意授權給榮民總醫院的" + client_role + "嗎?");
            //authDisplay.append(newMessage + "\n");
        }
    };

}
