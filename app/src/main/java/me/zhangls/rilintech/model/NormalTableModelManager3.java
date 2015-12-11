package me.zhangls.rilintech.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 手功能分级
 * Created by YANG on 15/11/24.
 */
public class NormalTableModelManager3 {

    public static ArrayList<NormalTableModel> scaleList;
    public static NormalTableModel normalTableModel;
    public  static ArrayList<String>list_ll;
    private static String TEXTVIEW = "Textview";
    private static String SPINNER = "Spinner";
    private static String RADIOGROUP = "Radiogroup";
    private static String MAXTEXT = "Maxtext";
    private static String EDITTEXT = "Edittext";
    public static ArrayList<NormalTableModel> getTableItemList(){
        scaleList = new ArrayList<>();

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("动作一：患手固定纸张,健手使用剪刀");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("");
        list_ll.add("能够完成");
        list_ll.add("不能完成");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("motion_one");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("动作二:患手拿钱包,健手使用钱包 ");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("");
        list_ll.add("能够完成");
        list_ll.add("不能完成");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("motion_two");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("动作三:用患手悬空撑伞 10秒钟以上 ");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("");
        list_ll.add("能够完成");
        list_ll.add("不能完成");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("motion_three");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("用患手剪指甲 ");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("");
        list_ll.add("能够完成");
        list_ll.add("不能完成");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("motion_four");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("用患手系纽扣 ");
        normalTableModel.setType(SPINNER);
        list_ll = new ArrayList<>();
        list_ll.add("");
        list_ll.add("能够完成");
        list_ll.add("不能完成");
        normalTableModel.setDicText(list_ll);
        normalTableModel.setName("motion_five");
        scaleList.add(normalTableModel);


        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("手功能分级");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setName("grade");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("记录时间");
        normalTableModel.setType(TEXTVIEW);
        normalTableModel.setName("record_time");
        scaleList.add(normalTableModel);

        normalTableModel = new NormalTableModel();
        normalTableModel.setLeftText("记录者");
        normalTableModel.setType(EDITTEXT);
        normalTableModel.setName("record_man");
        scaleList.add(normalTableModel);
        return scaleList;
    }
}
