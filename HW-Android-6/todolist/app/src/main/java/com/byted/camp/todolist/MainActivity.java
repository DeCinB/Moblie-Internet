package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.operation.activity.DatabaseActivity;
import com.byted.camp.todolist.operation.activity.DebugActivity;
import com.byted.camp.todolist.operation.activity.SettingActivity;
import com.byted.camp.todolist.operation.db.FeedReaderContract;
import com.byted.camp.todolist.ui.NoteListAdapter;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BDCMainActivity";
    private static final int REQUEST_CODE_ADD = 1002;
    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;
    private TodoDbHelper todoDbHelper;
    private SimpleDateFormat format = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(MainActivity.this, NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });
        todoDbHelper=new TodoDbHelper(this);

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            case R.id.action_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            notesAdapter.refresh(loadNotesFromDatabase());
        }
    }

    private List<Note> loadNotesFromDatabase() {
        // TODO 从数据库中查询数据，并转换成 JavaBeans
        List<Note> notes= new ArrayList();
        SQLiteDatabase db=todoDbHelper.getReadableDatabase();
        String[] projection={
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUNMN_NAME_DATE,
                TodoContract.TodoEntry.COLUNMN_NAME_CONTENT,
                TodoContract.TodoEntry.COLUNMN_NAME_COMPLETE,
                TodoContract.TodoEntry.COLUNMN_NAME_PRIOTITY
        };

        String order=TodoContract.TodoEntry.COLUNMN_NAME_COMPLETE+','+
                TodoContract.TodoEntry.COLUNMN_NAME_PRIOTITY+','+
                TodoContract.TodoEntry.COLUNMN_NAME_DATE;

        Cursor cursor=db.query(
                TodoContract.TodoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                order
        );

        while (cursor.moveToNext()){
            long toDoid=cursor.getLong(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID));
            String date_str= cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUNMN_NAME_DATE));
            String content=cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUNMN_NAME_CONTENT));
            State state=State.from(cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUNMN_NAME_COMPLETE)));
            int priority=cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUNMN_NAME_PRIOTITY));

            Note note=new Note(toDoid);
            note.setContent(content);
            note.setPriority(priority);
            note.setState(state);
            Date date=null;
            try{
                date=format.parse(date_str);
            }catch (java.text.ParseException e){
                Log.d(TAG, "loadNotesFromDatabase: 日期转换错误");
            }
            note.setDate(date);
            notes.add(note);
        }
        return notes;
    }

    private void deleteNote(Note note) {
        // TODO 删除数据
        SQLiteDatabase db=todoDbHelper.getWritableDatabase();

        db.delete(
                TodoContract.TodoEntry.TABLE_NAME,
                TodoContract.TodoEntry.COLUNMN_NAME_CONTENT+"=?",
                new String[]{note.getContent()});
        Log.d(TAG, "deleteNote: success"+note.getContent());
        notesAdapter.refresh(loadNotesFromDatabase());
    }

    private void updateNode(Note note) {
        // 更新数据
        SQLiteDatabase db=todoDbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        note.setState(State.DONE);

        contentValues.put(TodoContract.TodoEntry.COLUNMN_NAME_COMPLETE,1);
        db.update(
                TodoContract.TodoEntry.TABLE_NAME,
                contentValues,
                TodoContract.TodoEntry.COLUNMN_NAME_CONTENT+"=?",
                new String[]{note.getContent()}
        );
        Log.d(TAG, "updateNode: "+note.getContent());
        notesAdapter.refresh(loadNotesFromDatabase());
    }

}
