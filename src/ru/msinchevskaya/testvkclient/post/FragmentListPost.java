package ru.msinchevskaya.testvkclient.post;

import java.util.List;

import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentListPost extends Fragment{
	private PostAdapter mAdapter;
	private ListView lvPost;
	private IListPostListener postListener;
	
	public interface IListPostListener{
		public void loadNext();
		public void loadUpdates();
		public void onPostClick(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_list_post, null);	
		lvPost = (ListView) contentView.findViewById(R.id.lvPost);
		lvPost.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount){
					postListener.loadNext();
				}
			}
		});
		
		lvPost.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position	,
					long id) {
				postListener.onPostClick(position);
			}
		});
		return contentView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			postListener = (IListPostListener) activity;
		}
		catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IListPostListener");
        }
	}
	
	public void update(List<Post> list){
		if (mAdapter == null){
			mAdapter = new PostAdapter(getActivity(), 0, list);
			lvPost.setAdapter(mAdapter);
			Log.d(getActivity().getString(R.string.app_tag), "new");
		}
		else {
			mAdapter.notifyDataSetChanged();
			Log.d(getActivity().getString(R.string.app_tag), "update");
		}
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mAdapter = null;
		lvPost = null;
	}


}
