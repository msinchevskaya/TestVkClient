package ru.msinchevskaya.testvkclient.post;


import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class FullPostActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_post);
		
		Post post = getIntent().getParcelableExtra(PostActivity.INTENT_POST);
        FragmentFullPost fragmentFull = new FragmentFullPost();
		Bundle bundle = new Bundle();
		bundle.putParcelable(PostActivity.INTENT_POST, post);
		fragmentFull.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentFull, fragmentFull).commit();
	}
}
