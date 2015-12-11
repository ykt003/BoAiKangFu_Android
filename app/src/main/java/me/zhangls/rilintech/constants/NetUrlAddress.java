package me.zhangls.rilintech.constants;

/**
 * Created by rilintech on 15/9/8.
 */
public class NetUrlAddress {

    //token
    public static final String token = "9ba2a51a1458421c245bace706ec159faf88247327aa2f607a1c8dcdb7f33673119bf62e32bd29723df40542944a5c2de8e50c089e61cee3f242fe4cab343b80";

    //public static final String ipAndPort = "http://121.40.195.149:3005";
    public static final String ipAndPort = "http://192.168.0.119:3001";
    //public static final String ipAndPort = "http://192.168.0.123:3003";
    //public static final String ipAndPort = "http://192.168.23.1:3333";
    //public static final String ipAndPort="http://192.168.0.111:2222";

    /**
     * 修改密码
     */
    public static final String UpdatePassword = ipAndPort + "/app_normal/change_password/1.json?";

    /**
     * 获取模块下的量表数据
     */
    public static final String GetModelTableData = ipAndPort + "/app_pat_profile/profile_show/1.json?";

    //普通表获取数据
    public static final String GetNormalTableData = ipAndPort + "/app_normal/index.json?";
    //普通表更新
    public static final String UpdateNormalTableData = ipAndPort + "/app_normal/update/aaa.json?";
    //普通表添加
    public static final String CreateNormalTableData = ipAndPort + "/app_normal/create/1.json?";
    //普通表删除
    public static final String DeleteNormalTableData = ipAndPort + "/app_normal/destroy/aaa.json?";

    //量表共用url
    public static final String Common_create_url = ipAndPort + "/app_scale/create/1.json?token=" + NetUrlAddress.token;
    public static final String Common_update_url = ipAndPort + "/app_scale/update/1.json?token=" + NetUrlAddress.token;
    public static final String Common_destroy_url = ipAndPort + "/app_scale/destroy/1.json?token=" + NetUrlAddress.token;


    //获取菜单
    public static final String MENU_LIB_URL = ipAndPort + "/menu_libs/app_index/1.json?";
    //获取表数据
    public static final String GET_TABLE_DATA_URL = ipAndPort + "/app_scale/index/1.json?";
    public static final String GET_FLOW_CHART_DATA_URL = ipAndPort + "/app_pat_profile/profile_index/1.json?";

    // 患者添加的字母表
    public static final String ICD_DATA_URL = ipAndPort + "/icd_catalogs/index/1.json?";
    public static final String ICF_DATA_URL = ipAndPort + "/icf_catalogs/index/1.json?";

    //APP登录
    public static final String login_url = ipAndPort + "/login/check/1.json";
    //徒手肌力检查记录表请求数据
    public static final String getData_url = ipAndPort + "/patient_lumbago_manual_muscle_test_evaluations/index/1.json";
    //徒手肌力检查记录表删除
    public static final String delete_url = ipAndPort + "/patient_lumbago_manual_muscle_test_evaluations/destroy/468.json";
    //徒手肌力检查记录表新建
    public static final String create_url = ipAndPort + "/patient_lumbago_manual_muscle_test_evaluations/create/1.json";
    //徒手肌力检查记录表更新
    public static final String update_url = ipAndPort + "/patient_lumbago_manual_muscle_test_evaluations/update/468.json";


    //康复目标请求数据
    public static final String re_getData_url = ipAndPort + "/recovered_goals/index/1.json?";
    //康复目标删除
    public static final String re_delete_url = ipAndPort + "/recovered_goals/destroy/aaa.json?";
    //康复目标新建
    public static final String re_create_url = ipAndPort + "/recovered_goals/create/1.json?";
    //康复目标更新
    public static final String re_update_url = ipAndPort + "/recovered_goals/update/aaa.json?";

    //治疗计划请求数据
    public static final String tp_getData_url = ipAndPort + "/treatment_plans/index/1.json?";
    //治疗计划删除
    public static final String tp_delete_url = ipAndPort + "/treatment_plans/destroy/aaa.json?";
    //治疗计划新建
    public static final String tp_create_url = ipAndPort + "/treatment_plans/create/1.json?";
    //治疗计划更新
    public static final String tp_update_url = ipAndPort + "/treatment_plans/update/aaa.json?";


