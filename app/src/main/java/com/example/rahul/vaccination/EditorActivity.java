package com.example.rahul.vaccination;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rahul.vaccination.data.ChildContract;
import com.example.rahul.vaccination.data.VaccineHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class EditorActivity extends AppCompatActivity {


    private VaccineHelper vaccineHelper;
    Calendar myCalendar = Calendar.getInstance();
    EditText date_picker;
    EditText time_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        date_picker = (EditText)findViewById(R.id.date_picker);
        time_picker = (EditText) findViewById(R.id.time_picker);
        vaccineHelper = new VaccineHelper(this);
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
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_picker.setText(sdf.format(myCalendar.getTime()));
    }


    public void save(String childName, String fatherName, String motherName, Spinner gender, int dob)
    {
        SQLiteDatabase db = vaccineHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ChildContract.ChildEntry.COLUMN_NAME,childName);
        values.put(ChildContract.ChildEntry.COLUMN_FATHER,fatherName);
        values.put(ChildContract.ChildEntry.COLUMN_MOTHER,motherName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }
}
