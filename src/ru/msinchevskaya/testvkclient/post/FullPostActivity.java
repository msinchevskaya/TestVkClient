package ru.msinchevskaya.testvkclient.post;

import java.util.List;

import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader;
import ru.msinchevskaya.testvkclient.utils.VkItemLoader.IVkItemLoadListener;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class FullPostActivity extends ActionBarActivity implements IVkItemLoadListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_post);
		TextView tv = (TextView) findViewById(R.id.tv_post_text);
		Post post = getIntent().getExtras().getParcelable(PostActivity.INTENT_POST);
		tv.setText(post.getListPhotos().get(0));
		
		VkItemLoader itemLoader = VkItemLoader.getInstance(this);
		itemLoader.loadComments(post.getId(), post.getComments(), this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void loadingSuccess(List<VkItem> listItem) {
		
	}

	@Override
	public void loadingError(String message) {
		
	}
}
