package ru.msinchevskaya.testvkclient.post;

import org.json.JSONObject;

import ru.msinchevskaya.testvkclient.EnterActivity;
import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.R.id;
import ru.msinchevskaya.testvkclient.R.layout;
import ru.msinchevskaya.testvkclient.R.menu;
import ru.msinchevskaya.testvkclient.auth.Account;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PostActivity extends ActionBarActivity {
	
	private Account mAccount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		mAccount = Account.getInstance(this);
		RequestQueue queue = Volley.newRequestQueue(this);
		String url = "http://api.vk.com/oauth/logout" ;
		
		Fragment fragmentList = getSupportFragmentManager().findFragmentById(R.id.fragmentList);
		Fragment fragmentFull = getSupportFragmentManager().findFragmentById(R.id.fragmentFull);
		
		if (fragmentFull != null){
			Log.d(getString(R.string.app_tag), "Tablet");
		}
		else 
			Log.d(getString(R.string.app_tag), "Phone");
		//"https://api.vk.com/method/wall.get?owner_id=" 
		//+ mAccount.getUserId() +
		//"&v=5.2";
		
		
		setTitle(Account.getInstance(this).getUser().getFullName());
//		Log.i(getString(R.string.app_tag), url);
//		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//			  @Override
//			  public void onResponse(JSONObject response) {
//					Log.i(getString(R.string.app_tag), ""+response);
//					startEnterActivity();
//			  }
//			 }, new Response.ErrorListener() {
//
//			  @Override
//			  public void onErrorResponse(VolleyError error) {
//					Log.i(getString(R.string.app_tag), "error");
//			  }
//			 });
//		
//		queue.add(jsObjRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.logout, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.action_logout:
			mAccount.removeAccount();
			startEnterActivity();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startEnterActivity(){
		Intent intent = new Intent(this, EnterActivity.class);
		startActivity(intent);
		finish();
	}

}
