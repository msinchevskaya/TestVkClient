package ru.msinchevskaya.testvkclient.post;

import java.util.List;

import org.json.JSONObject;

import ru.msinchevskaya.testvkclient.EnterActivity;
import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.R.id;
import ru.msinchevskaya.testvkclient.R.layout;
import ru.msinchevskaya.testvkclient.R.menu;
import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader.IVkItemLoadListener;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import ru.msinchevskaya.testvkclient.vkitems.User;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;

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

public class PostActivity extends ActionBarActivity implements IVkItemLoadListener{
	
	private Account mAccount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		mAccount = Account.getInstance(this);
		RequestQueue queue = Volley.newRequestQueue(this);
		
		Fragment fragmentList = getSupportFragmentManager().findFragmentById(R.id.fragmentList);
		Fragment fragmentFull = getSupportFragmentManager().findFragmentById(R.id.fragmentFull);
		
		if (fragmentFull != null){
			Log.d(getString(R.string.app_tag), "Tablet");
		}
		else 
			Log.d(getString(R.string.app_tag), "Phone");
		setTitle(Account.getInstance(this).getUser().getFullName());
		
		VkItemLoader itemLoader = VkItemLoader.getInstance(this);
		itemLoader.loadPost(0, 10, this);
				
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

	@Override
	public void loadingSuccess(List<VkItem> listItem) {
		for (VkItem item : listItem){
			Post post = (Post)item;
			Log.i(getString(R.string.app_tag), "Likes = " + post.getLikes());
		}
	}

	@Override
	public void loadingError(String message) {
		Log.d(getString(R.string.app_tag), message);
	}

}
