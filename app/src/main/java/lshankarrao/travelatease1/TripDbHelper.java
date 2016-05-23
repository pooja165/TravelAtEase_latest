package lshankarrao.travelatease1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class TripDbHelper extends SQLiteOpenHelper {

    static private final int VERSION=3;
    static private final String DB_NAME="TripDatabase.db";

    static private final String SQL_CREATE_TRIP_TABLE =
            "CREATE TABLE tripInfo (" +
            "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  title TEXT," +
            "  city TEXT," +
            "  state TEXT," +
            "  country TEXT," +
            "  startDate TEXT," +
            "  endDate TEXT," +
            "  notes TEXT);";

    static private final String SQL_CREATE_EVENT_TABLE =
            "CREATE TABLE eventInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  startDate TEXT," +
                    "  endDate TEXT," +
                    "  address TEXT," +
                    "  city TEXT," +
                    "  state TEXT," +
                    "  country TEXT," +
                    "  tripId INTEGER);";

    static private final String SQL_CREATE_HOTEL_TABLE =
            "CREATE TABLE hotelInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  hotel TEXT," +
                    "  address TEXT," +
                    "  checkin TEXT," +
                    "  checkout TEXT," +
                    "  confirmationNo TEXT," +
                    "  notes TEXT," +
                    "  eventId INTEGER);";

    static private final String SQL_DROP_TABLE = "DROP TABLE tripInfo";

    Context context;

    public TripDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
        Toast.makeText(context, "TripDbHelper constructor!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context, "TripDbHelper onCreate!!!", Toast.LENGTH_SHORT).show();
        db.execSQL(SQL_CREATE_TRIP_TABLE);
        db.execSQL(SQL_CREATE_EVENT_TABLE);
        db.execSQL(SQL_CREATE_HOTEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TRIP_TABLE);

        Toast.makeText(context, "Upgrading DB and dropping data!!!", Toast.LENGTH_SHORT).show();
    }

    public int getMaxRecID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM contact;", null);

        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tripInfo;", null);
    }

    public long addTripInfo(TripInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("_id", ci.id);
        contentValues.put("title", ci.title);
        contentValues.put("city", ci.city);
        contentValues.put("state", ci.state);
        contentValues.put("country", ci.country);
        contentValues.put("startDate", ci.startDate);
        contentValues.put("endDate", ci.endDate);
        contentValues.put("notes", ci.notes);
        //contentValues.put("events", ci.events);
        return db.insert("tripInfo", null, contentValues);
    }

    public long addEventInfo(EventInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", ci.address);
        contentValues.put("city", ci.city);
        contentValues.put("state", ci.state);
        contentValues.put("country", ci.country);
        contentValues.put("startDate", ci.startDate);
        contentValues.put("endDate", ci.endDate);
        contentValues.put("tripId", ci.tripId);
        return db.insert("eventInfo", null, contentValues);
    }

    public void addHotelInfo(HotelInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("_id", ci.id);
        contentValues.put("hotel", ci.hotel);
        contentValues.put("address", ci.address);
        contentValues.put("checkin", ci.checkin);
        contentValues.put("checkout", ci.checkout);
        contentValues.put("confirmationNo", ci.confirmationNo);
        contentValues.put("notes", ci.notes);
        contentValues.put("eventId", ci.eventId);
        db.insert("hotelInfo", null, contentValues);
    }


    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("tripInfo", "_id=?", new String[]{String.valueOf(id)});

        /*
        String SQL_DELETE="DELETE FROM contact WHERE _id=" + id + ";";
        db.execSQL(SQL_DELETE);
         */
    }
}
