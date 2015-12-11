package me.zhangls.rilintech.model;

import java.util.ArrayList;

import me.zhangls.rilintech.utils.L;

/**
 * 肌肉维度评价表
 * Created by YANG on 15/11/24.
 */
public class NormalTableModelManager2 {
    public static ArrayList<NormalTableModel> scaleList;
    public static NormalTableModel normalTableModel;
    public ArrayList<String> list_ll;
    private static String TEXTVIEW = "Textview";
    private static String SPINNER = "Spinner";
    private static String RADIOGROUP = "Radiogroup";
    private static String MAXTEXT = "Maxtext";
    private static String EDITTEXT = "Edittext";

    public static ArrayList<NormalTableModel> getTableItemList() {
        scaleList = new ArrayList<>();

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("上臂(鹰嘴突上)");
        normalTableModel.setName("arm_up");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("cm");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("上臂(鹰嘴突下)");
        normalTableModel.setName("arm_down");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("cm");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("大腿(髌上)");
        normalTableModel.setName("leg_up");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("cm");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("小腿(髌下)");
        normalTableModel.setName("leg_down");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setRightText("cm");
        scaleList.add(normalTableModel);

        L.d("yy", "list_info.size(900909)===" + scaleList.size());

        return scaleList;

    }
}
