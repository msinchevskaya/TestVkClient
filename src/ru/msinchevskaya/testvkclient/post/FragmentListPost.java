package ru.msinchevskaya.testvkclient.post;

import ru.msinchevskaya.testvkclient.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentListPost extends Fragment {
	
	private final String nums[] = {"1", "2", "3", "4", "5", "6"};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_list_post, null);	
		ListView lvPost = (ListView) contentView.findViewById(R.id.lvPost);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nums);
		lvPost.setAdapter(adapter);
		return contentView;
	}

}
