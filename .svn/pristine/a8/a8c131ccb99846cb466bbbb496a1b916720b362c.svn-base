package me.zhangls.rilintech.model;


import java.util.ArrayList;

/**
 * 康复评价会记录表所有item集合
 */
public class NormalTableModelManager10 {

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
        normalTableModel.setLeftText("评价次数");
        normalTableModel.setName("eval_time");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("评价说明");
        normalTableModel.setName("eval_desc");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("");
        list_ll.add("初期评定");
        list_ll.add("中期评定");
        list_ll.add("末期评定");
        normalTableModel.setDicText(list_ll);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("评价地点");
        normalTableModel.setName("eval_palce");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("评价时间");
        normalTableModel.setName("eval_date");
        normalTableModel.setType(TEXTVIEW);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("参加人员");
        normalTableModel.setName("join_pers");
        normalTableModel.setType(MAXTEXT);
        list_ll = new ArrayList<>();
        list_ll.add("主任医师:");
        list_ll.add("副主任医师:");
        list_ll.add("主管医师:");
        list_ll.add("主治医师:");
        list_ll.add("住院医师:");
        list_ll.add("康复护士:");
        list_ll.add("物理治疗师:");
        list_ll.add("作业治疗师:");
        list_ll.add("言语治疗师:");
        normalTableModel.setDicText(list_ll);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("患者病情及诊断");
        normalTableModel.setName("course_diag");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("护理相关问题 ");
        normalTableModel.setName("nurse_quest");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("物理治疗师意见 ");
        normalTableModel.setName("phy_sugg");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("作业治疗师意见 ");
        normalTableModel.setName("work_sugg");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("其他治疗师意见(言语、理疗、文体、心理) ");
        normalTableModel.setName("other_sugg");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("康复医师意见 ");
        normalTableModel.setName("recover_sugg");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("上级医师指导意见 ");
        normalTableModel.setName("doc_sugg");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);


        return  scaleList;


    }

}
