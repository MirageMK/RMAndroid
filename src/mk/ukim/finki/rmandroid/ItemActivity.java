package mk.ukim.finki.rmandroid;

import mk.ukim.finki.rmandroid.model.Item;
import mk.ukim.finki.rmandroid.utils.DrawableManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends Activity {

	public static final String PREFS_NAME = "MyCart";

	private Item item;
	private TextView tvSubtitle;
	private TextView tvDescription;
	private TextView tvContent;
	private ImageView imgItem;
	private Button btnAddToCart;
	private EditText etQuantity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		// Show the Up button in the action bar.
		item = (Item) getIntent().getSerializableExtra("Item");
		setTitle(item.getTitle());
		tvSubtitle = (TextView) findViewById(R.id.tvSubtitle);
		tvSubtitle.setText(item.getSubtitle());
		tvDescription = (TextView) findViewById(R.id.tvDescription);
		tvDescription.setText(item.getDescription());
		tvContent = (TextView) findViewById(R.id.tvContent);
		tvContent.setText(item.getContent());
		imgItem = (ImageView) findViewById(R.id.imgItem);
		DrawableManager dm = new DrawableManager();
		dm.fetchDrawableOnThread(item.getBackgroundImage(), imgItem);

		etQuantity = (EditText) findViewById(R.id.etQuantity);

		btnAddToCart = (Button) findViewById(R.id.addToCart);
		btnAddToCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// We need an Editor object to make preference changes.
				// All objects are from android.context.Context

				String title = item.getTitle();
				float price = Float.valueOf(item.getSubtitle().substring(1));
				;
				int quantity;
				try {
					quantity = Integer.valueOf(etQuantity.getText().toString());
				} catch (Exception e) {
					quantity = 0;
				}

				if (quantity > 0) {
					SharedPreferences order = getSharedPreferences(PREFS_NAME,
							0);
					int itemsCount = order.getInt("itemsCount", 0) + 1;
					SharedPreferences.Editor editor = order.edit();
					editor.putInt("itemsCount", itemsCount);
					editor.putString("title_" + itemsCount, title);
					editor.putFloat("price_" + itemsCount, price);
					editor.putInt("quantity_" + itemsCount, quantity);

					// Commit the edits!
					editor.commit();

					Toast.makeText(ItemActivity.this, R.string.addedToCart,
							Toast.LENGTH_SHORT).show();

					finish();
				} else {
					Toast.makeText(ItemActivity.this, R.string.invalidQuantity,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
