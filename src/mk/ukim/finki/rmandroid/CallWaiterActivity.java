package mk.ukim.finki.rmandroid;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.rmandroid.adapters.OrderItemAdapter;
import mk.ukim.finki.rmandroid.model.OrderItem;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CallWaiterActivity extends Activity {

	public static final String PREFS_NAME = "MyCart";

	private OrderItemAdapter adapter;
	private ListView lvOrder;
	private TextView tvTotal;
	private Button btnCallWaiter;
	private RadioGroup rgCallType;
	private RadioButton rCallType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_waiter);

		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		lvOrder = (ListView) findViewById(R.id.lvOrders);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		btnCallWaiter = (Button) findViewById(R.id.callWaiter);
		rgCallType = (RadioGroup) findViewById(R.id.rgCallType);

		SharedPreferences order = getSharedPreferences(PREFS_NAME, 0);
		int itemsCount = order.getInt("itemsCount", 0);
		float total = 0;
		List<OrderItem> lstOrderItem = new ArrayList<OrderItem>();
		for (int i = 1; i <= itemsCount; i++) {
			String title = order.getString("title_" + i, "");
			float price = order.getFloat("price_" + i, 0);
			int quantity = order.getInt("quantity_" + i, 0);
			float subTotal = price * quantity;
			total += subTotal;
			OrderItem oi = new OrderItem(quantity, price, title);
			lstOrderItem.add(oi);
		}
		tvTotal.setText(String.valueOf(total));
		adapter = new OrderItemAdapter(this,
				R.layout.listview_item_row_oreder_item, lstOrderItem);
		lvOrder.setAdapter(adapter);

		btnCallWaiter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rCallType = (RadioButton) findViewById(rgCallType
						.getCheckedRadioButtonId());

				if (rCallType.getText().equals("Pay")) {
					getSharedPreferences(PREFS_NAME, 0).edit().clear().commit();
				}

				Toast.makeText(CallWaiterActivity.this,
						R.string.waiterIsComing, Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}

}
