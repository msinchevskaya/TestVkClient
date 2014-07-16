package ru.msinchevskaya.testvkclient.post;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.utils.BitmapLruCache;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends ArrayAdapter<Post>{
	
	private final Context mContext;
	private final LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	
    //Шаблон ViewHolder
    static class ViewHolder {
        public TextView tvDate;
        public TextView tvShortText;
        public ImageView userImage;
        public TextView tvUserName;
    }

	public PostAdapter(Context context, int resource, List<Post> objects) {
		super(context, resource, objects);
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	    mImageLoader = new ImageLoader(Volley.newRequestQueue(mContext), new BitmapLruCache(
				BitmapLruCache.getDefaultLruCacheSize()));
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
        View rowView = convertView;
        final Post post = getItem(position);
        
        if (rowView == null) {
            rowView = mInflater.inflate(R.layout.adapter_post, null, true);
            holder = new ViewHolder();
    		holder.tvDate = ((TextView)rowView.findViewById(R.id.tv_date));
    		holder.tvShortText = (TextView)rowView.findViewById(R.id.tv_shortText);
    		holder.userImage = (ImageView) rowView.findViewById(R.id.iv_userImage);    		
    		holder.tvUserName = (TextView) rowView.findViewById(R.id.tv_userName);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        
        holder.tvDate.setText(post.getDate());
        holder.tvShortText.setText(post.getShortText());
        holder.tvUserName.setText(Account.getInstance(mContext).getUser().getFullName());
		mImageLoader.get(Account.getInstance(mContext).getUser().getPhotoUrl(), ImageLoader.getImageListener(holder.userImage,
						R.drawable.ic_launcher, R.drawable.ic_launcher));
		return rowView;
	}
}
