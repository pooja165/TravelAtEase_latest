package lshankarrao.travelatease1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TripDbHelper extends SQLiteOpenHelper {

    static private final int VERSION = 3;
    static private final String DB_NAME = "TripDatabase.db";

    static private final String SQL_CREATE_TRIP_TABLE =
            "CREATE TABLE tripInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  title TEXT," +
                    "  city TEXT," +
                    "  state TEXT," +
                    "  country TEXT," +
                    "  startDate TEXT," +
                    "  endDate TEXT," +
                    "  startTime TEXT," +
                    "  endTime TEXT," +
                    "  notes TEXT," +
                    "stTimeMillis INTEGER, " +
                    "endTimeMillis INTEGER);";

    static private final String SQL_CREATE_EVENT_TABLE =
            "CREATE TABLE eventInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  title TEXT," +
                    "  startDate TEXT," +
                    "  endDate TEXT," +
                    "  startTime TEXT," +
                    "  endTime TEXT," +
                    "  address TEXT," +
                    "  city TEXT," +
                    "  state TEXT," +
                    "  country TEXT," +
                    "  tripId INTEGER," +
                    "  stTimeMillis INTEGER," +
                    "  endTimeMillis INTEGER);";

    static private final String SQL_CREATE_HOTEL_TABLE =
            "CREATE TABLE hotelInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  hotel TEXT," +
                    "  address TEXT," +
                    "  checkin_date TEXT," +
                    "  checkout_date TEXT," +
                    "  checkin_time TEXT," +
                    "  checkout_time TEXT," +
                    "  confirmationNo TEXT," +
                    "  notes TEXT," +
                    "  eventId INTEGER);";

    static private final String SQL_CREATE_TRANSPORT_TABLE =
            "CREATE TABLE transportInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  fromPlace TEXT," +
                    "  fromDate TEXT," +
                    "  fromTime TEXT," +
                    "  toPlace TEXT," +
                    "  toDate TEXT," +
                    "  toTime TEXT," +
                    "  typeofTransport TEXT," +
                    "  confNo TEXT," +
                    "  flightNo TEXT," +
                    "  notes TEXT," +
                    "  eventId INTEGER);";

    static private final String SQL_CREATE_OTHER_RES_TABLE =
            "CREATE TABLE otherResInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  startDate TEXT," +
                    "  startTime TEXT," +
                    "  endDate TEXT," +
                    "  endTime TEXT," +
                    "  typeOfEvent TEXT," +
                    "  address TEXT," +
                    "  city TEXT," +
                    "  state TEXT," +
                    "  country TEXT," +
                    "  notes TEXT," +
                    "  eventId INTEGER);";

    static private final String SQL_DROP_TABLE = "DROP TABLE tripInfo";

    Context context;

    public TripDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
        //Toast.makeText(context, "TripDbHelper constructor!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Toast.makeText(context, "TripDbHelper onCreate!!!", Toast.LENGTH_SHORT).show();
        db.execSQL(SQL_CREATE_TRIP_TABLE);
        db.execSQL(SQL_CREATE_EVENT_TABLE);
        db.execSQL(SQL_CREATE_HOTEL_TABLE);
        db.execSQL(SQL_CREATE_TRANSPORT_TABLE);
        db.execSQL(SQL_CREATE_OTHER_RES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TRIP_TABLE);
        db.execSQL(SQL_CREATE_EVENT_TABLE);
        db.execSQL(SQL_CREATE_HOTEL_TABLE);
        db.execSQL(SQL_CREATE_TRANSPORT_TABLE);
        db.execSQL(SQL_CREATE_OTHER_RES_TABLE);

        //Toast.makeText(context, "Upgrading DB and dropping data!!!", Toast.LENGTH_SHORT).show();
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

    public Cursor fetchAllTrips() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tripInfo;", null);
    }

    public Cursor fetchAllEventsForTrip(int tripId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //return db.rawQuery("SELECT * FROM eventInfo;", null);
        return db.rawQuery("SELECT * FROM eventInfo WHERE tripId=" + tripId + " ORDER BY stTimeMillis;", null);
    }

    public Cursor fetchUpcomingTrips() {
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();

        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM tripInfo WHERE stTimeMillis>=" + currentTime + " ORDER BY stTimeMillis;";
        return db.rawQuery(q, null);
    }

    public Cursor fetchPastTrips() {
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();

        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM tripInfo WHERE endTimeMillis<" + currentTime + " ORDER BY stTimeMillis;";
        return db.rawQuery(q, null);
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
        contentValues.put("startTime", ci.startTime);
        contentValues.put("endTime", ci.endTime);
        contentValues.put("notes", ci.notes);
        contentValues.put("stTimeMillis", ci.stTimeMillis);
        contentValues.put("endTimeMillis", ci.endTimeMillis);
        //contentValues.put("events", ci.events);
        return db.insert("tripInfo", null, contentValues);
    }

    public long addEventInfo(EventInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", ci.title);
        contentValues.put("address", ci.address);
        contentValues.put("city", ci.city);
        contentValues.put("state", ci.state);
        contentValues.put("country", ci.country);
        contentValues.put("startDate", ci.startDate);
        contentValues.put("endDate", ci.endDate);
        contentValues.put("startTime", ci.startTime);
        contentValues.put("endTime", ci.endTime);
        contentValues.put("tripId", ci.tripId);
        contentValues.put("stTimeMillis", ci.stTimeMillis);
        contentValues.put("endTimeMillis", ci.endTimeMillis);
        return db.insert("eventInfo", null, contentValues);
    }

    public void addHotelInfo(HotelInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("_id", ci.id);
        contentValues.put("hotel", ci.hotel);
        contentValues.put("address", ci.address);
        contentValues.put("checkin_date", ci.checkin_date);
        contentValues.put("checkout_date", ci.checkout_date);
        contentValues.put("checkin_time", ci.checkin_time);
        contentValues.put("checkout_time", ci.checkout_time);
        contentValues.put("confirmationNo", ci.confirmationNo);
        contentValues.put("notes", ci.notes);
        contentValues.put("eventId", ci.eventId);
        db.insert("hotelInfo", null, contentValues);
    }

    public void addTransportInfo(TransportInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("_id", ci.id);
        contentValues.put("fromPlace", ci.fromPlace);
        contentValues.put("fromDate", ci.fromDate);
        contentValues.put("fromTime", ci.fromTime);
        contentValues.put("toPlace", ci.toPlace);
        contentValues.put("toDate", ci.toDate);
        contentValues.put("toTime", ci.toTime);
        contentValues.put("notes", ci.notes);
        contentValues.put("typeofTransport", ci.typeofTransport);
        contentValues.put("confNo", ci.confNo);
        contentValues.put("flightNo", ci.flightNo);
        contentValues.put("eventId", ci.eventId);
        db.insert("transportInfo", null, contentValues);
    }

    public void addOtherResInfo(OtherResInfo ci) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("_id", ci.id);
        contentValues.put("startDate", ci.startDate);
        contentValues.put("startTime", ci.startTime);
        contentValues.put("endDate", ci.endDate);
        contentValues.put("endTime", ci.endTime);
        contentValues.put("typeOfEvent", ci.typeofEvent);
        contentValues.put("address", ci.address);
        contentValues.put("city", ci.city);
        contentValues.put("state", ci.state);
        contentValues.put("country", ci.country);
        contentValues.put("notes", ci.notes);
        contentValues.put("eventId", ci.eventId);
        db.insert("otherResInfo", null, contentValues);
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("tripInfo", "_id=?", new String[]{String.valueOf(id)});

        /*
        String SQL_DELETE="DELETE FROM contact WHERE _id=" + id + ";";
        db.execSQL(SQL_DELETE);
         */
    }

    public void logAllTrips() {
        Cursor c = fetchAllTrips();
        Log.i("no. of trips= ", c.getCount()+"");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.i("", c.getString(c.getColumnIndex("title")));
            Log.i("", c.getString(c.getColumnIndex("city")));
            Log.i("", c.getString(c.getColumnIndex("state")));
            Log.i("", c.getString(c.getColumnIndex("country")));
            Log.i("", c.getString(c.getColumnIndex("startDate")));
            Log.i("", c.getString(c.getColumnIndex("endDate")));
            Log.i("", c.getString(c.getColumnIndex("startTime")));
            Log.i("", c.getString(c.getColumnIndex("endTime")));
            Log.i("", c.getString(c.getColumnIndex("notes")));
            c.moveToNext();
        }
    }


    public TripInfo getTripInfo(int id) {
        Log.i("id", id + "");

        SQLiteDatabase db = this.getReadableDatabase();
        //query for basic trip details
        Cursor c = db.rawQuery("SELECT * FROM tripInfo WHERE _id=" + id + ";", null);
        if (c == null || c.getCount() < 1) {
            Log.i("kirik", "agide");
            return new TripInfo();
        }
        c.moveToFirst();
        Log.i("count = ", c.getCount() + "");
        Log.i("index = ", c.getColumnIndex("title") + "");


        Log.i("title TripDBhelper", c.getInt(c.getColumnIndex("_id")) + "");
        //need to query for events with this trip id stored.
        //get the event ids for all the trips which have the current "id" stored in the trip id field.
        //Cursor d = db.rawQuery("SELECT * FROM eventInfo WHERE tripId=" +id+"", null);
        TripInfo tripInfo = new TripInfo(
                c.getString(c.getColumnIndex("title")),
                c.getString(c.getColumnIndex("city")),
                c.getString(c.getColumnIndex("state")),
                c.getString(c.getColumnIndex("country")),
                c.getString(c.getColumnIndex("startDate")),
                c.getString(c.getColumnIndex("endDate")),
                c.getString(c.getColumnIndex("startTime")),
                c.getString(c.getColumnIndex("endTime")),
                c.getString(c.getColumnIndex("notes")),
                c.getLong(c.getColumnIndex("stTimeMillis")),
                c.getLong(c.getColumnIndex("endTimeMillis"))
        );


        return tripInfo;
    }

    public List<EventInfo> getEventInfo(int id) {

        Log.i("id", id + "");

        SQLiteDatabase db = this.getReadableDatabase();
        //query for basic trip details
        Cursor c = db.rawQuery("SELECT * FROM eventInfo WHERE tripId=" + id + ";", null);
        if (c == null || c.getCount() < 1) {
            Log.i("kirik", "aagide");
            return null;
        }
        c.moveToFirst();
        Log.i("count = ", c.getCount() + "");
        List<EventInfo> infos = new ArrayList<EventInfo>();
        while (!c.isAfterLast()) {
            EventInfo eventInfo = new EventInfo(c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("title")),
                    c.getString(c.getColumnIndex("startDate")),
                    c.getString(c.getColumnIndex("endDate")),
                    c.getString(c.getColumnIndex("startTime")),
                    c.getString(c.getColumnIndex("endTime")),
                    c.getString(c.getColumnIndex("address")),
                    c.getString(c.getColumnIndex("city")),
                    c.getString(c.getColumnIndex("state")),
                    c.getString(c.getColumnIndex("country")),
                    c.getInt(c.getColumnIndex("tripId")),
                    c.getLong(c.getColumnIndex("stTimeMillis")),
                    c.getLong(c.getColumnIndex("endTimeMillis")));
            eventInfo.id = c.getInt(c.getColumnIndex("_id"));
            c.moveToNext();
            infos.add(eventInfo);
        }

        return infos;
    }
}
