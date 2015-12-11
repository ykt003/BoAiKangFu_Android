package me.zhangls.rilintech.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 水中肢体功能
 * Created by YANG on 15/11/24.
 */
public class NormalTableModelManager4 {

    public static ArrayList<NormalTableModel> scaleList;
    public static NormalTableModel normalTableModel;
    public static ArrayList<String> list_ll;
    private static String TEXTVIEW = "Textview";
    private static String SPINNER = "Spinner";
    private static String RADIOGROUP = "Radiogroup";
    private static String MAXTEXT = "Maxtext";
    private static String EDITTEXT = "Edittext";

    public static ArrayList<NormalTableModel> getTableItemList() {
        scaleList = new ArrayList<>();

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("日期");
        normalTableModel.setType(TEXTVIEW);
        normalTableModel.setName("date_time");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("治疗时间");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("min");
        normalTableModel.setName("zl_time");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("水温");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("°C");
        normalTableModel.setName("water_temperature");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("治疗水深");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("m");
        normalTableModel.setName("d_of_warter");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("所用器材");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setName("equipment");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("运动训练");
        normalTableModel.setType(MAXTEXT);
        list_ll = new ArrayList<>();
        list_ll.add("运动控制训练");
        list_ll.add("肌力增强训练");
        list_ll.add("平衡及协调训练");
        list_ll.add("游泳训练");
        list_ll.add("水中步行训练");
        list_ll.add("牵张训练");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("movement_training");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("治疗作用");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("一般代谢功能");
        list_ll.add("一般任务和要求,未特指");
        list_ll.add("与卫生有关专业人员的个人态度");
        list_ll.add("与听和前庭功能相关的感觉");
        list_ll.add("与心血管和呼吸功能相关的感觉，如心率脱常、心悸、气短等感觉");
        list_ll.add("与时间有关的变化");
        list_ll.add("与泌尿功能相关的感觉");
        list_ll.add("与泌尿和生殖系统有关的结构,未特指");
        list_ll.add("与消化、代谢和内分泌系统有关的结构,未特指");
        list_ll.add("与消化系统相关的感觉");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("therapeutic_effect");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("执行者");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setName("executor");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("SOAP_记录");
        normalTableModel.setType(MAXTEXT);
        list_ll = new ArrayList<>();
        list_ll.add("主观：");
        list_ll.add("客观：");
        list_ll.add("评定：");
        list_ll.add("计划：");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("soap_record");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("备注");
        normalTableModel.setType(MAXTEXT);
        normalTableModel.setName("remark");
        scaleList.add(normalTableModel);

        return scaleList;

    }
}
