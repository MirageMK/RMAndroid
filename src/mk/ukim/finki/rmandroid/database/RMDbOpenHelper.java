package mk.ukim.finki.rmandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RMDbOpenHelper extends SQLiteOpenHelper {

	// public static final String COLUMN_TABLE_ID = "_id";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_BACKGROUND_IMAGE = "backgroundImage";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_KEY = "key";
	public static final String COLUMN_GROUPKEY = "groupKey";
	public static final String COLUMN_SUBTITLE = "subtitle";
	public static final String COLUMN_TITLE = "title";
	public static final String TABLE_GROUP = "mGroup";
	public static final String TABLE_ITEM = "mItem";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME_EXPRESSION = "RMDatabase.db";

	private static final String TABLE_GROUP_CREATE = String.format(
			"create table %s (%s integer primary key, "
					+ "%s text, %s text, %s text, %s text, %s text);",
			TABLE_GROUP, COLUMN_ID, COLUMN_BACKGROUND_IMAGE,
			COLUMN_DESCRIPTION, COLUMN_KEY, COLUMN_SUBTITLE, COLUMN_TITLE);

	private static final String TABLE_ITEM_CREATE = String.format(
			"create table %s (%s integer primary key, "
					+ "%s text, %s text, %s text, %s text, %s text, %s text);",
			TABLE_ITEM, COLUMN_ID, COLUMN_BACKGROUND_IMAGE, COLUMN_CONTENT,
			COLUMN_DESCRIPTION, COLUMN_GROUPKEY, COLUMN_SUBTITLE, COLUMN_TITLE);

	public RMDbOpenHelper(Context context) {
		super(context, DATABASE_NAME_EXPRESSION, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_GROUP_CREATE);
		database.execSQL(TABLE_ITEM_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_GROUP));
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_ITEM));
		onCreate(db);
	}

}