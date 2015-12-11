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

import me.zhangls.rilintech.model.TableAlynInfo2;

/**
 * Created by YANG on 15/8/22.
 */
public class TableAlyn2Manager {

    public static final String DB_NAME = "boaikangfu";
    public static final String TABLE_NAME = "table_alyn_infos2";
    public static final int DB_VERSION = 2;
    private static final String TAG = "TableAlyn1Manager";
    private Context mContext;
    public static final String DATABASE_CREATE = "create table if not exists table_alyn_infos2("
            + " _id Integer primary key autoincrement, "
            + " flag varchar(255), "
            + " alyn_uuid varchar(255), "
            + " date varchar(255), "
            + " instructions varchar(255), "
            + " user_name varchar(255), "
            + " score_t Integer, "
            + " scores Integer, "
            + " answer1 varchar(255), "
            + " answer2 varchar(255), "
            + " answer3 varchar(255), "
            + " answer4 varchar(255), "
            + " answer5 varchar(255), "
            + " answer6 varchar(255), "
            + " answer7 varchar(255), "
            + " answer8 varchar(255), "
            + " answer9 varchar(255), "
            + " answer10 varchar(255), "
            + " answer11 varchar(255), "
            + " answer12 varchar(255), "
            + " answer13 varchar(255), "
            + " answer14 varchar(255), "
            + " answer15 varchar(255), "
            + " answer16 varchar(255), "
            + " answer17 varchar(255), "
            + " answer18 varchar(255), "
            + " answer19 varchar(255), "
            + " answer20 varchar(255), "
            + " answer21 varchar(255), "
            + " answer22 varchar(255), "
            + " answer23 varchar(255), "
            + " answer24 varchar(255), "
            + " answer25 varchar(255), "
            + " answer26 varchar(255), "
            + " answer27 varchar(255) "
            + " ) ";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    public TableAlyn2Manager(Context context) {

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
            Log.d(TAG, "db.getVersion()=" + db.getVersion());
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

    /**
     * 插入一条数据
     * @param info
     */
    public void insertOne(TableAlynInfo2 info){
        ContentValues values = new ContentValues();
        values.put("flag",info.getFlag());
        values.put("alyn_uuid",info.getAlyn_uuid());
        values.put("date",info.getDate());
        values.put("instructions",info.getInstructions());
        values.put("user_name",info.getUser_name());
        values.put("scores",info.getScores());
        values.put("score_t",info.getScore_t());
        values.put("answer1",info.getAnswer1());
        values.put("answer2",info.getAnswer2());
        values.put("answer3",info.getAnswer3());
        values.put("answer4",info.getAnswer4());
        values.put("answer5",info.getAnswer5());
        values.put("answer6",info.getAnswer6());
        values.put("answer7",info.getAnswer7());
        values.put("answer8",info.getAnswer8());
        values.put("answer9",info.getAnswer9());
        values.put("answer10",info.getAnswer10());
        values.put("answer11",info.getAnswer11());
        values.put("answer12",info.getAnswer12());
        values.put("answer13", info.getAnswer13());
        values.put("answer14", info.getAnswer14());
        values.put("answer15", info.getAnswer15());
        values.put("answer16", info.getAnswer16());
        values.put("answer17", info.getAnswer17());
        values.put("answer18", info.getAnswer18());
        values.put("answer19", info.getAnswer19());
        values.put("answer20", info.getAnswer20());
        values.put("answer21", info.getAnswer21());
        values.put("answer22", info.getAnswer22());
        values.put("answer23", info.getAnswer23());
        values.put("answer24", info.getAnswer24());
        values.put("answer25", info.getAnswer25());
        values.put("answer26", info.getAnswer26());
        values.put("answer27", info.getAnswer27());

        mSQLiteDatabase.insert(TABLE_NAME, null, values);
    }

    /**
     * 更新一条
     * @param uuid
     * @param info
     */
    public void updateOne(String uuid, TableAlynInfo2 info){
        ContentValues values = new ContentValues();
        values.put("flag",info.getFlag());
        values.put("alyn_uuid",info.getAlyn_uuid());
        values.put("date",info.getDate());
        values.put("instructions",info.getInstructions());
        values.put("user_name",info.getUser_name());
        values.put("scores",info.getScores());
        values.put("score_t",info.getScore_t());
        values.put("answer1",info.getAnswer1());
        values.put("answer2",info.getAnswer2());
        values.put("answer3",info.getAnswer3());
        values.put("answer4",info.getAnswer4());
        values.put("answer5",info.getAnswer5());
        values.put("answer6",info.getAnswer6());
        values.put("answer7",info.getAnswer7());
        values.put("answer8",info.getAnswer8());
        values.put("answer9",info.getAnswer9());
        values.put("answer10",info.getAnswer10());
        values.put("answer11",info.getAnswer11());
        values.put("answer12",info.getAnswer12());
        values.put("answer13", info.getAnswer13());
        values.put("answer14", info.getAnswer14());
        values.put("answer15", info.getAnswer15());
        values.put("answer16", info.getAnswer16());
        values.put("answer17", info.getAnswer17());
        values.put("answer18", info.getAnswer18());
        values.put("answer19", info.getAnswer19());
        values.put("answer20", info.getAnswer20());
        values.put("answer21", info.getAnswer21());
        values.put("answer22", info.getAnswer22());
        values.put("answer23", info.getAnswer23());
        values.put("answer24", info.getAnswer24());
        values.put("answer25", info.getAnswer25());
        values.put("answer26", info.getAnswer26());
        values.put("answer27", info.getAnswer27());

        String whereClause = "alyn_uuid = ?";
        String []whereArgs = new String[]{uuid};
        mSQLiteDatabase.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    /**
     *  查询
     */
    public Cursor cursorQueryOne(String instructions, String username) {
        String selection ="instructions=? and user_name=?";
        String[] selectionArgs =new  String[]{instructions,username};
        return mSQLiteDatabase.query(TABLE_NAME, null,selection , selectionArgs, null, null, null);

    }

    /**
     * 查询
     * @param instructions
     * @param username
     * @return info
     */
    public TableAlynInfo2 queryOne(String instructions, String username){

        Cursor c = cursorQueryOne(instructions,username);
        TableAlynInfo2 info = null;
        while(c.moveToNext()){
            info = new TableAlynInfo2();
            info.setFlag(c.getString(c.getColumnIndex("flag")));
            info.setAlyn_uuid(c.getString(c.getColumnIndex("alyn_uuid")));
            info.setDate(c.getString(c.getColumnIndex("date")));
            info.setInstructions(c.getString(c.getColumnIndex("instructions")));
            info.setUser_name(c.getString(c.getColumnIndex("user_name")));
            info.setScores(Integer.parseInt(c.getString(c.getColumnIndex("scores"))));
            info.setScore_t(Integer.parseInt(c.getString(c.getColumnIndex("score_t"))));
            info.setAnswer1(c.getString(c.getColumnIndex("answer1")));
            info.setAnswer2(c.getString(c.getColumnIndex("answer2")));
            info.setAnswer3(c.getString(c.getColumnIndex("answer3")));
            info.setAnswer4(c.getString(c.getColumnIndex("answer4")));
            info.setAnswer5(c.getString(c.getColumnIndex("answer5")));
            info.setAnswer6(c.getString(c.getColumnIndex("answer6")));
            info.setAnswer7(c.getString(c.getColumnIndex("answer7")));
            info.setAnswer8(c.getString(c.getColumnIndex("answer8")));
            info.setAnswer9(c.getString(c.getColumnIndex("answer9")));
            info.setAnswer10(c.getString(c.getColumnIndex("answer10")));
            info.setAnswer11(c.getString(c.getColumnIndex("answer11")));
            info.setAnswer12(c.getString(c.getColumnIndex("answer12")));
            info.setAnswer13(c.getString(c.getColumnIndex("answer13")));
            info.setAnswer14(c.getString(c.getColumnIndex("answer14")));
            info.setAnswer15(c.getString(c.getColumnIndex("answer15")));
            info.setAnswer16(c.getString(c.getColumnIndex("answer16")));
            info.setAnswer17(c.getString(c.getColumnIndex("answer17")));
            info.setAnswer18(c.getString(c.getColumnIndex("answer18")));
            info.setAnswer19(c.getString(c.getColumnIndex("answer19")));
            info.setAnswer20(c.getString(c.getColumnIndex("answer20")));
            info.setAnswer21(c.getString(c.getColumnIndex("answer21")));
            info.setAnswer22(c.getString(c.getColumnIndex("answer22")));
            info.setAnswer23(c.getString(c.getColumnIndex("answer23")));
            info.setAnswer24(c.getString(c.getColumnIndex("answer24")));
            info.setAnswer25(c.getString(c.getColumnIndex("answer25")));
            info.setAnswer26(c.getString(c.getColumnIndex("answer26")));
            info.setAnswer27(c.getString(c.getColumnIndex("answer27")));

        }
        return info;

    }

    /**
     * 查找当前用户所有的评定说明
     * @param username
     * @return
     */
    public List<String> getAllInstructions(String username){

        List<String> list = null;
        Cursor c = mSQLiteDatabase.rawQuery("select * from  table_alyn_infos2 where user_name = ?", new String[]{username});
        list = new ArrayList<>();
        while (c.moveToNext()){
            String s = c.getString(c.getColumnIndex("instructions"));
            list.add(s);
        }
        return list;
    }

    /**
     * 删除一条评定
     * @param username
     * @param instruction
     */
    public void deleteOne(String username, String instruction){
        String whereClause = "user_name = ? and instructions = ?";
        String [] whereArg = new String[]{username, instruction};
        mSQLiteDatabase.delete(TABLE_NAME,whereClause,whereArg );
    }

}
