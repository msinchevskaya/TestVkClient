package ru.msinchevskaya.testvkclient;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.post.PostActivity;
import ru.msinchevskaya.testvkclient.utils.JSONParser;
import ru.msinchevskaya.testvkclient.vkitems.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class EnterActivity extends Activity {

	private static final int LOGIN_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter);

		if (Account.getInstance(this).isInit()){
			setTitle(Account.getInstance(this).getUser().getFullName());
			startPostActivity();
		}
	}

	public void onEnterClick(View v){
		startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_CODE);
	}

	private void getUserName(){
		RequestQueue queue = Volley.newRequestQueue(this);
		String url = "https://api.vk.com/method/users.get?user_ids=" 
				+ Account.getInstance(this).getUserId() +
				"&fields=photo_50&&v=5.2";
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, 
				new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				User user = null;
				try {
					user = (User)JSONParser.parseUser(response).get(0);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Account.getInstance(getApplicationContext()).setUser(user);
				startPostActivity();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(getString(R.string.app_tag), error.toString());
			}
		});

		queue.add(jsObjRequest);
	}

	private void startPostActivity(){
		Intent intent = new Intent(this, PostActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
		case LOGIN_CODE:
			if (resultCode == RESULT_OK){
				String accessToken = data.getStringExtra(LoginActivity.INTENT_ACESS_TOKEN);
				int expiressIn = data.getIntExtra(LoginActivity.INTENT_EXPIRESIN, 0);
				String userId = data.getStringExtra(LoginActivity.INTENT_USERID);
				Account.getInstance(this).saveAccountParams(accessToken, expiressIn, userId);
				getUserName();
			}
			break;

		default:
			break;
		}
	}
}
