package com.example.rahul.vaccination.data;

import android.provider.BaseColumns;

/**
 * Created by rahul on 26/3/17.
 */

public final class ChildContract {
    private ChildContract(){}

    public static final class ChildEntry implements BaseColumns{
        public final static String ID = BaseColumns._ID;
        public final static String TABLE_NAME = "child" ;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_FATHER = "father";
        public final static String COLUMN_MOTHER = "mother";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_DOB = "dob";


        public final static int GENDER_MALE = 0;
        public final static int GENDER_FEMALE = 1;
    }
}
