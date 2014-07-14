package ru.msinchevskaya.testvkclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {
	
	public static final String INTENT_ACESS_TOKEN = "accesstoken";
	public static final String INTENT_EXPIRESIN = "expiresin";
	public static final String INTENT_USERID = "userid";
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mContext = this;
		
		String url = "https://oauth.vk.com/authorize?client_id=" + getString(R.string.client_id) + "&redirect_uri=" + getString(R.string.redirect_uri) + 
				"&scope=" + getString(R.string.scope) + "&display=mobile&v=5.23&response_type=token&revoke=1 ";
		
		Log.d(getString(R.string.app_tag), url);	
		
	    CookieSyncManager.createInstance(mContext);
	    CookieManager cookieManager = CookieManager.getInstance();
	    cookieManager.removeAllCookie();
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.loadUrl(url);
		webview.setWebViewClient(new AuthWebViewClient());		
	}
	
	private void parseUrl(String url){
		if (url.contains("error")){
			Log.e(getString(R.string.app_tag), url);
			sendErrorResult();
		}
		
		String tokens[] = url.split("[&=]");
		
		String accessToken = tokens[1];
		int expiresIn = Integer.valueOf(tokens[3]);
		String userId = tokens[5];
		
		Log.i(getString(R.string.app_tag), "Access token = " + accessToken);
		Log.i(getString(R.string.app_tag), "Expires in = " + expiresIn);
		Log.i(getString(R.string.app_tag), "User id = " + userId);
		
		sendSuccessResult(accessToken, expiresIn, userId);
	}
	
	private void sendErrorResult(){
		Intent intent = new Intent();
		setResult(RESULT_CANCELED, intent);
		finish();
	}
	
	private void sendSuccessResult(String accessToken, int expiressIn, String userId){
		Intent intent = new Intent();
		intent.putExtra(INTENT_ACESS_TOKEN, accessToken);
		intent.putExtra(INTENT_EXPIRESIN, expiressIn);
		intent.putExtra(INTENT_USERID, userId);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private class AuthWebViewClient extends WebViewClient {
		
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    {
			Log.d(getString(R.string.app_tag), url);	
	    	view.loadUrl(url);
	    	if (url.startsWith(mContext.getString(R.string.redirect_uri)))
	    		LoginActivity.this.parseUrl(url);
	        return true;
	    }
	}
	

}
