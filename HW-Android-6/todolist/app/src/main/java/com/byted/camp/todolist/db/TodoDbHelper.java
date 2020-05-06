package com.byted.camp.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */

public class TodoDbHelper extends SQLiteOpenHelper {

    public static String TAG="BDC_DB";
    // TODO 定义数据库名、版本；创建数据库
    public static final int DATEBASE_VERISON=1;
    public static final String DATEBASE_NAME= TodoContract.TodoEntry.TABLE_NAME;


    public TodoDbHelper(Context context) {
        super(context, DATEBASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TodoContract.SQL_CREAT_TABLE);
        Log.d(TAG, "onCreate:"+ TodoContract.SQL_CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
