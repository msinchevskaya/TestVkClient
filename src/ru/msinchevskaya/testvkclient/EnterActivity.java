package ru.msinchevskaya.testvkclient;

import java.util.List;

import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.post.PostActivity;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader.IVkItemLoadListener;
import ru.msinchevskaya.testvkclient.vkitems.User;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class EnterActivity extends Activity implements IVkItemLoadListener {

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
		VkItemLoader loader = VkItemLoader.getInstance(this);
		loader.loadUser(Account.getInstance(this).getUserId(), this);
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

	@Override
	public void loadingSuccess(List<VkItem> listItem) {
		if (!listItem.isEmpty()){
			User user = (User)listItem.get(0);
			Account.getInstance(this).setUser(user);
			startPostActivity();
		}
	}

	@Override
	public void loadingError(String message) {
		Log.e(getString(R.string.app_tag), message);
	}
}
