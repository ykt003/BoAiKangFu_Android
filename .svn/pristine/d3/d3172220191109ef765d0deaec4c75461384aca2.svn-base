package me.zhangls.rilintech.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.model.MenuLibModel;

/**
 * Created by YANG on 15/11/10.
 */
public class MenuLibManager {

    public static final String DB_NAME = "boaikangfu";
    public static final String TABLE_NAME = "menu_lib_table";
    public static final int DB_VERSION = 2;
    private static final String TAG = "MenuLibManager";
    private Context mContext;
    public static final String DATABASE_CREATE = "create table if not exists menu_lib_table("
            + " _id Integer primary key autoincrement, "
            + " z_name varchar(255), "
            + " z_id varchar(255), "
            + " z_menu_lib_id varchar(255) "
            + " ) ";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    public MenuLibManager(Context context) {

        mContext = context;
    }

    /**
     * 内部类集成SQLiteOpenHelper 连接数据库，打开和关闭数据库
     */
    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {

            super(context, DB_NAME, null, DB_VERSION);
            onCreate(getReadableDatabase());
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

        mDatabaseHelper = new DataBaseManagementHelper(mContext);

        mSQLiteDatabase = mDatabaseHelper.getReadableDatabase();
    }

    public void closeDataBase() throws SQLException {

        mDatabaseHelper.close();
    }
    //插入数据
    public long insertData(MenuLibModel model){

        ContentValues values = new ContentValues();

        values.put("z_id",model.getZ_id());
        values.put("z_name",model.getZ_name());
        values.put("z_menu_lib_id", model.getZ_menu_lib_id());

        return mSQLiteDatabase.insert(TABLE_NAME,null,values);

    }


    public Cursor cursorQuery(String menu_lib_id) {
        String selection ="z_menu_lib_id=?";
        String[] selectionArgs =new  String[]{menu_lib_id};
        return mSQLiteDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

    }

    /**
     * 查找符合条件
     * @param menu_lib_id
     * @return
     */
    public List<MenuLibModel> getMenu(String menu_lib_id){

        List<MenuLibModel> list_left_menu = new ArrayList<>();
        MenuLibModel model = null;
        Cursor cursor = cursorQuery(menu_lib_id);
        while (cursor.moveToNext()){

            model = new MenuLibModel();

            model.setZ_id(cursor.getString(cursor.getColumnIndex("z_id")));
            model.setZ_name(cursor.getString(cursor.getColumnIndex("z_name")));
            model.setZ_menu_lib_id(cursor.getString(cursor.getColumnIndex("z_menu_lib_id")));

            list_left_menu.add(model);

        }

        cursor.close();

        return  list_left_menu;

    }


}
