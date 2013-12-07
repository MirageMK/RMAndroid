package mk.ukim.finki.rmandroid.database;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.model.Item;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RMDao {
	// Database fields
	private SQLiteDatabase database;
	private RMDbOpenHelper dbHelper;
	private String[] allColumnsGroup = { RMDbOpenHelper.COLUMN_ID,
			RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE,
			RMDbOpenHelper.COLUMN_DESCRIPTION, RMDbOpenHelper.COLUMN_KEY,
			RMDbOpenHelper.COLUMN_SUBTITLE, RMDbOpenHelper.COLUMN_TITLE };

	private String[] allColumnsItem = { RMDbOpenHelper.COLUMN_ID,
			RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE,
			RMDbOpenHelper.COLUMN_CONTENT, RMDbOpenHelper.COLUMN_DESCRIPTION,
			RMDbOpenHelper.COLUMN_GROUPKEY, RMDbOpenHelper.COLUMN_SUBTITLE,
			RMDbOpenHelper.COLUMN_TITLE };

	public RMDao(Context context) {
		dbHelper = new RMDbOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
		dbHelper.close();
	}

	public boolean insertGroup(Group group) {
		long insertId = database.insert(RMDbOpenHelper.TABLE_GROUP, null,
				groupToContentValues(group));

		if (insertId > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteGroup() {
		database.delete(RMDbOpenHelper.TABLE_GROUP, null, null);
	}

	public List<Group> getAllGroups() {
		List<Group> groups = new ArrayList<Group>();

		Cursor cursor = database.query(RMDbOpenHelper.TABLE_GROUP,
				allColumnsGroup, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				groups.add(cursorToGroup(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return groups;
	}

	protected Group cursorToGroup(Cursor cursor) {
		Group group = new Group();
		group.setID(cursor.getInt(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_ID)));

		group.setBackgroundImage(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE)));

		group.setDescription(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_DESCRIPTION)));
		group.setKey(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_KEY)));
		group.setSubtitle(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_SUBTITLE)));
		group.setTitle(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_TITLE)));

		return group;
	}

	protected ContentValues groupToContentValues(Group group) {
		ContentValues values = new ContentValues();

		values.put(RMDbOpenHelper.COLUMN_ID, group.getID());
		values.put(RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE,
				group.getBackgroundImage());
		values.put(RMDbOpenHelper.COLUMN_DESCRIPTION, group.getDescription());
		values.put(RMDbOpenHelper.COLUMN_KEY, group.getKey());
		values.put(RMDbOpenHelper.COLUMN_SUBTITLE, group.getSubtitle());
		values.put(RMDbOpenHelper.COLUMN_TITLE, group.getTitle());
		return values;
	}

	public boolean insertItem(Item item) {
		long insertId = database.insert(RMDbOpenHelper.TABLE_ITEM, null,
				itemToContentValues(item));

		if (insertId > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteItem() {
		database.delete(RMDbOpenHelper.TABLE_ITEM, null, null);
	}

	public List<Item> getAllItems(String key) {
		List<Item> items = new ArrayList<Item>();

		Cursor cursor = database.query(RMDbOpenHelper.TABLE_ITEM,
				allColumnsItem, RMDbOpenHelper.COLUMN_GROUPKEY + "=?",
				new String[] { key }, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				items.add(cursorToItem(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return items;
	}

	protected Item cursorToItem(Cursor cursor) {
		Item item = new Item();
		item.setID(cursor.getInt(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_ID)));

		item.setBackgroundImage(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE)));

		item.setContent(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_CONTENT)));

		item.setDescription(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_DESCRIPTION)));
		item.setGroupKey(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_GROUPKEY)));
		item.setSubtitle(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_SUBTITLE)));
		item.setTitle(cursor.getString(cursor
				.getColumnIndex(RMDbOpenHelper.COLUMN_TITLE)));

		return item;
	}

	protected ContentValues itemToContentValues(Item item) {
		ContentValues values = new ContentValues();

		values.put(RMDbOpenHelper.COLUMN_ID, item.getID());
		values.put(RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE,
				item.getBackgroundImage());
		values.put(RMDbOpenHelper.COLUMN_CONTENT, item.getContent());
		values.put(RMDbOpenHelper.COLUMN_DESCRIPTION, item.getDescription());
		values.put(RMDbOpenHelper.COLUMN_GROUPKEY, item.getGroupKey());
		values.put(RMDbOpenHelper.COLUMN_SUBTITLE, item.getSubtitle());
		values.put(RMDbOpenHelper.COLUMN_TITLE, item.getTitle());
		return values;
	}
}
