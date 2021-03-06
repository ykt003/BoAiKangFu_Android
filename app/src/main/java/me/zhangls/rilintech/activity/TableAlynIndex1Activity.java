package me.zhangls.rilintech.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableAlynAdapter;
import me.zhangls.rilintech.manager.TableAlyn1Manager;
import me.zhangls.rilintech.model.TableAlynInfo1;
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/8/22.
 */

/**
 * Alyn 水中适应性评定类
 */
public class TableAlynIndex1Activity extends BaseActivity {

    List<String> list = new ArrayList();
    AlertDialog dialog = null;
    private Intent intent;
    /**
     * 评定日期
     */
    private TextView date;
    /**
     * 说明项
     */
    private String instruction;
    /**
     * 评定说明
     */
    private Spinner mSpinner;
    /**
     * 总分
     */
    private TextView scores;
    /**
     * 百分制得分
     */
    private TextView scores_t;
    /**
     * 展现视图
     */
    private ListView listView;
    /**
     * 题目集
     */
    private List<String> list_titles = new ArrayList<>();
    /**
     * 得分集
     */
    private List<String> list_scores = new ArrayList<>();
    private TableAlyn1Manager manager = null;
    private TableAlynInfo1 info = null;
    private TableAlynAdapter adapter = null;
    /**
     * 评定说明的选项
     */
    private String[] arr_instructions = new String[]{"初评", "中评1", "中评2", "中评3", "中评4", "中评5", "末评"};
    /**
     * spinner加载器
     */
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_alyn_index);

        initView();
        setData("初评", "admin");

    }

    /**
     * 赋值
     *
     * @param instructions
     * @param username
     */
    private void setData(final String instructions, String username) {
        if (queryData(instructions, username) == null || queryData(instructions, username).size() <= 0) {
            Toast.makeText(this, "无数据...", Toast.LENGTH_SHORT).show();
        } else {
            list_titles = getTitles();
            list_scores = queryData(instructions, username);
        }
        adapter = new TableAlynAdapter(this, list_titles, list_scores);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TableAlynIndex1Activity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                TextView delete = (TextView) view1.findViewById(R.id.delete);
                TextView add = (TextView) view1.findViewById(R.id.add);
                TextView edit = (TextView) view1.findViewById(R.id.edit);

                dialog = builder.show();
                dialog.getWindow().setContentView(view1);
                //删除
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        TableAlyn1Manager manager = new TableAlyn1Manager(TableAlynIndex1Activity.this);
                        manager.openDataBase();
                        manager.deleteOne("admin", instruction);
                        manager.closeDataBase();

                        date.setText("");
                        scores_t.setText("");
                        scores.setText("");
                        list_scores.clear();
                        list_titles.clear();
                        setData("初评", "admin");
                        for(int i = 0;i<arr_instructions.length;i++){
                            if(arr_instructions[i].equals("初评")){
                                mSpinner.setSelection(i);
                            }
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
                //新建
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        add(view);
                    }
                });
                //编辑
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        intent = new Intent(TableAlynIndex1Activity.this, TableAlynInfo1Activity.class);
                        intent.putExtra("isAdd", false);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 初始化数据集
     */
    private void initView() {

        date = (TextView) findViewById(R.id.date);
        listView = (ListView) findViewById(R.id.patient_infos_list_item);
        scores = (TextView) findViewById(R.id.scores_tv);
        scores_t = (TextView) findViewById(R.id.scores_t);

        //评定说明
        mSpinner = (Spinner) findViewById(R.id.instructions);

        for(String s:arr_instructions){
            list.add(s);
        }
        arrayAdapter = new SpinnerBaseAdapter(this,android.R.layout.simple_spinner_item,mSpinner,list);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr_instructions);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner_layout);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //instruction = arr_instructions[position];
                instruction = list.get(position);
                L.d(instruction);
                SharedPreferences sharedPreferences = getSharedPreferences("instructions", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("instruction", instruction);
                editor.commit();

                date.setText("");
                scores_t.setText("");
                scores.setText("");
                list_scores.clear();
                list_titles.clear();
                //setData(arr_instructions[position], "admin");
                setData(instruction, "admin");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner.setAdapter(arrayAdapter);

    }

    /**
     * 查数据
     *
     * @return
     */
    private List<String> queryData(String instructions, String username) {
        manager = new TableAlyn1Manager(getApplicationContext());
        manager.openDataBase();
        info = manager.queryOne(instructions, username);
        manager.closeDataBase();

        if (info == null) {

        } else {

            date.setText(info.getDate());
            scores.setText(info.getScores() + "");
            scores_t.setText(info.getScore_t() + "");

            list_scores = new ArrayList<>();
            list_scores.add(info.getAnswer1().substring(0, 1));
            list_scores.add(info.getAnswer2().substring(0, 1));
            list_scores.add(info.getAnswer3().substring(0, 1));
            list_scores.add(info.getAnswer4().substring(0, 1));
            list_scores.add(info.getAnswer5().substring(0, 1));
            list_scores.add(info.getAnswer6().substring(0, 1));
            list_scores.add(info.getAnswer7().substring(0, 1));
            list_scores.add(info.getAnswer8().substring(0, 1));
            list_scores.add(info.getAnswer9().substring(0, 1));
            list_scores.add(info.getAnswer10().substring(0, 1));
            list_scores.add(info.getAnswer11().substring(0, 1));
            list_scores.add(info.getAnswer12().substring(0, 1));
            list_scores.add(info.getAnswer13().substring(0, 1));
        }
        return list_scores;
    }

    /**
     * 获取题目集
     *
     * @return
     */
    private List<String> getTitles() {
        list_titles = new ArrayList<>();
        list_titles.add(getString(R.string.alyn1_title1));
        list_titles.add(getString(R.string.alyn1_title2));
        list_titles.add(getString(R.string.alyn1_title3));
        list_titles.add(getString(R.string.alyn1_title4));
        list_titles.add(getString(R.string.alyn1_title5));
        list_titles.add(getString(R.string.alyn1_title6));
        list_titles.add(getString(R.string.alyn1_title7));
        list_titles.add(getString(R.string.alyn1_title8));
        list_titles.add(getString(R.string.alyn1_title9));
        list_titles.add(getString(R.string.alyn1_title10));
        list_titles.add(getString(R.string.alyn1_title11));
        list_titles.add(getString(R.string.alyn1_title12));
        list_titles.add(getString(R.string.alyn1_title13));

        return list_titles;
    }


    @Override
    protected void onResume() {

        SharedPreferences sharedPreferences = getSharedPreferences("instructions", Context.MODE_PRIVATE);

        String instruction = sharedPreferences.getString("instruction","初评");

        list_scores.clear();
        list_titles.clear();
        setData(instruction, "admin");
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    /**
     * 添加按钮
     *
     * @param view
     */
    public void add(View view) {
        TableAlyn1Manager manager = new TableAlyn1Manager(this);
        manager.openDataBase();
        List<String>list = manager.getAllInstructions("admin");

        List<String>list_s = new ArrayList<>();
        for(String s:arr_instructions){
            list_s.add(s);
        }

        for(int i=0;i<list.size();i++){
            if(list_s.contains(list.get(i))){
                list_s.remove(list.get(i));
            }
        }

        if(list_s.size()>0) {

            intent = new Intent(this, TableAlynInfo1Activity.class);
            intent.putExtra("isAdd",true);
            startActivity(intent);
        }else{
            Toast.makeText(TableAlynIndex1Activity.this, "已满..", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回按钮
     *
     * @param view
     */
    public void back(View view) {
        this.finish();
    }

}
