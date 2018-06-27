package fhwedel.projektstudie;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    Note note;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private int mode;
    private EditText restaurant;
    private EditText menu;
    private EditText latitude;
    private EditText longitude;

    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        this.restaurant = (EditText)this.findViewById(R.id.text_restaurant);
        this.menu = (EditText)this.findViewById(R.id.text_menu);
        this.latitude = (EditText)this.findViewById(R.id.latitude);
        this.longitude = (EditText)this.findViewById(R.id.longitude);

        Intent intent = this.getIntent();
        this.note = (Note) intent.getSerializableExtra("note");
        if(note== null)  {
            this.mode = MODE_CREATE;
        } else  {
            this.mode = MODE_EDIT;
            this.restaurant.setText(note.getNoteRestaurant());
            this.menu.setText(note.getNoteMenu());
            String lat = Double.toString(note.getNoteLatitude());
            this.latitude.setText(lat);
            String lon = Double.toString(note.getNoteLongitude());
            this.longitude.setText(lon);
        }

    }



    // User Click on the Save button.
    public void buttonSaveClicked(View view)  {
        MyDatabaseHelper db = new MyDatabaseHelper(this);

        String restaurant = this.restaurant.getText().toString();
        String menu = this.menu.getText().toString();
        Double latitude = Double.parseDouble(this.latitude.getText().toString());
        Double longitude = Double.parseDouble(this.longitude.getText().toString());

        if(restaurant.equals("") || menu.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter restaurant & menu", Toast.LENGTH_LONG).show();
            return;
        }

        //TODO latitude und longitude abfragen (!= null / NaN() / ...)
        /*
        if(latitude.isNaN()){
            Toast.makeText(getApplicationContext(),
                    "Please enter a latitude", Toast.LENGTH_LONG).show();
            return;
        }
        if(longitude.isNaN()){
            Toast.makeText(getApplicationContext(),
                    "Please enter a longitude", Toast.LENGTH_LONG).show();
            return;
        }
        */

        if(mode==MODE_CREATE ) {
            this.note= new Note(restaurant,menu, latitude, longitude);
            db.addNote(note);
        } else  {
            this.note.setNoteRestaurant(restaurant);
            this.note.setNoteMenu(menu);
            this.note.setNoteLatitude(latitude);
            this.note.setNoteLongitude(longitude);
            db.updateNote(note);
        }

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }

    // User Click on the Cancel button.
    public void buttonCancelClicked(View view)  {
        // Do nothing, back MainActivity.
        this.onBackPressed();
    }

    // When completed this Activity,
    // Send feedback to the Activity called it.
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }

}
