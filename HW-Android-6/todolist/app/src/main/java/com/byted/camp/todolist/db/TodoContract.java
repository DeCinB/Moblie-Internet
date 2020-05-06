package com.byted.camp.todolist.db;
import android.provider.BaseColumns;

import java.util.Date;
import java.util.function.ToDoubleBiFunction;

/**
 *
 * @author 1481410986@qq.com （BianDexin）
 * 2020/05/02
 */

public final class TodoContract {
    // TODO 定义表结构和 SQL 语句常量

    //创建表
    public static final String SQL_CREAT_TABLE=
            "CREATE TABLE "+TodoEntry.TABLE_NAME+"("+
                    TodoEntry._ID+" INTEGER PRIMARY KEY,"+
                    TodoEntry.COLUNMN_NAME_CONTENT+" TEXT,"+
                    TodoEntry.COLUNMN_NAME_COMPLETE+" INTEGER,"+
                    TodoEntry.COLUNMN_NAME_DATE+" TEXT,"+
                    TodoEntry.COLUNMN_NAME_PRIOTITY+" INTEGER)";

    //查询所有实例，按是否完成、优先级、时间排序
    public static final String SQL_GET_ENTRIES=
            "SELECT * FROM "+TodoEntry.TABLE_NAME+
                    "ORDER BY "+TodoEntry.COLUNMN_NAME_COMPLETE+","+
                    TodoEntry.COLUNMN_NAME_PRIOTITY+","+
                    TodoEntry.COLUNMN_NAME_DATE+" ASC";


    //表结构
    public class TodoEntry implements BaseColumns {

        public static final String TABLE_NAME="todo";
        public static final String COLUNMN_NAME_DATE="date";
        public static final String COLUNMN_NAME_CONTENT="todoContent";
        public static final String COLUNMN_NAME_COMPLETE="complete";
        public static final String COLUNMN_NAME_PRIOTITY = "priority";
    }
    private TodoContract() {
    }

}
