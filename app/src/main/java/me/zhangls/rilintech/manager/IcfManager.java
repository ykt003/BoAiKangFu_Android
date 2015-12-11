package me.zhangls.rilintech.manager;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/8/20.
 */
public class IcfManager {
    public static final String DB_NAME = "boaikangfu";
    public static final String TABLE_NAME = "hospital_icf_infos";
    public static final int DB_VERSION = 2;
    private static final String TAG = "hospital_icf_infos";
    private Context mContext;
    public static final String DATABASE_CREATE = "create table if not exists"+TABLE_NAME+"(_id integer primary key autoincrement ,"
            + " term varchar(255) not null)";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    public IcfManager(Context pContext) {
        mContext = pContext;
        mDatabaseHelper = new DataBaseManagementHelper(pContext);
    }

    /**
     * 内部类集成SQLiteOpenHelper 连接数据库，打开和关闭数据库
     */
    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            onCreate(getWritableDatabase());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void openDataBase() throws SQLException {
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {

        mDatabaseHelper.close();
        mDatabaseHelper=null;
        mSQLiteDatabase.close();
        mSQLiteDatabase=null;
    }


}
