package me.zhangls.rilintech.model;

/**
 * Created by Administrator on 2015/8/4.
 */
public class MedicalRecord {
    //病房号
    private int mHospitalRoomRecord;
    //科室
    private String mHospitalDepartment;
    //康复诊断
    private String mHospitalRecoveryDiagnosis;
    //临床诊断
    private String mHospitalClinicalDiagnosis;
    //主诉
    private String mHospitalChiefComplaint;
    //既往史
    private String mHospitalPastHistory;

    public int getmHospitalRoomRecord() {
        return mHospitalRoomRecord;
    }

    public void setmHospitalRoomRecord(int mHospitalRoomRecord) {
        this.mHospitalRoomRecord = mHospitalRoomRecord;
    }

    public String getmHospitalDepartment() {
        return mHospitalDepartment;
    }

    public void setmHospitalDepartment(String mHospitalDepartment) {
        this.mHospitalDepartment = mHospitalDepartment;
    }

    public String getmHospitalRecoveryDiagnosis() {
        return mHospitalRecoveryDiagnosis;
    }

    public void setmHospitalRecoveryDiagnosis(String mHospitalRecoveryDiagnosis) {
        this.mHospitalRecoveryDiagnosis = mHospitalRecoveryDiagnosis;
    }

    public String getmHospitalClinicalDiagnosis() {
        return mHospitalClinicalDiagnosis;
    }

    public void setmHospitalClinicalDiagnosis(String mHospitalClinicalDiagnosis) {
        this.mHospitalClinicalDiagnosis = mHospitalClinicalDiagnosis;
    }

    public String getmHospitalChiefComplaint() {
        return mHospitalChiefComplaint;
    }

    public void setmHospitalChiefComplaint(String mHospitalChiefComplaint) {
        this.mHospitalChiefComplaint = mHospitalChiefComplaint;
    }

    public String getmHospitalPastHistory() {
        return mHospitalPastHistory;
    }

    public void setmHospitalPastHistory(String mHospitalPastHistory) {
        this.mHospitalPastHistory = mHospitalPastHistory;
    }
}
