package com.example.rahul.vaccination;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.rahul.vaccination.data.ChildContract;
import com.example.rahul.vaccination.data.VaccineHelper;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Period;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private  int count = 0;

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //button for going to child details edit page and add a new child or to edit details of the existing child
        Button button = (Button) findViewById(R.id.editor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorActivity.class);
                startActivity(intent);
            }
        });
        //end of the button functionality


        //for viewing the items of the database in the LogCat
        String[] projection = {
                ChildContract.ChildEntry.ID,
                ChildContract.ChildEntry.COLUMN_NAME,
                ChildContract.ChildEntry.COLUMN_FATHER,
                ChildContract.ChildEntry.COLUMN_MOTHER,
                ChildContract.ChildEntry.COLUMN_GENDER,
                ChildContract.ChildEntry.COLUMN_DOB
        };

         VaccineHelper vaccineHelper = new VaccineHelper(this);
         SQLiteDatabase db = vaccineHelper.getReadableDatabase();
        Cursor c = db.query(ChildContract.ChildEntry.TABLE_NAME,projection,null,null,null,null,null);

        while(c.moveToNext()){
            Log.d("row id",c.getString(c.getColumnIndex(ChildContract.ChildEntry.ID)) + c.getString(c.getColumnIndex(ChildContract.ChildEntry.COLUMN_NAME)) + c.getString(c.getColumnIndex(ChildContract.ChildEntry.COLUMN_FATHER)) + c.getString(c.getColumnIndex(ChildContract.ChildEntry.COLUMN_MOTHER)) + c.getString(c.getColumnIndex(ChildContract.ChildEntry.COLUMN_GENDER)) + c.getString(c.getColumnIndex(ChildContract.ChildEntry.COLUMN_DOB)));

        }
        //database items in the logcat END...




        final TextView age_display = (TextView)findViewById(R.id.age_detail);
        AndroidThreeTen.init(this);



        Thread t = new Thread(){
            @Override
            public void run() {
                while(!isInterrupted()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                            //getting current date and time instance
                            LocalDateTime today = LocalDateTime.now();
                            //getting date and time instance of birthday
                            LocalDateTime birthday = LocalDateTime.parse("21-06-1996 00:30",formatter);


                            //getting duration of current day from midnight till current instant
                            LocalDateTime YesterdayTillMidnight = today.toLocalDate().atStartOfDay();
                            Duration durationInCurrentDay = Duration.between(YesterdayTillMidnight,today);


//                            Duration durationInFirstDay = Duration.between(birthday,firstDayAfterBirth);

//                            Duration totalDuration = durationInFirstDay.plus(durationInCurrentDay);

                            Period p = Period.between(birthday.toLocalDate(),today.toLocalDate());
                            long hours = durationInCurrentDay.toHours();
                            long minutes = durationInCurrentDay.toMinutes()-hours*60;
                            long seconds = durationInCurrentDay.getSeconds()-(hours*3600)-(minutes*60);
 //                           long milliseconds = durationInCurrentDay.toMillis()-hours*3600000-minutes*60000-seconds*1000;


                            age_display.setText("Rahul Pandey is " +  p.getYears() + " years " +
                                    p.getMonths() + " months " + p.getDays() + " days " +
                                    hours + " hours " + minutes + " minutes " + seconds + " seconds old.");
                        }
                    });

                }
            }
        };
        t.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.child_list:
                loadChildList();
                break;
            default:
                  return super.onOptionsItemSelected(item);
    }
    return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
   }


   public void loadChildList() {

       AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
       //builderSingle.setIcon(R.drawable.ic_launcher);
       builderSingle.setTitle("Select Child");

       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_activated_1);

       String[] projection = {
                ChildContract.ChildEntry.COLUMN_NAME
        };


        //getting data from the database
        VaccineHelper vaccineHelper = new VaccineHelper(this);
        SQLiteDatabase db = vaccineHelper.getReadableDatabase();
        Cursor c = db.query(ChildContract.ChildEntry.TABLE_NAME,projection,null,null,null,null,null);
       while(c.moveToNext()){
           arrayAdapter.add(c.getString(c.getColumnIndex(ChildContract.ChildEntry.COLUMN_NAME)));
       }


       //dismissing the dialog on clicking on the cancel button
       builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });


      builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
//               String strName = arrayAdapter.getItem(which);
//               AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
//               builderInner.setMessage(strName);
//               builderInner.setTitle("Your Selected Item is");
//               builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog,int which) {
//                       dialog.dismiss();
//                   }
//               });
//               builderInner.show();
           }
       });
      builderSingle.show();
   }


}
