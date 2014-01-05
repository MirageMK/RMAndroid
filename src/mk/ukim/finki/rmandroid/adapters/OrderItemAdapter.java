package mk.ukim.finki.rmandroid.adapters;

import java.util.List;

import mk.ukim.finki.rmandroid.R;
import mk.ukim.finki.rmandroid.model.OrderItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> {
	Context context;
	int layoutResourceId;
	List<OrderItem> data = null;

	public OrderItemAdapter(Context context, int layoutResourceId,
			List<OrderItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		OrderItemHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new OrderItemHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.tvOTitle);
			holder.txtPrice = (TextView) row.findViewById(R.id.tvOPrice);
			holder.txtQuantity = (TextView) row.findViewById(R.id.tvOQuantity);
			holder.txtSubtotal = (TextView) row.findViewById(R.id.tvOSubtotal);

			row.setTag(holder);
		} else {
			holder = (OrderItemHolder) row.getTag();
		}

		OrderItem item = data.get(position);
		holder.txtTitle.setText(item.getTitle());
		holder.txtPrice.setText(String.valueOf(item.getPrice()));
		holder.txtQuantity.setText(String.valueOf(item.getQuantity()));
		holder.txtSubtotal.setText(String.valueOf(item.getQuantity()
				* item.getPrice()));

		return row;
	}

	static class OrderItemHolder {
		TextView txtTitle;
		TextView txtQuantity;
		TextView txtPrice;
		TextView txtSubtotal;
	}

	public void setData(List<OrderItem> data) {
		this.data.addAll(data);
		notifyDataSetChanged();
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
