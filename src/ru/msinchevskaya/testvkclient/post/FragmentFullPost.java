package ru.msinchevskaya.testvkclient.post;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.utils.BitmapLruCache;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentFullPost extends Fragment {
	
	private Post mPost;
	private ImageLoader mImageLoader;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle bundle = getArguments();
	    mPost = bundle.getParcelable(PostActivity.INTENT_POST);
	    mImageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), new BitmapLruCache(
				BitmapLruCache.getDefaultLruCacheSize()));
	  }
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_full_post, null);
		((TextView)contentView.findViewById(R.id.tv_date)).setText(mPost.getDate());
		((TextView)contentView.findViewById(R.id.tv_shortText)).setText(mPost.getText());
		((TextView)contentView.findViewById(R.id.tv_likes_count)).setText(""+mPost.getLikes());
		((TextView)contentView.findViewById(R.id.tv_reposts_count)).setText(""+mPost.getReposts());
		((TextView)contentView.findViewById(R.id.tv_comments_count)).setText(""+mPost.getComments());
		ImageView ivUser = (ImageView) contentView.findViewById(R.id.iv_userImage);
		
		((TextView)contentView.findViewById(R.id.tv_userName)).setText(Account.getInstance(getActivity()).getUser().getFullName());
		mImageLoader.get(Account.getInstance(getActivity()).getUser().getPhotoUrl(), ImageLoader.getImageListener(ivUser,
					R.drawable.ic_launcher, R.drawable.ic_launcher));
		
		ImageView ivPost = (ImageView) contentView.findViewById(R.id.iv_postImage);
		if (!mPost.getListPhotos().isEmpty()){
			mImageLoader.get(mPost.getListPhotos().get(0), ImageLoader.getImageListener(ivPost,
					R.drawable.ic_launcher, R.drawable.ic_launcher));
		}
		
		return contentView;
	}

}
