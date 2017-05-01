package com.example.rahul.vaccination;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.rahul.vaccination.data.ChildContract;
import com.example.rahul.vaccination.data.VaccineHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class EditorActivity extends AppCompatActivity {


    Context context = this;
    private VaccineHelper vaccineHelper;

    private EditText childName ;
    private EditText fatherName;
    private EditText motherName;
    private Calendar myCalendar = Calendar.getInstance();
    private EditText date_picker;
    private EditText time_picker;
    private Spinner genderSpinner;
    private Button done_button;
    private int mGender = ChildContract.ChildEntry.GENDER_UNKNOWN;
    private long oldMillis;
    //private EditText show_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        done_button = (Button)findViewById(R.id.done_editing);
        childName = (EditText)findViewById(R.id.child_name);
        fatherName = (EditText)findViewById(R.id.father_name);
        motherName = (EditText)findViewById(R.id.mother_name);
        date_picker = (EditText)findViewById(R.id.date_picker);
        time_picker = (EditText) findViewById(R.id.time_picker);
        genderSpinner = (Spinner)findViewById(R.id.spinner_gender);
        vaccineHelper = new VaccineHelper(this);
        //show_time = (EditText)findViewById(R.id.showTime);

        setupSpinner();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        date_picker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditorActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current time
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time_picker.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });






        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        genderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = ChildContract.ChildEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = ChildContract.ChildEntry.GENDER_FEMALE;
                    } else {
                        mGender = ChildContract.ChildEntry.GENDER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = ChildContract.ChildEntry.GENDER_UNKNOWN;
            }
        });
    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_picker.setText(sdf.format(myCalendar.getTime()));
    }



    public void save()
    {
        String childNameString = childName.getText().toString().trim();
        String fatherNameString = fatherName.getText().toString().trim();
        String motherNameString = motherName.getText().toString().trim();


        SQLiteDatabase db = vaccineHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //getting the date of birth in milliseconds and then storing it to the database
        String givenDateString = date_picker.getText().toString().trim() + " " + time_picker.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm");
        try {
            Date mDate = sdf.parse(givenDateString);
            oldMillis = mDate.getTime();
            //System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("givenDateString",givenDateString);
        Log.d("value of oldmills",String.valueOf(oldMillis));

        values.put(ChildContract.ChildEntry.COLUMN_NAME,childNameString);
        values.put(ChildContract.ChildEntry.COLUMN_FATHER,fatherNameString);
        values.put(ChildContract.ChildEntry.COLUMN_MOTHER,motherNameString);
        values.put(ChildContract.ChildEntry.COLUMN_GENDER,mGender);
        values.put(ChildContract.ChildEntry.COLUMN_DOB,oldMillis);


        long newRowId = db.insert(ChildContract.ChildEntry.TABLE_NAME, null, values);


        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case (R.id.clear_all):
                clearAllEntries();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearAllEntries(){

    }
}
