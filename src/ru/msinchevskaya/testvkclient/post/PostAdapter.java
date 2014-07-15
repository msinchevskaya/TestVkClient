package ru.msinchevskaya.testvkclient.post;

import java.util.List;

import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PostAdapter extends ArrayAdapter<Post>{
	
	private final Context mContext;
	private final LayoutInflater mInflater;

	public PostAdapter(Context context, int resource, List<Post> objects) {
		super(context, resource, objects);
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.adapter_post, null);
		final Post post = getItem(position);
		((TextView)convertView.findViewById(R.id.tv_date)).setText(post.getDate());
		((TextView)convertView.findViewById(R.id.tv_shortText)).setText(post.getText());
		((TextView)convertView.findViewById(R.id.tv_likes_count)).setText(""+post.getLikes());
		((TextView)convertView.findViewById(R.id.tv_reposts_count)).setText(""+post.getReposts());
		((TextView)convertView.findViewById(R.id.tv_comments_count)).setText(""+post.getComments());
		
		if (post.getFromId().equals(Account.getInstance(mContext).getUserId())){
			((TextView)convertView.findViewById(R.id.tv_userName)).setText(Account.getInstance(mContext).getUser().getFullName());
		}
		return convertView;
	}
}
