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

import me.zhangls.rilintech.model.MedicalHistoryRecord;

/**
 * Created by rilintech on 15/8/4.
 */
public class MedicalHistoryRecordManager {

    public static final String DB_NAME = "boaikangfu";
    public static final String TABLE_NAME = "medical_history_records";
    public static final int DB_VERSION = 2;
    private static final String TAG = "medical_history_records";
    private Context mContext;
    public static final String DATABASE_CREATE = "create table if not exists medical_history_records (" +
            "_id Integer primary key autoincrement,"
            + " medical_history_date  varchar(255), "
            + " medical_history_relator varchar(255), "
            + " medical_history_reliable varchar(255), "
            + " main_suit varchar(255), "
            + " disease_history varchar(255), "
            + " past_history varchar(255), "
            + " medicalHistoryRecord_uuid varchar(255) "
            + " ) ";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    public MedicalHistoryRecordManager(Context context) {

        mContext = context;
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
            Log.i(TAG, "db.getVersion()=" + db.getVersion());
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    public void openDataBase() throws SQLException {

        mDatabaseHelper = new DataBaseManagementHelper(mContext);

        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {

        mDatabaseHelper.close();
        mDatabaseHelper=null;
        mSQLiteDatabase.close();
        mSQLiteDatabase=null;
    }

    /**
     * 删除某一行
     */
    public void removeEntry(long _rowIndex) {

        mSQLiteDatabase.delete(TABLE_NAME, "_id = " + _rowIndex, null);
    }

    /**
     * 插入一条疾病记录
     *
     * @param medicalHistoryRecord 疾病记录
     */
    public void addMedicalHistoryRecord(MedicalHistoryRecord medicalHistoryRecord) {
        mSQLiteDatabase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put("medical_history_date", medicalHistoryRecord.getMedical_history_date());
            cv.put("medical_history_relator", medicalHistoryRecord.getMedical_history_relator());
            cv.put("medical_history_reliable", medicalHistoryRecord.getMedical_history_reliable());
            cv.put("main_suit", medicalHistoryRecord.getMain_suit());
            cv.put("disease_history", medicalHistoryRecord.getDisease_history());
            cv.put("past_history", medicalHistoryRecord.getPast_history());
            cv.put("medicalHistoryRecord_uuid", medicalHistoryRecord.getMhr_uuid());
            mSQLiteDatabase.insert(TABLE_NAME, null, cv);
            mSQLiteDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
    }

    /**
     * 批量插入病史记录
     *
     * @param medicalHistoryRecords
     */
    public void addMedicalHistoryRecords(List<MedicalHistoryRecord> medicalHistoryRecords) {
        for ( MedicalHistoryRecord medicalHistoryRecord: medicalHistoryRecords) {
            this.addMedicalHistoryRecord(medicalHistoryRecord);
        }
    }

    /**
     * 获取一条记录
     * @param mhr_uuid
     * @return
     */
    public MedicalHistoryRecord queryMedicalHistoryRecord(String mhr_uuid){
        Cursor c = mSQLiteDatabase.rawQuery("select * from medical_history_records where medicalHistoryRecord_uuid=?",
                new String[]{mhr_uuid});
        MedicalHistoryRecord mhr = null;
        while(c.moveToNext()){
            mhr = new MedicalHistoryRecord();
            mhr.setMedical_history_date(c.getString(c.getColumnIndex("medical_history_date")));
            mhr.setMedical_history_relator(c.getString(c.getColumnIndex("medical_history_relator")));
            mhr.setMedical_history_reliable(c.getString(c.getColumnIndex("medical_history_reliable")));
            mhr.setMain_suit(c.getString(c.getColumnIndex("main_suit")));
            mhr.setDisease_history(c.getString(c.getColumnIndex("disease_history")));
            mhr.setPast_history(c.getString(c.getColumnIndex("past_history")));
            mhr.setMhr_uuid(c.getString(c.getColumnIndex("medicalHistoryRecord_uuid")));
        }
        return mhr;
    }



    /**
     * 查找所有的病史记录
     *
     * @return medicalHistoryRecords
     */
    public List<MedicalHistoryRecord> queryAllMedicalHistoryRecords() {
        Cursor cursor = this.mSQLiteDatabase
                .rawQuery("select * from medical_history_records order by _id desc", null);
        List<MedicalHistoryRecord> medicalHistoryRecords
                = new ArrayList<>();

        while (cursor.moveToNext()) {
            MedicalHistoryRecord medicalHistoryRecord = new MedicalHistoryRecord();
            medicalHistoryRecord.setMedical_history_date(cursor
                    .getString(cursor.getColumnIndex("medical_history_date")));
            medicalHistoryRecord.setMedical_history_relator(cursor
                    .getString(cursor.getColumnIndex("medical_history_relator")));
            medicalHistoryRecord.setMedical_history_reliable(cursor
                    .getString(cursor.getColumnIndex("medical_history_reliable")));
            medicalHistoryRecord.setMain_suit(cursor
                    .getString(cursor.getColumnIndex("main_suit")));
            medicalHistoryRecord.setPast_history(cursor
                    .getString(cursor.getColumnIndex("past_history")));
            medicalHistoryRecord.setDisease_history(cursor
                    .getString(cursor.getColumnIndex("disease_history")));
            medicalHistoryRecord.setMhr_uuid(cursor
                    .getString(cursor.getColumnIndex("medicalHistoryRecord_uuid")));

            medicalHistoryRecords.add(medicalHistoryRecord);
        }
        cursor.close();
        return medicalHistoryRecords;
    }

    /**
     * 更新一条数据
     * @param mhr
     * @param mhr_uuid
     */
    public void updateMedicalHistoryRecords(MedicalHistoryRecord mhr,String mhr_uuid){
        ContentValues values = new ContentValues();
        values.put("medical_history_date",mhr.getMedical_history_date());
        values.put("medical_history_relator",mhr.getMedical_history_relator());
        values.put("medical_history_reliable",mhr.getMedical_history_reliable());
        values.put("main_suit",mhr.getMain_suit());
        values.put("past_history",mhr.getPast_history());
        values.put("disease_history",mhr.getDisease_history());
        values.put("medicalHistoryRecord_uuid",mhr_uuid);

        String whereClause = "medicalHistoryRecord_uuid = ? ";
        String[]whereArgs = {mhr_uuid};

        mSQLiteDatabase.update(TABLE_NAME,values,whereClause,whereArgs);
    }

}
