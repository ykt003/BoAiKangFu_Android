package me.zhangls.rilintech.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.model.MedicalRecord;

/**
 * Created by rilintech on 15/8/3.
 */
public class MedicalRecordManager {


    public static final String DB_NAME = "boaikangfu";
    public static final String TABLE_NAME = "hospital_medical_record_infos";
    public static final int DB_VERSION = 2;
    private static final String TAG = "hospital_medical_record_infos";
    private Context mContext;
    public static final String DATABASE_CREATE = "create table if not exists hospital_medical_record_infos (_id integer primary key autoincrement ,"
            + " hospitalRoomRecord  integer not null , hospitalDepartment varchar(255) ,"
            + " hospitalRecoveryDiagnosis varchar(255) , hospitalClinicalDiagnosis varchar(255) ,"
            + " hospitalChiefComplaint varchar(255) , hospitalPastHistory varchar(255))";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    public MedicalRecordManager(Context pContext) {
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

    // 删除某一行
    public void removeEntry(long _rowIndex) {

        mSQLiteDatabase.delete(TABLE_NAME, "_id = " + _rowIndex, null);
    }

    /**
     * 插入一个记录
     *
     * @param
     */
    public void addMedicalRecordInfo(MedicalRecord PMedicalRecord) {
        mSQLiteDatabase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put("hospitalRoomRecord", PMedicalRecord.getmHospitalRoomRecord());
            cv.put("hospitalDepartment", PMedicalRecord.getmHospitalDepartment());
            cv.put("hospitalRecoveryDiagnosis", PMedicalRecord.getmHospitalRecoveryDiagnosis());
            cv.put("hospitalClinicalDiagnosis", PMedicalRecord.getmHospitalClinicalDiagnosis());
            cv.put("hospitalChiefComplaint", PMedicalRecord.getmHospitalChiefComplaint());
            cv.put("hospitalPastHistory", PMedicalRecord.getmHospitalPastHistory());

            long result = mSQLiteDatabase.insert(TABLE_NAME, null, cv);
            if(result!=-1){
                mSQLiteDatabase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
    }

    /**
     * 插入批量的记录
     *
     * @param
     */
    public void addPatientInfos(List<MedicalRecord> pMedicalRecords) {
        for (MedicalRecord _medicalRecord : pMedicalRecords) {
            this.addMedicalRecordInfo(_medicalRecord);
        }
    }

    /**
     * 查找所有的患者
     *
     * @return
     */
    public List<MedicalRecord> medicalRecords() {
        Cursor cursor = this.mSQLiteDatabase.rawQuery("select * from hospital_medical_record_infos", null);
        List<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>();

        while (cursor.moveToNext()) {
            MedicalRecord _medicalRecord = new MedicalRecord();
            _medicalRecord.setmHospitalChiefComplaint(cursor.getString(cursor.getColumnIndex("hospitalChiefComplaint")));
            _medicalRecord.setmHospitalClinicalDiagnosis(cursor.getString(cursor.getColumnIndex("hospitalClinicalDiagnosis")));
            _medicalRecord.setmHospitalDepartment(cursor.getString(cursor.getColumnIndex("hospitalDepartment")));
            _medicalRecord.setmHospitalPastHistory(cursor.getString(cursor.getColumnIndex("hospitalPastHistory")));
            _medicalRecord.setmHospitalRecoveryDiagnosis(cursor.getString(cursor.getColumnIndex("hospitalRecoveryDiagnosis")));
            _medicalRecord.setmHospitalRoomRecord(cursor.getInt(cursor.getColumnIndex("hospitalRoomRecord")));

            medicalRecordList.add(_medicalRecord);
        }
        cursor.close();
        return medicalRecordList;
    }

}
