package me.zhangls.rilintech.model;

/**
 * Created by YANG on 15/12/2.
 */


import java.util.ArrayList;

/**
 * 出院总结
 */
public class NormalTableModelManager11 {

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
        normalTableModel.setLeftText("次数");
        normalTableModel.setName("cishu");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("治疗开始时间");
        normalTableModel.setName("start_time");
        normalTableModel.setType(TEXTVIEW);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("治疗结束时间");
        normalTableModel.setName("end_time");
        normalTableModel.setType(TEXTVIEW);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("治疗次数");
        normalTableModel.setName("times");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("初评得分");
        normalTableModel.setName("start_score");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("末评得分");
        normalTableModel.setName("end_score");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("评定统计分析");
        normalTableModel.setName("analysis");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("进步点 ");
        normalTableModel.setName("progress");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("不足处");
        normalTableModel.setName("weak");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("疗效总结");
        normalTableModel.setName("summarize");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("出院指导/家庭训练");
        normalTableModel.setName("guidance");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("备注");
        normalTableModel.setName("remark");
        normalTableModel.setType(MAXTEXT);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("总结时间");
        normalTableModel.setName("zj_time");
        normalTableModel.setType(TEXTVIEW);
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("总结者 ");
        normalTableModel.setName("zjze");
        normalTableModel.setType(EDITTEXT);
        scaleList.add(normalTableModel);


        return  scaleList;


    }

}