    //Barthel指数评定（BI）请求数据
    public static final String Barthel_getData_url = ipAndPort + "/patient_barthel_evaluations/index/1.json?";
    //Barthel指数评定（BI）删除
    public static final String Barthel_delete_url = ipAndPort + "/patient_barthel_evaluations/destroy/1.json?";
    //Barthel指数评定（BI）新建
    public static final String Barthel_create_url = ipAndPort + "/patient_barthel_evaluations/create/1.json?";
    //Barthel指数评定（BI）更新
    public static final String Barthel_update_url = ipAndPort + "/patient_barthel_evaluations/update/1.json?";

    //改良Ashworth肌张力评定记录表
    public static final String Ashworth_getData_url = ipAndPort + "/patient_ashworth_levels/index/1.json?";
    //改良Ashworth肌张力评定记录表 删除
    public static final String Ashworth_delete_url = ipAndPort + "/patient_ashworth_levels/destroy/1.json?";
    //改良Ashworth肌张力评定记录表 新建
    public static final String Ashworth_create_url = ipAndPort + "/patient_ashworth_levels/create/1.json?";
    //改良Ashworth肌张力评定记录表 更新
    public static final String Ashworth_update_url = ipAndPort + "/patient_ashworth_levels/update/1.json?";


    //关节活动度记录表(ROM)
    public static final String Rom_getData_url = ipAndPort + "/patient_lumbago_range_of_motion_evaluations/index/1.json?";
    //关节活动度记录表(ROM) 删除
    public static final String Rom_delete_url = ipAndPort + "/patient_lumbago_range_of_motion_evaluations/destroy/aaa.json?";
    //关节活动度记录表(ROM) 新建
    public static final String Rom_create_url = ipAndPort + "/patient_lumbago_range_of_motion_evaluations/create/1.json?";
    //关节活动度记录表(ROM) 更新
    public static final String Rom_update_url = ipAndPort + "/patient_lumbago_range_of_motion_evaluations/update/aaa.json?";


    //Fugl-Meyer评定(Fugl-Meyer Assessment,FMA)
    public static final String Fma_getData_url = ipAndPort + "/patient_fugl_meyer_evaluations/index/1.json?";
    //关节活动度记录表(ROM) 删除
    public static final String Fma_delete_url = ipAndPort + "/patient_fugl_meyer_evaluations/destroy/aaa.json?";
    //关节活动度记录表(ROM) 新建
    public static final String Fma_create_url = ipAndPort + "/patient_fugl_meyer_evaluations/create/1.json?";
    //关节活动度记录表(ROM) 更新
    public static final String Fma_update_url = ipAndPort + "/patient_fugl_meyer_evaluations/update/aaa.json?";

    //粗大运动功能评估量表 (Gross Motor Function Measure,GMFM)
    public static final String Gmfm_getData_url = ipAndPort + "/global/index/1.json?";
    //粗大运动功能评估量表 删除
    public static final String Gmfm_delete_url = ipAndPort + "/global/destroy/1.json?";
    //粗大运动功能评估量表 新建
    public static final String Gmfm_create_url = ipAndPort + "/global/create/1.json?";

    //粗大运动功能评估量表 更新
    public static final String Gmfm_update_url = ipAndPort + "/global/update/1.json?";

    //功能独立性评定(Functional Independence Measurement,FIM)
    public static final String Fim_getData_url = ipAndPort + "/patient_fim_evaluations/index/1.json?";
    //功能独立性评定 删除
    public static final String Fim_delete_url = ipAndPort + "/patient_fim_evaluations/destroy/1.json?";
    //功能独立性评定 新建
    public static final String Fim_create_url = ipAndPort + "/patient_fim_evaluations/create/1.json?";
    //功能独立性评定 更新
    public static final String Fim_update_url = ipAndPort + "/patient_fim_evaluations/update/1.json?";

    //神经系统评价表
    public static final String Nse_getData_url = ipAndPort + "/app_normal/index.json?";
    //神经系统评价表 删除
    public static final String Nse_delete_url = ipAndPort + "/app_normal/destroy/aaa.json?";
    //神经系统评价表 新建
    public static final String Nse_create_url = ipAndPort + "/app_normal/create/1.json?";
    //神经系统评价表 更新
    public static final String Nse_update_url = ipAndPort + "/app_normal/update/aaa.json?";


}
