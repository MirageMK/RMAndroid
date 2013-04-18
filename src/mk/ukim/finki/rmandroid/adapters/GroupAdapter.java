package mk.ukim.finki.rmandroid.adapters;

import java.util.List;

import mk.ukim.finki.rmandroid.R;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.utils.DrawableManager;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapter extends ArrayAdapter<Group> {
	Context context;
	int layoutResourceId;
	List<Group> data = null;
	DrawableManager dm;

	public GroupAdapter(Context context, int layoutResourceId, List<Group> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.dm = new DrawableManager();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		GroupHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new GroupHolder();
			holder.imgIcon = (ImageView) row.findViewById(R.id.ivIcon);
			holder.txtTitle = (TextView) row.findViewById(R.id.tvTitle);

			row.setTag(holder);
		} else {
			holder = (GroupHolder) row.getTag();
		}

		Group group = data.get(position);
		holder.txtTitle.setText(group.getTitle());
		dm.fetchDrawableOnThread(group.getBackgroundImage(), holder.imgIcon);

		return row;
	}

	static class GroupHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}

	public void setData(List<Group> data) {
		this.data.addAll(data);
		notifyDataSetChanged();
	}
}
