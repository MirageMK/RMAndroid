package mk.ukim.finki.rmandroid.database;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.rmandroid.model.Group;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RMDao {
	// Database fields
	private SQLiteDatabase database;
	private RMDbOpenHelper dbHelper;
	private String[] allColumns = { RMDbOpenHelper.COLUMN_ID,
			RMDbOpenHelper.COLUMN_BACKGROUND_IMAGE,
			RMDbOpenHelper.COLUMN_DESCRIPTION, RMDbOpenHelper.COLUMN_KEY,
			RMDbOpenHelper.COLUMN_SUBTITLE, RMDbOpenHelper.COLUMN_TITLE };

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

	public boolean insert(Group group) {
		long insertId = database.insert(RMDbOpenHelper.TABLE_GROUP, null,
				itemToContentValues(group));

		if (insertId > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void delete() {
		database.delete(RMDbOpenHelper.TABLE_GROUP, null, null);
	}

	public List<Group> getAllItems() {
		List<Group> groups = new ArrayList<Group>();

		Cursor cursor = database.query(RMDbOpenHelper.TABLE_GROUP, allColumns,
				null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				groups.add(cursorToItem(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return groups;
	}

	protected Group cursorToItem(Cursor cursor) {
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

	protected ContentValues itemToContentValues(Group group) {
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
}
