package ru.msinchevskaya.testvkclient.utils;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;

public class VkItemLoader {
	
	private static VkItemLoader mInstance;
	private Context mContext;
	
	private int status;
	public static final int LOADED = 100;
	public static final int STOPPED = 101;
	
	
	private VkItemLoader(Context context){
		mContext = context;
		status = STOPPED;
	}
	
	public static VkItemLoader getInstance(Context context){
		if (mInstance == null)
			mInstance = new VkItemLoader(context.getApplicationContext());
		return mInstance;
	}
	
	public interface IVkItemLoadListener{
		public void loadingSuccess(List<VkItem> listItem);
		public void loadingError(String message);
	}
	
	public int getStatus(){
		return status;
	}
	/**
	 * 
	 * @param userId
	 * @param listener
	 * ��������� ������ � ������������, ���������� callback
	 */
	public void loadUser(final String userId, final IVkItemLoadListener listener){
		
		status = LOADED;
		
		RequestQueue queue = Volley.newRequestQueue(mContext);
		String url = "https://api.vk.com/method/users.get?user_ids=" 
				+ Account.getInstance(mContext).getUserId() +
				"&fields=photo_50&&v=5.2";
		
		final Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				try {
					List<VkItem> listUser = JSONParser.parseUser(response);
					listener.loadingSuccess(listUser);
					status = STOPPED;
				} catch (JSONException e){
					listener.loadingError(e.getLocalizedMessage());
					status = STOPPED;
				}
			}
		};
		
		final Response.ErrorListener errorListener = new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.loadingError(error.getLocalizedMessage());
				status = STOPPED;
			}
		};
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
		queue.add(jsObjRequest);
	}
	
	/**
	 * 
	 * @param ofset �������� �� ������� �����
	 * @param count ���������� ����������� ������
	 * @param listener 
	 * ��������� ������ ���������� ������ � ���������� callback
	 */
	public void loadPost(final int offset, final int count, final IVkItemLoadListener listener){
		
		status = LOADED;
		
		RequestQueue queue = Volley.newRequestQueue(mContext);
		String url = "https://api.vk.com/method/wall.get?owner_id=" 
			+ Account.getInstance(mContext).getUserId() + "&offset=" + offset 
			+ "&count=" + count +
			"&v=5.2";
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

			  @Override
			  public void onResponse(JSONObject response) {
				  try {
					List<VkItem> listPost = JSONParser.parsePosts(response);
					listener.loadingSuccess(listPost);
					status = STOPPED;
				} catch (JSONException e) {
					listener.loadingError(e.getLocalizedMessage());
					status = STOPPED;
				}
					
			  }
			 }, new Response.ErrorListener() {

				 @Override
				 public void onErrorResponse(VolleyError error) {
					 listener.loadingError(error.getLocalizedMessage());
					 status = STOPPED;
				 }
			 });
		
		queue.add(jsObjRequest);
	}
	

}
