package com.example.rahul.vaccination;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private  String[] child_list;
    private int entries=0;

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




        String ageDetail = new String();
        ageDetail = "Rahul is 2 years 11 months 24 days 7 hours 3 minutes 45 seconds old.";
        TextView ageDetailTextView = (TextView)findViewById(R.id.age_detail);
        ageDetailTextView.setText(ageDetail);


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

       // menu.clear();
//        String[] projection = {
//                ChildContract.ChildEntry.COLUMN_NAME
//        };
//
//        VaccineHelper vaccineHelper = new VaccineHelper(this);
//        SQLiteDatabase db = vaccineHelper.getReadableDatabase();
//        Cursor c = db.query(ChildContract.ChildEntry.TABLE_NAME,projection,null,null,null,null,null);
//
//        int i=0;
//        showAllChild();
//        while(i<entries) {
//            menu.add(0, Menu.FIRST + i, Menu.NONE,child_list[i]);
//            i++;
//        }

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
