package me.zhangls.rilintech.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.DataStatisticalActivity;
import me.zhangls.rilintech.activity.DischargeSummaryIndexActivity;
import me.zhangls.rilintech.activity.TableAlynIndex1Activity;
import me.zhangls.rilintech.activity.TableAlynIndex2Activity;
import me.zhangls.rilintech.activity.TableAshworthIndexActivity;
import me.zhangls.rilintech.activity.TableBarthelIndexActivity;
import me.zhangls.rilintech.activity.TableChildIndexActivity;
import me.zhangls.rilintech.activity.TableFimIndexActivity;
import me.zhangls.rilintech.activity.TableFmaIndexActivity;
import me.zhangls.rilintech.activity.TableGmfmIndexActivity;
import me.zhangls.rilintech.activity.TableListViewShowActivity;
import me.zhangls.rilintech.activity.TableMcGillIndexActivity;
import me.zhangls.rilintech.activity.TableMmtIndexActivity;
import me.zhangls.rilintech.activity.TableNervousSystemIndexActivity;
import me.zhangls.rilintech.activity.TableNormalChildIndexActivity;
import me.zhangls.rilintech.activity.TableRehabilitationGoalIndexActivity;
import me.zhangls.rilintech.activity.TableRomIndexActivity;
import me.zhangls.rilintech.activity.TableTreadmillTrainingInWaterIndexActivity;
import me.zhangls.rilintech.activity.TableTreatmentPlanIndexActivity;
import me.zhangls.rilintech.adapter.TableMainAdapter;
import me.zhangls.rilintech.adapter.TableMoreAdapter;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.model.TableFragmentListViewDate;
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 13-11-23.
 */
