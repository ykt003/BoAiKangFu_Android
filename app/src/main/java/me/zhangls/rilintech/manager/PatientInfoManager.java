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

import me.zhangls.rilintech.model.PatientInfo;

/**
 * Created by rilintech on 15/8/3.
 */
public class PatientInfoManager {


    public static final String DB_NAME = "boaikangfu";
    public static final String TABLE_NAME = "patient_infos";
    public static final int DB_VERSION = 2;
    private static final String TAG = "patient_infos";
    private Context mContext;
    public static final String DATABASE_CREATE = " create table if not exists patient_infos (patient_uuid  varchar(255) primary key not null,"
            + " p_bah Integer, name  varchar(255), sex varchar(255), age Integer, birthday varchar(255), province_id varchar(255), "
            + " nation varchar(255), zhiwei varchar(255), wh_chd varchar(255), hy_zhk varchar(255), p_keshi varchar(255), p_jfly varchar(255), "
            + " first_releate_person varchar(255), tel Integer, height Integer, weight Integer, p_bmi double, p_sqks varchar(255), "
            + " p_zhenduan varchar(255), lczd_icd varchar(255), p_bbbw varchar(255), bbbw_icd varchar(255), p_startdate varchar(255), "
            + " p_bingcheng varchar(255), p_gnza varchar(255), p_fbjg varchar(255), p_zljg varchar(255), p_kfjg varchar(255), "
            + " p_xy Integer, shu_z_ya Integer, p_xl Integer, p_bxl Integer, p_szbxl Integer, p_mb Integer, p_xt Integer, zl_xm varchar(255), "
            + " zl_zt varchar(255), sj_ap varchar(255), zhliao_bw varchar(255), executor varchar(255), lr_time varchar(255))";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    public PatientInfoManager(Context context) {
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

 /*   // 删除某一行
    public void removeEntry(long _rowIndex) {
        mSQLiteDatabase.delete(TABLE_NAME, "_id = " + _rowIndex, null);
    }*/

    /**
     * 插入一个患者
     *
     * @param patientInfo 患者
     */
    public void addPatientInfo(PatientInfo patientInfo) {

        mSQLiteDatabase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
           cv.put("patient_uuid",patientInfo.getmPatient_uuid());
           cv.put("p_bah",patientInfo.getmP_bah());
           cv.put("name",patientInfo.getmName());
           cv.put("sex",patientInfo.getmSex());
           cv.put("age",patientInfo.getmAge());
           cv.put("birthday",patientInfo.getmBirthday());
           cv.put("province_id",patientInfo.getmProvince_id());
           cv.put("nation",patientInfo.getmNation());
           cv.put("zhiwei",patientInfo.getmZhiwei());
           cv.put("wh_chd",patientInfo.getmWh_chd());
           cv.put("hy_zhk",patientInfo.getmHy_zhk());
           cv.put("p_keshi",patientInfo.getmP_keshi());
           cv.put("p_jfly",patientInfo.getmP_jfly());
           cv.put("first_releate_person",patientInfo.getmFirst_releate_person());
           cv.put("tel",patientInfo.getmTel());
           cv.put("height",patientInfo.getmHeight());
           cv.put("weight",patientInfo.getmWeight());
           cv.put("p_bmi",patientInfo.getmP_bmi());
           cv.put("p_sqks",patientInfo.getmP_sqks());
           cv.put("p_zhenduan",patientInfo.getmP_zhenduan());
           cv.put("lczd_icd",patientInfo.getmLczd_icd());
           cv.put("p_bbbw",patientInfo.getmP_bbbw());
           cv.put("bbbw_icd",patientInfo.getmBbbw_icd());
           cv.put("p_startdate",patientInfo.getmP_startdate());
           cv.put("p_bingcheng",patientInfo.getmP_bingcheng());
           cv.put("p_gnza",patientInfo.getmP_gnza());
           cv.put("p_fbjg",patientInfo.getmP_fbjg());
           cv.put("p_zljg",patientInfo.getmP_zljg());
           cv.put("p_kfjg",patientInfo.getmP_kfjg());
           cv.put("p_xy",patientInfo.getmP_xy());
           cv.put("shu_z_ya",patientInfo.getmShu_z_ya());
           cv.put("p_xl",patientInfo.getmP_xl());
           cv.put("p_bxl",patientInfo.getmP_bxl());
           cv.put("p_szbxl",patientInfo.getmP_szbxl());
           cv.put("p_mb",patientInfo.getmP_mb());
           cv.put("p_xt",patientInfo.getmP_xt());
           cv.put("zl_xm",patientInfo.getmZl_xm());
           cv.put("zl_zt",patientInfo.getmZl_zt());
           cv.put("sj_ap",patientInfo.getmSj_ap());
           cv.put("zhliao_bw",patientInfo.getmZhliao_bw());
           cv.put("executor",patientInfo.getmExecutor());
           cv.put("lr_time",patientInfo.getmLr_time());

            long result =  mSQLiteDatabase.insert(TABLE_NAME, null, cv);
            if(result!=-1){
                mSQLiteDatabase.setTransactionSuccessful();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mSQLiteDatabase.endTransaction();
        }


    }

    /**
     * 插入批量的患者
     *
     * @param patientInfos
     */
    public void addPatientInfos(List<PatientInfo> patientInfos) {
        for (PatientInfo patientInfo : patientInfos) {
            this.addPatientInfo(patientInfo);
        }
    }

    /**
     * 查找所有的患者
     *
     * @return
     */
    public List<PatientInfo> patientInfos() {
        // Cursor cursor = this.mSQLiteDatabase.rawQuery("select * from patient_infos order by p_bah desc", null);
        Cursor cursor = this.mSQLiteDatabase.rawQuery("select * from patient_infos", null);
        List<PatientInfo> patientInfoList = new ArrayList<PatientInfo>();

        while (cursor.moveToNext()) {
            PatientInfo patientInfo  = new PatientInfo();
            patientInfo.setmPatient_uuid(cursor.getString(cursor.getColumnIndex("patient_uuid")));
            patientInfo.setmP_bah(cursor.getInt(cursor.getColumnIndex("p_bah")));
            patientInfo.setmName(cursor.getString(cursor.getColumnIndex("name")));
            patientInfo.setmSex(cursor.getString(cursor.getColumnIndex("sex")));
            patientInfo.setmAge(cursor.getInt(cursor.getColumnIndex("age")));
            patientInfo.setmBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            patientInfo.setmProvince_id(cursor.getString(cursor.getColumnIndex("province_id")));
            patientInfo.setmNation(cursor.getString(cursor.getColumnIndex("nation")));
            patientInfo.setmZhiwei(cursor.getString(cursor.getColumnIndex("zhiwei")));
            patientInfo.setmWh_chd(cursor.getString(cursor.getColumnIndex("wh_chd")));
            patientInfo.setmHy_zhk(cursor.getString(cursor.getColumnIndex("hy_zhk")));
            patientInfo.setmP_keshi(cursor.getString(cursor.getColumnIndex("p_keshi")));
            patientInfo.setmP_jfly(cursor.getString(cursor.getColumnIndex("p_jfly")));
            patientInfo.setmFirst_releate_person(cursor.getString(cursor.getColumnIndex("first_releate_person")));
            patientInfo.setmTel(cursor.getString(cursor.getColumnIndex("tel")));
            patientInfo.setmHeight(cursor.getString(cursor.getColumnIndex("height")));
            patientInfo.setmWeight(cursor.getInt(cursor.getColumnIndex("weight")));
            patientInfo.setmP_bmi(cursor.getInt(cursor.getColumnIndex("p_bmi")));
            patientInfo.setmP_sqks(cursor.getString(cursor.getColumnIndex("p_sqks")));
            patientInfo.setmP_zhenduan(cursor.getString(cursor.getColumnIndex("p_zhenduan")));
            patientInfo.setmLczd_icd(cursor.getString(cursor.getColumnIndex("lczd_icd")));
            patientInfo.setmP_bbbw(cursor.getString(cursor.getColumnIndex("p_bbbw")));
            patientInfo.setmBbbw_icd(cursor.getString(cursor.getColumnIndex("bbbw_icd")));
            patientInfo.setmP_startdate(cursor.getString(cursor.getColumnIndex("p_startdate")));
            patientInfo.setmP_bingcheng(cursor.getString(cursor.getColumnIndex("p_bingcheng")));
            patientInfo.setmP_gnza(cursor.getString(cursor.getColumnIndex("p_gnza")));
            patientInfo.setmP_fbjg(cursor.getString(cursor.getColumnIndex("p_fbjg")));
            patientInfo.setmP_zljg(cursor.getString(cursor.getColumnIndex("p_zljg")));
            patientInfo.setmP_kfjg(cursor.getString(cursor.getColumnIndex("p_kfjg")));
            patientInfo.setmP_xy(cursor.getInt(cursor.getColumnIndex("p_xy")));
            patientInfo.setmShu_z_ya(cursor.getInt(cursor.getColumnIndex("shu_z_ya")));
            patientInfo.setmP_xl(cursor.getInt(cursor.getColumnIndex("p_xl")));
            patientInfo.setmP_bxl(cursor.getInt(cursor.getColumnIndex("p_bxl")));
            patientInfo.setmP_szbxl(cursor.getInt(cursor.getColumnIndex("p_szbxl")));
            patientInfo.setmP_mb(cursor.getInt(cursor.getColumnIndex("p_mb")));
            patientInfo.setmP_xt(cursor.getInt(cursor.getColumnIndex("p_xt")));
            patientInfo.setmZl_xm(cursor.getString(cursor.getColumnIndex("zl_xm")));
            patientInfo.setmZl_zt(cursor.getString(cursor.getColumnIndex("zl_zt")));
            patientInfo.setmSj_ap(cursor.getString(cursor.getColumnIndex("sj_ap")));
            patientInfo.setmZhliao_bw(cursor.getString(cursor.getColumnIndex("zhliao_bw")));
            patientInfo.setmExecutor(cursor.getString(cursor.getColumnIndex("executor")));
            patientInfo.setmLr_time(cursor.getString(cursor.getColumnIndex("lr_time")));

            patientInfoList.add(patientInfo);
        }
        cursor.close();
        return patientInfoList;
    }

    /**
     * 通过patient_uuid 查找患者
     * @param patient_uuid
     * @return
     */
    public PatientInfo patientInfo(String patient_uuid){
       // Cursor cursor = this.mSQLiteDatabase.rawQuery("select * from patient_infos where patient_uuid = ? order by p_bah desc", new String[]{patient_uuid});
        Cursor cursor = this.mSQLiteDatabase.rawQuery("select * from patient_infos where patient_uuid = ? ", new String[]{patient_uuid});
        PatientInfo  patientInfo=null;
        while (cursor.moveToNext()){
            patientInfo = new PatientInfo();
            patientInfo.setmPatient_uuid(cursor.getString(cursor.getColumnIndex("patient_uuid")));
            patientInfo.setmP_bah(cursor.getInt(cursor.getColumnIndex("p_bah")));
            patientInfo.setmName(cursor.getString(cursor.getColumnIndex("name")));
            patientInfo.setmSex(cursor.getString(cursor.getColumnIndex("sex")));
            patientInfo.setmAge(cursor.getInt(cursor.getColumnIndex("age")));
            patientInfo.setmBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            patientInfo.setmProvince_id(cursor.getString(cursor.getColumnIndex("province_id")));
            patientInfo.setmNation(cursor.getString(cursor.getColumnIndex("nation")));
            patientInfo.setmZhiwei(cursor.getString(cursor.getColumnIndex("zhiwei")));
            patientInfo.setmWh_chd(cursor.getString(cursor.getColumnIndex("wh_chd")));
            patientInfo.setmHy_zhk(cursor.getString(cursor.getColumnIndex("hy_zhk")));
            patientInfo.setmP_keshi(cursor.getString(cursor.getColumnIndex("p_keshi")));
            patientInfo.setmP_jfly(cursor.getString(cursor.getColumnIndex("p_jfly")));
            patientInfo.setmFirst_releate_person(cursor.getString(cursor.getColumnIndex("first_releate_person")));
            patientInfo.setmTel(cursor.getString(cursor.getColumnIndex("tel")));
            patientInfo.setmHeight(cursor.getString(cursor.getColumnIndex("height")));
            patientInfo.setmWeight(cursor.getInt(cursor.getColumnIndex("weight")));
            patientInfo.setmP_bmi(cursor.getInt(cursor.getColumnIndex("p_bmi")));
            patientInfo.setmP_sqks(cursor.getString(cursor.getColumnIndex("p_sqks")));
            patientInfo.setmP_zhenduan(cursor.getString(cursor.getColumnIndex("p_zhenduan")));
            patientInfo.setmLczd_icd(cursor.getString(cursor.getColumnIndex("lczd_icd")));
            patientInfo.setmP_bbbw(cursor.getString(cursor.getColumnIndex("p_bbbw")));
            patientInfo.setmBbbw_icd(cursor.getString(cursor.getColumnIndex("bbbw_icd")));
            patientInfo.setmP_startdate(cursor.getString(cursor.getColumnIndex("p_startdate")));
            patientInfo.setmP_bingcheng(cursor.getString(cursor.getColumnIndex("p_bingcheng")));
            patientInfo.setmP_gnza(cursor.getString(cursor.getColumnIndex("p_gnza")));
            patientInfo.setmP_fbjg(cursor.getString(cursor.getColumnIndex("p_fbjg")));
            patientInfo.setmP_zljg(cursor.getString(cursor.getColumnIndex("p_zljg")));
            patientInfo.setmP_kfjg(cursor.getString(cursor.getColumnIndex("p_kfjg")));
            patientInfo.setmP_xy(cursor.getInt(cursor.getColumnIndex("p_xy")));
            patientInfo.setmShu_z_ya(cursor.getInt(cursor.getColumnIndex("shu_z_ya")));
            patientInfo.setmP_xl(cursor.getInt(cursor.getColumnIndex("p_xl")));
            patientInfo.setmP_bxl(cursor.getInt(cursor.getColumnIndex("p_bxl")));
            patientInfo.setmP_szbxl(cursor.getInt(cursor.getColumnIndex("p_szbxl")));
            patientInfo.setmP_mb(cursor.getInt(cursor.getColumnIndex("p_mb")));
            patientInfo.setmP_xt(cursor.getInt(cursor.getColumnIndex("p_xt")));
            patientInfo.setmZl_xm(cursor.getString(cursor.getColumnIndex("zl_xm")));
            patientInfo.setmZl_zt(cursor.getString(cursor.getColumnIndex("zl_zt")));
            patientInfo.setmSj_ap(cursor.getString(cursor.getColumnIndex("sj_ap")));
            patientInfo.setmZhliao_bw(cursor.getString(cursor.getColumnIndex("zhliao_bw")));
            patientInfo.setmExecutor(cursor.getString(cursor.getColumnIndex("executor")));
            patientInfo.setmLr_time(cursor.getString(cursor.getColumnIndex("lr_time")));
        }
        cursor.close();
        return patientInfo;
    }
    /**
     * 更新一个患者
     *
     * @param patientInfo 患者
     */
    public void updatePatientInfo(PatientInfo patientInfo) {

       mSQLiteDatabase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put("patient_uuid",patientInfo.getmPatient_uuid());
            cv.put("p_bah",patientInfo.getmP_bah());
            cv.put("name",patientInfo.getmName());
            cv.put("sex",patientInfo.getmSex());
            cv.put("age",patientInfo.getmAge());
            cv.put("birthday",patientInfo.getmBirthday());
            cv.put("province_id",patientInfo.getmProvince_id());
            cv.put("nation",patientInfo.getmNation());
            cv.put("zhiwei",patientInfo.getmZhiwei());
            cv.put("wh_chd",patientInfo.getmWh_chd());
            cv.put("hy_zhk",patientInfo.getmHy_zhk());
            cv.put("p_keshi",patientInfo.getmP_keshi());
            cv.put("p_jfly",patientInfo.getmP_jfly());
            cv.put("first_releate_person",patientInfo.getmFirst_releate_person());
            cv.put("tel",patientInfo.getmTel());
            cv.put("height",patientInfo.getmHeight());
            cv.put("weight",patientInfo.getmWeight());
            cv.put("p_bmi",patientInfo.getmP_bmi());
            cv.put("p_sqks",patientInfo.getmP_sqks());
            cv.put("p_zhenduan",patientInfo.getmP_zhenduan());
            cv.put("lczd_icd",patientInfo.getmLczd_icd());
            cv.put("p_bbbw",patientInfo.getmP_bbbw());
            cv.put("bbbw_icd",patientInfo.getmBbbw_icd());
            cv.put("p_startdate",patientInfo.getmP_startdate());
            cv.put("p_bingcheng",patientInfo.getmP_bingcheng());
            cv.put("p_gnza",patientInfo.getmP_gnza());
            cv.put("p_fbjg",patientInfo.getmP_fbjg());
            cv.put("p_zljg",patientInfo.getmP_zljg());
            cv.put("p_kfjg",patientInfo.getmP_kfjg());
            cv.put("p_xy",patientInfo.getmP_xy());
            cv.put("shu_z_ya",patientInfo.getmShu_z_ya());
            cv.put("p_xl",patientInfo.getmP_xl());
            cv.put("p_bxl",patientInfo.getmP_bxl());
            cv.put("p_szbxl",patientInfo.getmP_szbxl());
            cv.put("p_mb",patientInfo.getmP_mb());
            cv.put("p_xt",patientInfo.getmP_xt());
            cv.put("zl_xm",patientInfo.getmZl_xm());
            cv.put("zl_zt",patientInfo.getmZl_zt());
            cv.put("sj_ap",patientInfo.getmSj_ap());
            cv.put("zhliao_bw",patientInfo.getmZhliao_bw());
            cv.put("executor",patientInfo.getmExecutor());
            cv.put("lr_time",patientInfo.getmLr_time());
            String whereClause="patient_uuid = ?";
            String[]whereArgs=new String[]{patientInfo.getmPatient_uuid()};
            mSQLiteDatabase.update(TABLE_NAME,cv,whereClause,whereArgs);
            long result =  mSQLiteDatabase.update(TABLE_NAME,cv,whereClause,whereArgs);
            if(result!=-1){
                mSQLiteDatabase.setTransactionSuccessful();
           }
       }catch (Exception e){
           e.printStackTrace();
        }finally {
             mSQLiteDatabase.endTransaction();
       }
    }

    /**
     * 删除某条记录
     * @param uuid
     */
    public void deletePatientInfoFromUUID(String uuid){
        String whereClause="patient_uuid=?";
        String[]whereArgs=new String[]{uuid};
        mSQLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs);
    }
}
