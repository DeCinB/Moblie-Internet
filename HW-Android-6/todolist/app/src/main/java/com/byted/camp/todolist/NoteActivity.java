package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "BDCNoteActivity";
    private EditText editText;
    private Button addBtn;
    private TodoDbHelper todoDbHelper;
    private SimpleDateFormat format = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss");

    private RadioGroup radioGroup;
    private RadioButton rbtn1;
    private RadioButton rbtn2;
    private RadioButton rbtn3;
    private RadioButton rbtn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);
        todoDbHelper=new TodoDbHelper(this);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        radioGroup=findViewById(R.id.radioGroup);
        rbtn1=findViewById(R.id.radioButton);
        rbtn2=findViewById(R.id.radioButton2);
        rbtn3=findViewById(R.id.radioButton3);
        rbtn4=findViewById(R.id.radioButton4);

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int priority=0;
                if(rbtn1.isChecked())
                    priority=0;
                else if(rbtn2.isChecked())
                    priority=1;
                else if (rbtn3.isChecked())
                    priority=2;
                else if(rbtn4.isChecked())
                    priority=3;

                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim(),priority);
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean saveNote2Database(String content,int priority) {
        // TODO 插入一条新数据，返回是否插入成功
        SQLiteDatabase db=todoDbHelper.getWritableDatabase();

        String date=format.format(new Date());
        ContentValues contentValues=new ContentValues();
        contentValues.put(TodoContract.TodoEntry.COLUNMN_NAME_CONTENT,content);
        contentValues.put(TodoContract.TodoEntry.COLUNMN_NAME_DATE,date);
        contentValues.put(TodoContract.TodoEntry.COLUNMN_NAME_COMPLETE,0);
        contentValues.put(TodoContract.TodoEntry.COLUNMN_NAME_PRIOTITY,priority);

        long rowId=db.insert(
                TodoContract.TodoEntry.TABLE_NAME,
                null,
                contentValues
        );
        if(rowId!=-1){
            Log.d(TAG, "saveNote2Database: ");
            return true;
        }
        return false;
    }

}