public class TableFragment extends Fragment {
    private int item_height;
    private int table_ll_height;
    private LinearLayout table_ll;
    private ListView mainlist;
    private ListView morelist;
    private List<Map<String, Object>> mainList;
    TableMainAdapter mainAdapter;
    TableMoreAdapter moreAdapter;
    TableFragmentListViewDate tableFragmentListViewDate;
    /**
     * 当前main_list的position
     */
    private int main_position;
    private Intent intent = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, null);

        tableFragmentListViewDate = new TableFragmentListViewDate(getActivity());

        initModle();
        initView(view);

        return view;
    }

    private void initView(View view) {
        /*table_ll = (LinearLayout) view.findViewById(R.id.ll_table);
        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(width,height);
        table_ll_height=view.getMeasuredHeight();*/

        Log.d("sssss", "height1=" + table_ll_height);

        mainlist = (ListView) view.findViewById(R.id.classify_mainlist);
        morelist = (ListView) view.findViewById(R.id.classify_morelist);
        mainAdapter = new TableMainAdapter(getActivity(), mainList);
        mainAdapter.setSelectItem(0);
        mainlist.setAdapter(mainAdapter);

        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main_position = position;
                //initAdapter(TableFragmentListViewDate.MORELISTTXT[position]);
                initAdapter(tableFragmentListViewDate.getList_more_text().get(position));
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
            }
        });
        mainlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 一定要设置这个属性，否则ListView不会刷新
        //initAdapter(TableFragmentListViewDate.MORELISTTXT[0]);
        initAdapter(tableFragmentListViewDate.getList_more_text().get(0));

        morelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moreAdapter.setSelectItem(position);
                moreAdapter.notifyDataSetChanged();
                String menu_lib_id = tableFragmentListViewDate.getList_menu_more_id().get(main_position).get(position);
                L.d("yy", "main_position===" + main_position + "--more_position==" + position + "---menu_lib_id=" + menu_lib_id);
                switch (menu_lib_id) {
                    case "95"://徒手肌力检查记录表(MMT)
                        intent = new Intent(getActivity(), TableMmtIndexActivity.class);
                        break;
                    case "38"://改良Ashworth肌张力评定记录表（MAT）
                        intent = new Intent(getActivity(), TableAshworthIndexActivity.class);
                        break;
                    case "92"://关节活动记录表（ROM）
                        intent = new Intent(getActivity(), TableRomIndexActivity.class);
                        break;
                    case "33"://Fugl-Meyer评定(Fugl-Meyer Assessment,FMA)
                        intent = new Intent(getActivity(), TableFmaIndexActivity.class);
                        break;
                    case "172"://粗大运动功能评估量表 (Gross Motor Function Measure,GMFM)
                        intent = new Intent(getActivity(), TableGmfmIndexActivity.class);
                        break;
//                    case "200"://Alyn水中适应性评定（WOTA1)
//                        intent = new Intent(getActivity(), TableFlowChart.class);
//                        break;
                    case "200"://Alyn水中适应性评定（WOTA1)
                        intent = new Intent(getActivity(), TableAlynIndex1Activity.class);
                        intent.putExtra("alyn", 1);
                        break;
                    case "171"://Alyn水中适应性评定（WOTA2)
                        intent = new Intent(getActivity(), TableAlynIndex2Activity.class);
                        intent.putExtra("alyn", 2);
                        break;
                    case "29"://Barthel指数评定（BI）
                        intent = new Intent(getActivity(), TableBarthelIndexActivity.class);
                        break;
                    case "35"://功能独立性评定量表(FIM)
                        intent = new Intent(getActivity(), TableFimIndexActivity.class);
                        break;
                    case "41"://MMSE
                        intent = new Intent(getActivity(), TableChildIndexActivity.class);
                        break;
                    case "156"://目标设定
                        intent = new Intent(getActivity(), TableRehabilitationGoalIndexActivity.class);
                        break;
                    case "160"://康复计划/治疗计划
                        intent = new Intent(getActivity(), TableTreatmentPlanIndexActivity.class);
                        break;
                    case "163"://治疗记录/水中平板步行训练
                        intent = new Intent(getActivity(), TableTreadmillTrainingInWaterIndexActivity.class);
                        break;
                    case "181"://RLA
                        Toast.makeText(MyApplication.getInstance(), "量表进行中", Toast.LENGTH_SHORT).show();
                        break;
                    case "82"://McGill
                        intent = new Intent(getActivity(), TableMcGillIndexActivity.class);
                        break;
                    case "254"://西部失语
                        Toast.makeText(MyApplication.getInstance(), "量表进行中", Toast.LENGTH_SHORT).show();
                        break;
                    case "255"://汉语言
                        Toast.makeText(MyApplication.getInstance(), "量表进行中", Toast.LENGTH_SHORT).show();
                        break;
                    case "sd37"://出院总结
                        intent = new Intent(getActivity(), DischargeSummaryIndexActivity.class);
                        break;
                    case "208"://康复评价会记录表
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "214"://颅神经检查
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "219"://肌肉维度评价表
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "206"://手功能分级
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "162"://水中肢体功能
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "164"://电动浴缸
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "167"://半身浴槽
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "186"://气泡涡流浴
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "165"://步行浴
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "166"://哈巴氏槽
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "213"://神经系统评价表
                        intent = new Intent(getActivity(), TableNervousSystemIndexActivity.class);
                        break;
                    case "195"://功能独立性评定统计
                        intent = new Intent(getActivity(), DataStatisticalActivity.class);
                        intent.putExtra("selected",0);
                        break;
                    case "197"://水中平板运动治疗记录统计
                        intent = new Intent(getActivity(), DataStatisticalActivity.class);
                        intent.putExtra("selected",1);
                        break;
                    case "153"://阶段小结
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
                    case "154"://出院总结
                        intent = new Intent(getActivity(), TableNormalChildIndexActivity.class);
                        break;
//                    case "246":
//                        intent = new Intent(getActivity(), TableChildIndexActivity.class);
//                        break;
//                    case "243":
//                        intent = new Intent(getActivity(), TableChildIndexActivity.class);
//                        break;
//                    case "242":
//                        intent = new Intent(getActivity(), TableChildIndexActivity.class);
//                        break;
                    default:
                        intent = new Intent(getActivity(), TableChildIndexActivity.class);
                        break;
                }
                /*if (main_position == 0) {//临床信息
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }

                } else if (main_position == 1) {//临床评价
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                } else if (main_position == 2) {//康复评定
                    switch (position) {
                        case 0://徒手肌力检查记录表(MMT)
                            intent = new Intent(getActivity(), TableMmtIndexActivity.class);
                            break;
                        case 1://改良Ashworth肌张力评定记录表（MAT）
                            intent = new Intent(getActivity(), TableAshworthIndexActivity.class);
                            break;
                        case 2://关节活动记录表（ROM）
                            intent = new Intent(getActivity(), TableRomIndexActivity.class);
                            break;
                        case 3://Fugl-Meyer评定(Fugl-Meyer Assessment,FMA)
                            intent = new Intent(getActivity(), TableFmaIndexActivity.class);
                            break;
                        case 4://粗大运动功能评估量表 (Gross Motor Function Measure,GMFM)
                            intent = new Intent(getActivity(), TableGmfmIndexActivity.class);
                            break;
                        case 6://Alyn水中适应性评定（WOTA1)
                            intent = new Intent(getActivity(), TableAlynIndex1Activity.class);
                            intent.putExtra("alyn", 1);
                            break;
                        case 7://Alyn水中适应性评定（WOTA2)
                            intent = new Intent(getActivity(), TableAlynIndex2Activity.class);
                            intent.putExtra("alyn", 2);
                            break;
                        case 15://Barthel指数评定（BI）
                            intent = new Intent(getActivity(), TableBarthelIndexActivity.class);
                            break;
                        case 16://功能独立性评定量表(FIM)
                            intent = new Intent(getActivity(), TableFimIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                } else if (main_position == 3) {//目标设定
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableRehabilitationGoalIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                } else if (main_position == 4) {//治疗项目
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                } else if (main_position == 5) {//康复计划
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableTreatmentPlanIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                } else if (main_position == 6) {//治疗纪录
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                        case 1:
                            intent = new Intent(getActivity(), TableTreadmillTrainingInWaterIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                } else if (main_position == 7) {//回顾总结
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                        case 5:
                            intent = new Intent(getActivity(), DischargeSummaryIndexActivity.class);
                            break;
                        default:
                            intent = new Intent(getActivity(), TableIndexActivity.class);
                            break;
                    }
                }*/
                if (intent != null) {
                    intent.putExtra("menu_lib_id", menu_lib_id);
                    intent.putExtra("main_position", main_position);
                    intent.putExtra("position", position);
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    /* private void initAdapter(String[] array) {
         moreAdapter = new TableMoreAdapter(getActivity(), array);
         morelist.setAdapter(moreAdapter);
         moreAdapter.notifyDataSetChanged();
     }*/
    private void initAdapter(List<String> array) {
        moreAdapter = new TableMoreAdapter(getActivity(), array);
        morelist.setAdapter(moreAdapter);
        moreAdapter.notifyDataSetChanged();

    }

    private void initModle() {
        mainList = new ArrayList<>();
        //for (int i = 0; i < TableFragmentListViewDate.LISTVIEWIMG.length; i++) {
        for (int i = 0; i < tableFragmentListViewDate.getList_image_normal().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            //map.put("img", TableFragmentListViewDate.LISTVIEWIMG[i]);
            map.put("img", tableFragmentListViewDate.getList_image_normal().get(i));
            //map.put("txt", TableFragmentListViewDate.LISTVIEWTXT[i]);
            map.put("txt", tableFragmentListViewDate.getList_main_text().get(i));
            mainList.add(map);
        }
    }
}
