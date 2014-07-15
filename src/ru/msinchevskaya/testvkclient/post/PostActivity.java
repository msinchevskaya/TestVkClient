package ru.msinchevskaya.testvkclient.post;

import java.util.ArrayList;
import java.util.List;

import ru.msinchevskaya.testvkclient.EnterActivity;
import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.post.FragmentListPost.IListPostListener;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader.IVkItemLoadListener;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PostActivity extends ActionBarActivity implements IVkItemLoadListener, IListPostListener{
	
	private Account mAccount;
	private ArrayList<Post> listPost = new ArrayList<Post>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		mAccount = Account.getInstance(this);
		setTitle(Account.getInstance(this).getUser().getFullName());	
		Log.d(getString(R.string.app_tag), getString(R.string.screen_type));
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		listPost = savedInstanceState.getParcelableArrayList("ListPost");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (listPost.isEmpty()){
			VkItemLoader itemLoader = VkItemLoader.getInstance(this);
			itemLoader.loadPost(0, 20, this);
		}
		else {		
			FragmentListPost listPostFragment = (FragmentListPost) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
			listPostFragment.update(listPost);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("ListPost",listPost);
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
			listPost.add(post);
		}
		FragmentListPost listPostFragment = (FragmentListPost) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
		listPostFragment.update(listPost);
	}

	@Override
	public void loadingError(String message) {
		Log.d(getString(R.string.app_tag), message);
	}

	@Override
	public void loadNext() {
		if (VkItemLoader.getInstance(this).getStatus() == VkItemLoader.STOPPED){
			VkItemLoader itemLoader = VkItemLoader.getInstance(this);
			itemLoader.loadPost(20, 40, this);
		}
	}

	@Override
	public void loadUpdates() {
	}

	@Override
	public void onPostClick(int position) {
		Log.d(getString(R.string.app_tag), listPost.get(position).getText());
	}
	
	private void startFullPostActivity(Post post){
		Intent intent = new Intent();
	}
	
	private void addFullPostFragment(Post post){
		
	}

}
