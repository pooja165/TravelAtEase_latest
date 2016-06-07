package lshankarrao.travelatease1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by lakshmi on 5/24/2016.
 */
public class EventListAdapter extends CursorAdapter {
    TripDbHelper tripDb;
    public EventListAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.event_list_custom_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        tripDb = new TripDbHelper(context);
        //get title
        Log.i("bV ", cursor.getCount() + "");
        Log.i("BV event id ", cursor.getInt(cursor.getColumnIndex("_id"))+"");

        Calendar current = Calendar.getInstance();
//        if(cursor.getCount()==2) {
//            Log.i("BV event id ", cursor.getInt(cursor.getColumnIndex("_id"))+"");
//            //((RelativeLayout) view.findViewById(R.id.relativeLayoutVTA)).setBackgroundColor(0xFFFFFF);
//            ((ListView) view.findViewById(R.id.listViewTLAEventList)).setBackgroundColor(0xFFFFFF);
//        }

        String eventTitle = cursor.getString(cursor.getColumnIndex("title"));
        ((TextView)view.findViewById(R.id.textViewVTAEventListTitle)).setText(eventTitle);
        Log.i("end time + curr time= ",cursor.getLong(cursor.getColumnIndex("endTimeMillis"))+" "+current.getTimeInMillis()+"");
        if(cursor.getLong(cursor.getColumnIndex("endTimeMillis"))< current.getTimeInMillis() ){
            ((RelativeLayout)view.findViewById(R.id.relativeLayoutEventListCustomRow)).setBackgroundColor(0xFFA3A3A3);//fff1f1f1);
            ((TextView)view.findViewById(R.id.textViewVTAEventListTitle)).setBackgroundColor(0xFFADA8A8);
        }

        int tripID = cursor.getInt(cursor.getColumnIndex("tripId"));
        Log.i("bla bla id ", tripID + "");

        String eventPlace =
                cursor.getString(cursor.getColumnIndex("address"))+", "+
                cursor.getString(cursor.getColumnIndex("city"))+", "+
                cursor.getString(cursor.getColumnIndex("state"))+", "+
                cursor.getString(cursor.getColumnIndex("country"));

        Log.i("new trip name displayed", eventPlace);
        ((TextView)view.findViewById(R.id.textViewVTAEventListPlace)).setText(eventPlace);

        long stTimeMilli = cursor.getLong(cursor.getColumnIndex("stTimeMillis"));
        long endTimeMilli = cursor.getLong(cursor.getColumnIndex("endTimeMillis"));
        int eventId = (int)cursor.getInt(cursor.getColumnIndex("_id"));
        Log.i("event id ", eventId+"");

        Log.i("end time ms ",endTimeMilli+"");
        Log.i("end time ms ",stTimeMilli+"");

        String[] ev_st_mon_day_year = tripDb.getDateFromMilli(stTimeMilli, "MMM/dd/yyyy");
        String[] ev_end_mon_day_year = tripDb.getDateFromMilli(endTimeMilli, "MMM/dd/yyyy");

        String eventFrom = ev_st_mon_day_year[0] + " " + ev_st_mon_day_year[1] + ", " + ev_st_mon_day_year[2] +
                " to " + ev_end_mon_day_year[0] + " " + ev_end_mon_day_year[1] + ", " + ev_end_mon_day_year[2];
                /*cursor.getString(cursor.getColumnIndex("startDate")) + "    "+
                cursor.getString(cursor.getColumnIndex("startTime"));*/
        Log.i("event duration ", eventFrom);
        ((TextView)view.findViewById(R.id.textViewVTAEventListDuration)).setText(eventFrom);

        ((ImageView)view.findViewById(R.id.imageViewHotel)).setVisibility(View.INVISIBLE);
        Cursor hotelCursor = tripDb.fetchAllHotelsForEvent(eventId);
        int hotelCount = hotelCursor.getCount();
        String reservations="";
        String hotelDetails=null;
        if(hotelCount >0){
            reservations = hotelCount + " hotel/s";
            hotelCursor.moveToFirst();
            hotelDetails = "Hotel: "+hotelCursor.getString(hotelCursor.getColumnIndex("hotel"))+" \nCheck-in: "+
                    hotelCursor.getString(hotelCursor.getColumnIndex("checkin_date"))+" \nConf No: "+
                    hotelCursor.getString(hotelCursor.getColumnIndex("confirmationNo"));
            ((ImageView)view.findViewById(R.id.imageViewHotel)).setVisibility(View.VISIBLE);
            //((ImageView)view.findViewById(R.id.imageViewHotel)).setImageSrc(R.)
        }
        else {
            reservations = "None";
            hotelDetails = "No hotel reservations to show";
        }


        Log.i("new trip name displayed", reservations);
        ((TextView)view.findViewById(R.id.textViewEventListHotelResDetails)).setText(hotelDetails);


        ((ImageView)view.findViewById(R.id.imageViewTransport)).setVisibility(View.INVISIBLE);
        Cursor transportCursor = tripDb.fetchAllTransportForEvent(eventId);
        int transportCount = transportCursor.getCount();
        String transportDetails=null;
        if(transportCount >0){
            reservations = reservations+", "+transportCount + " transport/s";
            transportCursor.moveToFirst();
            transportDetails = transportCursor.getString(transportCursor.getColumnIndex("fromPlace"))
                    +" \nTimings: "+
                    transportCursor.getString(transportCursor.getColumnIndex("fromDate"))+ " at "+
                    transportCursor.getString(transportCursor.getColumnIndex("fromTime"))+" \nConf No: "+
                    transportCursor.getString(transportCursor.getColumnIndex("confNo"));
            if(transportCursor.getString(transportCursor.getColumnIndex("typeofTransport")).equals("Car")) {
                ((ImageView) view.findViewById(R.id.imageViewTransport)).setImageResource(R.drawable.car_icon);
            }
            else if(transportCursor.getString(transportCursor.getColumnIndex("typeofTransport")).equals("Flight")){
                ((ImageView) view.findViewById(R.id.imageViewTransport)).setImageResource(R.drawable.flight_icon);
            }
            else if(transportCursor.getString(transportCursor.getColumnIndex("typeofTransport")).equals("Bus")){
                ((ImageView) view.findViewById(R.id.imageViewTransport)).setImageResource(R.drawable.bus_icon);
            }
            else if(transportCursor.getString(transportCursor.getColumnIndex("typeofTransport")).equals("Train")){
                ((ImageView) view.findViewById(R.id.imageViewTransport)).setImageResource(R.drawable.train_icon);
            }
            ((ImageView)view.findViewById(R.id.imageViewTransport)).setVisibility(View.VISIBLE);
        }
        else {
            reservations = "None";
            transportDetails = "No transport reservations to show";
        }


        Log.i("new trip name displayed", reservations);
        ((TextView)view.findViewById(R.id.textViewVTAEventListReservations)).setText(reservations);
        ((TextView)view.findViewById(R.id.textViewEventListTransportResDetails)).setText(transportDetails);


    }
}
