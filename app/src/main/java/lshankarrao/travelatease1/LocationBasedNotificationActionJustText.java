package lshankarrao.travelatease1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by vijay on 6/3/2016.
 */
public class LocationBasedNotificationActionJustText extends ActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_location_notification_action);
        Intent temp = getIntent();
        String s = temp.getStringExtra("tripPlace");
        Log.i("nodu ", s);
        maybeSendEmail(getIntent().getStringExtra("tripPlace"));

    }

    private void maybeSendEmail(final String place) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your friends email ids below(comma separated)" + "\nYour Location updates will be sent to them.");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailAddrs = input.getText().toString();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, emailAddrs.split(","));
                i.putExtra(Intent.EXTRA_SUBJECT, "Location Update");
                i.putExtra(Intent.EXTRA_TEXT, "Reached Safely. Saying hello from " + place);
                //i.putExtra(Intent.EXTRA_TEXT   , Html.fromHtml(mfullTripDetails + mEventDetails));
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menu != null) {

            menu.findItem(R.id.action_addtrip).setVisible(false);
        }
        return true;
    }
}
