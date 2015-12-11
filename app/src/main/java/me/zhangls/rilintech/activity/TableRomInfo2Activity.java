package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableRom2Adapter;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableRomInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/23.
 */
public class TableRomInfo2Activity extends BaseActivity implements View.OnClickListener {

    //评定说明当前选中的值
    private String spinner_value;
    //spinner加载器
    private ArrayAdapter<String> adapter;
    //评定说明的选项
    private ArrayList<String> list_instructions;
    //评定说明int
    private Map<String, Integer> instructions_map;
    //去除已经存在的评定说明后的spinner数据
    private List<String> list_instructions_new = new ArrayList<>();
    //评定说明
    private Spinner spinner_instructions;
    //时间选择器
    private TimePopupWindow pwTime;
    //正在编辑的对象
    private TableRomInfo info = null;
    //是否新建
    private Boolean isAdd = true;
    //返回键
    private ImageView key_back;
    //保存键
    private ImageView key_save;
    //评定日期
    private TextView date;
    //缓冲球
    private GoogleProgressBar progressBar;
    //当前服务器已经存在的评定集合
    private ArrayList<String> list_instructions_cloud = new ArrayList<>();
    //handler
    private Handler handler;
    //新建响应码
    private int create_code = 0;
    //更新响应码
    private int update_code = 0;
    //拼接参数
    private String param;
    //当前登录用户
    private int user_auth_id;
    //当前选中的患者
    private int patient_info_id;
    //ListView
    private ListView listView;
    //adapter
    private TableRom2Adapter adapter_listView;
    //部位
    private List<String> list_parts = new ArrayList<>();
    //动作
    private ArrayList<String> list_actions = new ArrayList<>();
    //左侧主动分数
    private ArrayList<String> list_left_init_scores = new ArrayList<>();
    //左侧被动分数
    private ArrayList<String> list_left_passive_scores = new ArrayList<>();
    //右侧主动分数
    private ArrayList<String> list_right_init_scores = new ArrayList<>();
    //右侧被动分数
    private ArrayList<String> list_right_passive_scores = new ArrayList<>();
    //adapter的返回值map
    private HashMap<Integer, String> map_init_lefts;
    private HashMap<Integer, String> map_passive_lefts;
    private HashMap<Integer, String> map_init_rights;
    private HashMap<Integer, String> map_passive_rights;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_rom_info2);

        getMyIntent();

        queryInstructions();

        getSharedPreferencesData();

        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }
        };

        initView();
    }

    /**
     * 获取当前登录用户和当前患者的ID
     */
    private void getSharedPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
        patient_info_id = sharedPreferences.getInt("id", -1);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
    }

    /**
     * 去除掉已经有的评定说明
     */
    public void queryInstructions() {
        list_instructions = new ArrayList<>();
        list_instructions.add(getString(R.string.evaluation0));
        list_instructions.add(getString(R.string.evaluation1));
        list_instructions.add(getString(R.string.evaluation2));
        list_instructions.add(getString(R.string.evaluation3));
        list_instructions.add(getString(R.string.evaluation4));
        list_instructions.add(getString(R.string.evaluation5));
        list_instructions.add(getString(R.string.evaluation6));

        list_instructions_new.addAll(list_instructions);
        for (int i = 0; i < list_instructions_cloud.size(); i++) {
            if (list_instructions_new.contains(list_instructions_cloud.get(i))) {
                list_instructions_new.remove(list_instructions_cloud.get(i));
            }
        }
        if (isAdd == false) {//如果是编辑界面，需要把当前的评定说明加回去
            list_instructions_new.add(0, info.getInstructions());
        }
    }

    /**
     * 获取到从indexActivity传过来的数据
     */
    private void getMyIntent() {

        isAdd = getIntent().getBooleanExtra("isAdd", true);
        if (isAdd) {
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");

        } else {
            info = getIntent().getParcelableExtra("info");
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");
        }
    }

    private void initView() {

        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

        key_back = (ImageView) findViewById(R.id.tittle_back);
        key_back.setOnClickListener(this);
        key_save = (ImageView) findViewById(R.id.patient_save);
        key_save.setOnClickListener(this);
        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        progressBar.setVisibility(View.GONE);

        date = (TextView) findViewById(R.id.date);
        date.setOnClickListener(this);
        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date d) {
                date.setText(MyApplication.getTime(d));
            }
        });
        pwTime.setCyclic(true);
        //评定说明
        spinner_instructions = (Spinner) findViewById(R.id.spinner_edit);
        adapter = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, spinner_instructions, list_instructions_new);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        spinner_instructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = list_instructions_new.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_instructions.setAdapter(adapter);


        listView = (ListView) findViewById(R.id.rom_list_item);


        setData();
    }

    /**
     * 给所有控件赋值
     */
    private void setData() {

        if (info != null) {

            date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));

            for (int i = 0; i < list_instructions_new.size(); i++) {
                if ((list_instructions_new.get(i)).equals(info.getInstructions())) {
                    spinner_instructions.setSelection(i);
                }
            }
        }

        list_actions.add(getString(R.string.rom_action1));
        list_actions.add(getString(R.string.rom_action2));
        list_actions.add(getString(R.string.rom_action3));
        list_actions.add(getString(R.string.rom_action4));
        list_actions.add(getString(R.string.rom_action5));
        list_actions.add(getString(R.string.rom_action6));
        list_actions.add(getString(R.string.rom_action7));
        list_actions.add(getString(R.string.rom_action8));
        list_actions.add(getString(R.string.rom_action9));
        list_actions.add(getString(R.string.rom_action10));
        list_actions.add(getString(R.string.rom_action11));
        list_actions.add(getString(R.string.rom_action12));
        list_actions.add(getString(R.string.rom_action13));
        list_actions.add(getString(R.string.rom_action14));
        list_actions.add(getString(R.string.rom_action15));
        list_actions.add(getString(R.string.rom_action16));
        list_actions.add(getString(R.string.rom_action17));
        list_actions.add(getString(R.string.rom_action18));
        list_actions.add(getString(R.string.rom_action19));
        list_actions.add(getString(R.string.rom_action20));
        list_actions.add(getString(R.string.rom_action21));
        list_actions.add(getString(R.string.rom_action22));
        list_actions.add(getString(R.string.rom_action23));
        list_actions.add(getString(R.string.rom_action24));
        list_actions.add(getString(R.string.rom_action25));
        list_actions.add(getString(R.string.rom_action26));
        list_actions.add(getString(R.string.rom_action27));
        list_actions.add(getString(R.string.rom_action28));
        list_actions.add(getString(R.string.rom_action29));
        list_actions.add(getString(R.string.rom_action30));
        list_actions.add(getString(R.string.rom_action31));
        list_actions.add(getString(R.string.rom_action32));
        list_actions.add(getString(R.string.rom_action33));
        list_actions.add(getString(R.string.rom_action34));
        list_actions.add(getString(R.string.rom_action35));
        list_actions.add(getString(R.string.rom_action36));
        list_actions.add(getString(R.string.rom_action37));
        list_actions.add(getString(R.string.rom_action38));
        list_actions.add(getString(R.string.rom_action39));
        list_actions.add(getString(R.string.rom_action40));
        list_actions.add(getString(R.string.rom_action41));
        list_actions.add(getString(R.string.rom_action42));
        list_actions.add(getString(R.string.rom_action43));
        list_actions.add(getString(R.string.rom_action44));
        list_actions.add(getString(R.string.rom_action45));
        list_actions.add(getString(R.string.rom_action46));
        list_actions.add(getString(R.string.rom_action47));
        list_actions.add(getString(R.string.rom_action48));
        list_actions.add(getString(R.string.rom_action49));
        list_actions.add(getString(R.string.rom_action50));
        list_actions.add(getString(R.string.rom_action51));
        list_actions.add(getString(R.string.rom_action52));
        list_actions.add(getString(R.string.rom_action53));
        list_actions.add(getString(R.string.rom_action54));
        list_actions.add(getString(R.string.rom_action55));
        list_actions.add(getString(R.string.rom_action56));
        list_actions.add(getString(R.string.rom_action57));
        list_actions.add(getString(R.string.rom_action58));
        list_actions.add(getString(R.string.rom_action59));
        list_actions.add(getString(R.string.rom_action60));
        list_actions.add(getString(R.string.rom_action61));
        list_actions.add(getString(R.string.rom_action62));
        list_actions.add(getString(R.string.rom_action63));
        list_actions.add(getString(R.string.rom_action64));
        list_actions.add(getString(R.string.rom_action65));
        list_actions.add(getString(R.string.rom_action66));
        list_actions.add(getString(R.string.rom_action67));
        list_actions.add(getString(R.string.rom_action68));
        list_actions.add(getString(R.string.rom_action69));
        list_actions.add(getString(R.string.rom_action70));

        for (int i = 0; i < 9; i++) {
            list_parts.add(getString(R.string.rom_parts_1));
        }
        for (int i = 0; i < 3; i++) {
            list_parts.add(getString(R.string.rom_parts_2));
        }
        for (int i = 0; i < 4; i++) {
            list_parts.add(getString(R.string.rom_parts_3));
        }
        for (int i = 0; i < 6; i++) {
            list_parts.add(getString(R.string.rom_parts_4));
        }
        for (int i = 0; i < 6; i++) {
            list_parts.add(getString(R.string.rom_parts_5));
        }
        for (int i = 0; i < 6; i++) {
            list_parts.add(getString(R.string.rom_parts_6));
        }
        for (int i = 0; i < 6; i++) {
            list_parts.add(getString(R.string.rom_parts_7));
        }
        for (int i = 0; i < 6; i++) {
            list_parts.add(getString(R.string.rom_parts_8));
        }
        for (int i = 0; i < 6; i++) {
            list_parts.add(getString(R.string.rom_parts_9));
        }
        for (int i = 0; i < 2; i++) {
            list_parts.add(getString(R.string.rom_parts_10));
        }
        for (int i = 0; i < 4; i++) {
            list_parts.add(getString(R.string.rom_parts_11));
        }
        for (int i = 0; i < 4; i++) {
            list_parts.add(getString(R.string.rom_parts_12));
        }
        for (int i = 0; i < 4; i++) {
            list_parts.add(getString(R.string.rom_parts_13));
        }
        for (int i = 0; i < 4; i++) {
            list_parts.add(getString(R.string.rom_parts_14));
        }
        if (info != null) {
            for (String s1 : info.getLeft_init_score().split(",")) {
                list_left_init_scores.add(s1);
            }
            for (String s2 : info.getLeft_passive_score().split(",")) {
                list_left_passive_scores.add(s2);
            }
            for (String s3 : info.getRight_init_score().split(",")) {
                list_right_init_scores.add(s3);
            }
            for (String s4 : info.getRight_passive_score().split(",")) {
                list_right_passive_scores.add(s4);
            }

        }

        adapter_listView = new TableRom2Adapter(this, list_parts, list_actions, list_left_init_scores,
                list_left_passive_scores, list_right_init_scores, list_right_passive_scores);
        listView.setAdapter(adapter_listView);
        //adapter_listView.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tittle_back:
                this.finish();
                break;
            case R.id.patient_save:
                if (dataIsEmpty()) {

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    map_init_lefts = TableRom2Adapter.getMap().get(0);
                    map_passive_lefts = TableRom2Adapter.getMap().get(1);
                    map_init_rights = TableRom2Adapter.getMap().get(2);
                    map_passive_rights = TableRom2Adapter.getMap().get(3);

                    param = "patient_info_id=" + patient_info_id
                            + "&user_auth_id=" + user_auth_id
                            + "&menu_id=" + 92
                            + "&evaluation[evaluation_time]=" + date.getText()
                            + "&evaluation[evaluation_time_note]=" + instructions_map.get(spinner_value)
                            + "&c=" + 70


                            + "&left_zd0=" + isNull(TableRom2Adapter.getMap().get(0).get(0))
                            + "&left_zd1=" + isNull(TableRom2Adapter.getMap().get(0).get(1))
                            + "&left_zd2=" + isNull(TableRom2Adapter.getMap().get(0).get(2))
                            + "&left_zd3=" + isNull(TableRom2Adapter.getMap().get(0).get(3))
                            + "&left_zd4=" + isNull(TableRom2Adapter.getMap().get(0).get(4))
                            + "&left_zd5=" + isNull(TableRom2Adapter.getMap().get(0).get(5))
                            + "&left_zd6=" + isNull(TableRom2Adapter.getMap().get(0).get(6))
                            + "&left_zd7=" + isNull(TableRom2Adapter.getMap().get(0).get(7))
                            + "&left_zd8=" + isNull(TableRom2Adapter.getMap().get(0).get(8))
                            + "&left_zd9=" + isNull(TableRom2Adapter.getMap().get(0).get(9))
                            + "&left_zd10=" + isNull(TableRom2Adapter.getMap().get(0).get(10))
                            + "&left_zd11=" + isNull(TableRom2Adapter.getMap().get(0).get(11))
                            + "&left_zd12=" + isNull(TableRom2Adapter.getMap().get(0).get(12))
                            + "&left_zd13=" + isNull(TableRom2Adapter.getMap().get(0).get(13))
                            + "&left_zd14=" + isNull(TableRom2Adapter.getMap().get(0).get(14))
                            + "&left_zd15=" + isNull(TableRom2Adapter.getMap().get(0).get(15))
                            + "&left_zd16=" + isNull(TableRom2Adapter.getMap().get(0).get(16))
                            + "&left_zd17=" + isNull(TableRom2Adapter.getMap().get(0).get(17))
                            + "&left_zd18=" + isNull(TableRom2Adapter.getMap().get(0).get(18))
                            + "&left_zd19=" + isNull(TableRom2Adapter.getMap().get(0).get(19))
                            + "&left_zd20=" + isNull(TableRom2Adapter.getMap().get(0).get(20))
                            + "&left_zd21=" + isNull(TableRom2Adapter.getMap().get(0).get(21))
                            + "&left_zd22=" + isNull(TableRom2Adapter.getMap().get(0).get(22))
                            + "&left_zd23=" + isNull(TableRom2Adapter.getMap().get(0).get(23))
                            + "&left_zd24=" + isNull(TableRom2Adapter.getMap().get(0).get(24))
                            + "&left_zd25=" + isNull(TableRom2Adapter.getMap().get(0).get(25))
                            + "&left_zd26=" + isNull(TableRom2Adapter.getMap().get(0).get(26))
                            + "&left_zd27=" + isNull(TableRom2Adapter.getMap().get(0).get(27))
                            + "&left_zd28=" + isNull(TableRom2Adapter.getMap().get(0).get(28))
                            + "&left_zd29=" + isNull(TableRom2Adapter.getMap().get(0).get(29))
                            + "&left_zd30=" + isNull(TableRom2Adapter.getMap().get(0).get(30))
                            + "&left_zd31=" + isNull(TableRom2Adapter.getMap().get(0).get(31))
                            + "&left_zd32=" + isNull(TableRom2Adapter.getMap().get(0).get(32))
                            + "&left_zd33=" + isNull(TableRom2Adapter.getMap().get(0).get(33))
                            + "&left_zd34=" + isNull(TableRom2Adapter.getMap().get(0).get(34))
                            + "&left_zd35=" + isNull(TableRom2Adapter.getMap().get(0).get(35))
                            + "&left_zd36=" + isNull(TableRom2Adapter.getMap().get(0).get(36))
                            + "&left_zd37=" + isNull(TableRom2Adapter.getMap().get(0).get(37))
                            + "&left_zd38=" + isNull(TableRom2Adapter.getMap().get(0).get(38))
                            + "&left_zd39=" + isNull(TableRom2Adapter.getMap().get(0).get(39))
                            + "&left_zd40=" + isNull(TableRom2Adapter.getMap().get(0).get(40))
                            + "&left_zd41=" + isNull(TableRom2Adapter.getMap().get(0).get(41))
                            + "&left_zd42=" + isNull(TableRom2Adapter.getMap().get(0).get(42))
                            + "&left_zd43=" + isNull(TableRom2Adapter.getMap().get(0).get(43))
                            + "&left_zd44=" + isNull(TableRom2Adapter.getMap().get(0).get(44))
                            + "&left_zd45=" + isNull(TableRom2Adapter.getMap().get(0).get(45))
                            + "&left_zd46=" + isNull(TableRom2Adapter.getMap().get(0).get(46))
                            + "&left_zd47=" + isNull(TableRom2Adapter.getMap().get(0).get(47))
                            + "&left_zd48=" + isNull(TableRom2Adapter.getMap().get(0).get(48))
                            + "&left_zd49=" + isNull(TableRom2Adapter.getMap().get(0).get(49))
                            + "&left_zd50=" + isNull(TableRom2Adapter.getMap().get(0).get(50))
                            + "&left_zd51=" + isNull(TableRom2Adapter.getMap().get(0).get(51))
                            + "&left_zd52=" + isNull(TableRom2Adapter.getMap().get(0).get(52))
                            + "&left_zd53=" + isNull(TableRom2Adapter.getMap().get(0).get(53))
                            + "&left_zd54=" + isNull(TableRom2Adapter.getMap().get(0).get(54))
                            + "&left_zd55=" + isNull(TableRom2Adapter.getMap().get(0).get(55))
                            + "&left_zd56=" + isNull(TableRom2Adapter.getMap().get(0).get(56))
                            + "&left_zd57=" + isNull(TableRom2Adapter.getMap().get(0).get(57))
                            + "&left_zd58=" + isNull(TableRom2Adapter.getMap().get(0).get(58))
                            + "&left_zd59=" + isNull(TableRom2Adapter.getMap().get(0).get(59))
                            + "&left_zd60=" + isNull(TableRom2Adapter.getMap().get(0).get(60))
                            + "&left_zd61=" + isNull(TableRom2Adapter.getMap().get(0).get(61))
                            + "&left_zd62=" + isNull(TableRom2Adapter.getMap().get(0).get(62))
                            + "&left_zd63=" + isNull(TableRom2Adapter.getMap().get(0).get(63))
                            + "&left_zd64=" + isNull(TableRom2Adapter.getMap().get(0).get(64))
                            + "&left_zd65=" + isNull(TableRom2Adapter.getMap().get(0).get(65))
                            + "&left_zd66=" + isNull(TableRom2Adapter.getMap().get(0).get(66))
                            + "&left_zd67=" + isNull(TableRom2Adapter.getMap().get(0).get(67))
                            + "&left_zd68=" + isNull(TableRom2Adapter.getMap().get(0).get(68))
                            + "&left_zd69=" + isNull(TableRom2Adapter.getMap().get(0).get(69))


                            + "&left_bd0=" + isNull(TableRom2Adapter.getMap().get(1).get(0))
                            + "&left_bd1=" + isNull(TableRom2Adapter.getMap().get(1).get(1))
                            + "&left_bd2=" + isNull(TableRom2Adapter.getMap().get(1).get(2))
                            + "&left_bd3=" + isNull(TableRom2Adapter.getMap().get(1).get(3))
                            + "&left_bd4=" + isNull(TableRom2Adapter.getMap().get(1).get(4))
                            + "&left_bd5=" + isNull(TableRom2Adapter.getMap().get(1).get(5))
                            + "&left_bd6=" + isNull(TableRom2Adapter.getMap().get(1).get(6))
                            + "&left_bd7=" + isNull(TableRom2Adapter.getMap().get(1).get(7))
                            + "&left_bd8=" + isNull(TableRom2Adapter.getMap().get(1).get(8))
                            + "&left_bd9=" + isNull(TableRom2Adapter.getMap().get(1).get(9))
                            + "&left_bd10=" + isNull(TableRom2Adapter.getMap().get(1).get(10))
                            + "&left_bd11=" + isNull(TableRom2Adapter.getMap().get(1).get(11))
                            + "&left_bd12=" + isNull(TableRom2Adapter.getMap().get(1).get(12))
                            + "&left_bd13=" + isNull(TableRom2Adapter.getMap().get(1).get(13))
                            + "&left_bd14=" + isNull(TableRom2Adapter.getMap().get(1).get(14))
                            + "&left_bd15=" + isNull(TableRom2Adapter.getMap().get(1).get(15))
                            + "&left_bd16=" + isNull(TableRom2Adapter.getMap().get(1).get(16))
                            + "&left_bd17=" + isNull(TableRom2Adapter.getMap().get(1).get(17))
                            + "&left_bd18=" + isNull(TableRom2Adapter.getMap().get(1).get(18))
                            + "&left_bd19=" + isNull(TableRom2Adapter.getMap().get(1).get(19))
                            + "&left_bd20=" + isNull(TableRom2Adapter.getMap().get(1).get(20))
                            + "&left_bd21=" + isNull(TableRom2Adapter.getMap().get(1).get(21))
                            + "&left_bd22=" + isNull(TableRom2Adapter.getMap().get(1).get(22))
                            + "&left_bd23=" + isNull(TableRom2Adapter.getMap().get(1).get(23))
                            + "&left_bd24=" + isNull(TableRom2Adapter.getMap().get(1).get(24))
                            + "&left_bd25=" + isNull(TableRom2Adapter.getMap().get(1).get(25))
                            + "&left_bd26=" + isNull(TableRom2Adapter.getMap().get(1).get(26))
                            + "&left_bd27=" + isNull(TableRom2Adapter.getMap().get(1).get(27))
                            + "&left_bd28=" + isNull(TableRom2Adapter.getMap().get(1).get(28))
                            + "&left_bd29=" + isNull(TableRom2Adapter.getMap().get(1).get(29))
                            + "&left_bd30=" + isNull(TableRom2Adapter.getMap().get(1).get(30))
                            + "&left_bd31=" + isNull(TableRom2Adapter.getMap().get(1).get(31))
                            + "&left_bd32=" + isNull(TableRom2Adapter.getMap().get(1).get(32))
                            + "&left_bd33=" + isNull(TableRom2Adapter.getMap().get(1).get(33))
                            + "&left_bd34=" + isNull(TableRom2Adapter.getMap().get(1).get(34))
                            + "&left_bd35=" + isNull(TableRom2Adapter.getMap().get(1).get(35))
                            + "&left_bd36=" + isNull(TableRom2Adapter.getMap().get(1).get(36))
                            + "&left_bd37=" + isNull(TableRom2Adapter.getMap().get(1).get(37))
                            + "&left_bd38=" + isNull(TableRom2Adapter.getMap().get(1).get(38))
                            + "&left_bd39=" + isNull(TableRom2Adapter.getMap().get(1).get(39))
                            + "&left_bd40=" + isNull(TableRom2Adapter.getMap().get(1).get(40))
                            + "&left_bd41=" + isNull(TableRom2Adapter.getMap().get(1).get(41))
                            + "&left_bd42=" + isNull(TableRom2Adapter.getMap().get(1).get(42))
                            + "&left_bd43=" + isNull(TableRom2Adapter.getMap().get(1).get(43))
                            + "&left_bd44=" + isNull(TableRom2Adapter.getMap().get(1).get(44))
                            + "&left_bd45=" + isNull(TableRom2Adapter.getMap().get(1).get(45))
                            + "&left_bd46=" + isNull(TableRom2Adapter.getMap().get(1).get(46))
                            + "&left_bd47=" + isNull(TableRom2Adapter.getMap().get(1).get(47))
                            + "&left_bd48=" + isNull(TableRom2Adapter.getMap().get(1).get(48))
                            + "&left_bd49=" + isNull(TableRom2Adapter.getMap().get(1).get(49))
                            + "&left_bd50=" + isNull(TableRom2Adapter.getMap().get(1).get(50))
                            + "&left_bd51=" + isNull(TableRom2Adapter.getMap().get(1).get(51))
                            + "&left_bd52=" + isNull(TableRom2Adapter.getMap().get(1).get(52))
                            + "&left_bd53=" + isNull(TableRom2Adapter.getMap().get(1).get(53))
                            + "&left_bd54=" + isNull(TableRom2Adapter.getMap().get(1).get(54))
                            + "&left_bd55=" + isNull(TableRom2Adapter.getMap().get(1).get(55))
                            + "&left_bd56=" + isNull(TableRom2Adapter.getMap().get(1).get(56))
                            + "&left_bd57=" + isNull(TableRom2Adapter.getMap().get(1).get(57))
                            + "&left_bd58=" + isNull(TableRom2Adapter.getMap().get(1).get(58))
                            + "&left_bd59=" + isNull(TableRom2Adapter.getMap().get(1).get(59))
                            + "&left_bd60=" + isNull(TableRom2Adapter.getMap().get(1).get(60))
                            + "&left_bd61=" + isNull(TableRom2Adapter.getMap().get(1).get(61))
                            + "&left_bd62=" + isNull(TableRom2Adapter.getMap().get(1).get(62))
                            + "&left_bd63=" + isNull(TableRom2Adapter.getMap().get(1).get(63))
                            + "&left_bd64=" + isNull(TableRom2Adapter.getMap().get(1).get(64))
                            + "&left_bd65=" + isNull(TableRom2Adapter.getMap().get(1).get(65))
                            + "&left_bd66=" + isNull(TableRom2Adapter.getMap().get(1).get(66))
                            + "&left_bd67=" + isNull(TableRom2Adapter.getMap().get(1).get(67))
                            + "&left_bd68=" + isNull(TableRom2Adapter.getMap().get(1).get(68))
                            + "&left_bd69=" + isNull(TableRom2Adapter.getMap().get(1).get(69))


                            + "&right_zd0=" + isNull(TableRom2Adapter.getMap().get(2).get(0))
                            + "&right_zd1=" + isNull(TableRom2Adapter.getMap().get(2).get(1))
                            + "&right_zd2=" + isNull(TableRom2Adapter.getMap().get(2).get(2))
                            + "&right_zd3=" + isNull(TableRom2Adapter.getMap().get(2).get(3))
                            + "&right_zd4=" + isNull(TableRom2Adapter.getMap().get(2).get(4))
                            + "&right_zd5=" + isNull(TableRom2Adapter.getMap().get(2).get(5))
                            + "&right_zd6=" + isNull(TableRom2Adapter.getMap().get(2).get(6))
                            + "&right_zd7=" + isNull(TableRom2Adapter.getMap().get(2).get(7))
                            + "&right_zd8=" + isNull(TableRom2Adapter.getMap().get(2).get(8))
                            + "&right_zd9=" + isNull(TableRom2Adapter.getMap().get(2).get(9))
                            + "&right_zd10=" + isNull(TableRom2Adapter.getMap().get(2).get(10))
                            + "&right_zd11=" + isNull(TableRom2Adapter.getMap().get(2).get(11))
                            + "&right_zd12=" + isNull(TableRom2Adapter.getMap().get(2).get(12))
                            + "&right_zd13=" + isNull(TableRom2Adapter.getMap().get(2).get(13))
                            + "&right_zd14=" + isNull(TableRom2Adapter.getMap().get(2).get(14))
                            + "&right_zd15=" + isNull(TableRom2Adapter.getMap().get(2).get(15))
                            + "&right_zd16=" + isNull(TableRom2Adapter.getMap().get(2).get(16))
                            + "&right_zd17=" + isNull(TableRom2Adapter.getMap().get(2).get(17))
                            + "&right_zd18=" + isNull(TableRom2Adapter.getMap().get(2).get(18))
                            + "&right_zd19=" + isNull(TableRom2Adapter.getMap().get(2).get(19))
                            + "&right_zd20=" + isNull(TableRom2Adapter.getMap().get(2).get(20))
                            + "&right_zd21=" + isNull(TableRom2Adapter.getMap().get(2).get(21))
                            + "&right_zd22=" + isNull(TableRom2Adapter.getMap().get(2).get(22))
                            + "&right_zd23=" + isNull(TableRom2Adapter.getMap().get(2).get(23))
                            + "&right_zd24=" + isNull(TableRom2Adapter.getMap().get(2).get(24))
                            + "&right_zd25=" + isNull(TableRom2Adapter.getMap().get(2).get(25))
                            + "&right_zd26=" + isNull(TableRom2Adapter.getMap().get(2).get(26))
                            + "&right_zd27=" + isNull(TableRom2Adapter.getMap().get(2).get(27))
                            + "&right_zd28=" + isNull(TableRom2Adapter.getMap().get(2).get(28))
                            + "&right_zd29=" + isNull(TableRom2Adapter.getMap().get(2).get(29))
                            + "&right_zd30=" + isNull(TableRom2Adapter.getMap().get(2).get(30))
                            + "&right_zd31=" + isNull(TableRom2Adapter.getMap().get(2).get(31))
                            + "&right_zd32=" + isNull(TableRom2Adapter.getMap().get(2).get(32))
                            + "&right_zd33=" + isNull(TableRom2Adapter.getMap().get(2).get(33))
                            + "&right_zd34=" + isNull(TableRom2Adapter.getMap().get(2).get(34))
                            + "&right_zd35=" + isNull(TableRom2Adapter.getMap().get(2).get(35))
                            + "&right_zd36=" + isNull(TableRom2Adapter.getMap().get(2).get(36))
                            + "&right_zd37=" + isNull(TableRom2Adapter.getMap().get(2).get(37))
                            + "&right_zd38=" + isNull(TableRom2Adapter.getMap().get(2).get(38))
                            + "&right_zd39=" + isNull(TableRom2Adapter.getMap().get(2).get(39))
                            + "&right_zd40=" + isNull(TableRom2Adapter.getMap().get(2).get(40))
                            + "&right_zd41=" + isNull(TableRom2Adapter.getMap().get(2).get(41))
                            + "&right_zd42=" + isNull(TableRom2Adapter.getMap().get(2).get(42))
                            + "&right_zd43=" + isNull(TableRom2Adapter.getMap().get(2).get(43))
                            + "&right_zd44=" + isNull(TableRom2Adapter.getMap().get(2).get(44))
                            + "&right_zd45=" + isNull(TableRom2Adapter.getMap().get(2).get(45))
                            + "&right_zd46=" + isNull(TableRom2Adapter.getMap().get(2).get(46))
                            + "&right_zd47=" + isNull(TableRom2Adapter.getMap().get(2).get(47))
                            + "&right_zd48=" + isNull(TableRom2Adapter.getMap().get(2).get(48))
                            + "&right_zd49=" + isNull(TableRom2Adapter.getMap().get(2).get(49))
                            + "&right_zd50=" + isNull(TableRom2Adapter.getMap().get(2).get(50))
                            + "&right_zd51=" + isNull(TableRom2Adapter.getMap().get(2).get(51))
                            + "&right_zd52=" + isNull(TableRom2Adapter.getMap().get(2).get(52))
                            + "&right_zd53=" + isNull(TableRom2Adapter.getMap().get(2).get(53))
                            + "&right_zd54=" + isNull(TableRom2Adapter.getMap().get(2).get(54))
                            + "&right_zd55=" + isNull(TableRom2Adapter.getMap().get(2).get(55))
                            + "&right_zd56=" + isNull(TableRom2Adapter.getMap().get(2).get(56))
                            + "&right_zd57=" + isNull(TableRom2Adapter.getMap().get(2).get(57))
                            + "&right_zd58=" + isNull(TableRom2Adapter.getMap().get(2).get(58))
                            + "&right_zd59=" + isNull(TableRom2Adapter.getMap().get(2).get(59))
                            + "&right_zd60=" + isNull(TableRom2Adapter.getMap().get(2).get(60))
                            + "&right_zd61=" + isNull(TableRom2Adapter.getMap().get(2).get(61))
                            + "&right_zd62=" + isNull(TableRom2Adapter.getMap().get(2).get(62))
                            + "&right_zd63=" + isNull(TableRom2Adapter.getMap().get(2).get(63))
                            + "&right_zd64=" + isNull(TableRom2Adapter.getMap().get(2).get(64))
                            + "&right_zd65=" + isNull(TableRom2Adapter.getMap().get(2).get(65))
                            + "&right_zd66=" + isNull(TableRom2Adapter.getMap().get(2).get(66))
                            + "&right_zd67=" + isNull(TableRom2Adapter.getMap().get(2).get(67))
                            + "&right_zd68=" + isNull(TableRom2Adapter.getMap().get(2).get(68))
                            + "&right_zd69=" + isNull(TableRom2Adapter.getMap().get(2).get(69))


                            + "&right_bd0=" + isNull(TableRom2Adapter.getMap().get(3).get(0))
                            + "&right_bd1=" + isNull(TableRom2Adapter.getMap().get(3).get(1))
                            + "&right_bd2=" + isNull(TableRom2Adapter.getMap().get(3).get(2))
                            + "&right_bd3=" + isNull(TableRom2Adapter.getMap().get(3).get(3))
                            + "&right_bd4=" + isNull(TableRom2Adapter.getMap().get(3).get(4))
                            + "&right_bd5=" + isNull(TableRom2Adapter.getMap().get(3).get(5))
                            + "&right_bd6=" + isNull(TableRom2Adapter.getMap().get(3).get(6))
                            + "&right_bd7=" + isNull(TableRom2Adapter.getMap().get(3).get(7))
                            + "&right_bd8=" + isNull(TableRom2Adapter.getMap().get(3).get(8))
                            + "&right_bd9=" + isNull(TableRom2Adapter.getMap().get(3).get(9))
                            + "&right_bd10=" + isNull(TableRom2Adapter.getMap().get(3).get(10))
                            + "&right_bd11=" + isNull(TableRom2Adapter.getMap().get(3).get(11))
                            + "&right_bd12=" + isNull(TableRom2Adapter.getMap().get(3).get(12))
                            + "&right_bd13=" + isNull(TableRom2Adapter.getMap().get(3).get(13))
                            + "&right_bd14=" + isNull(TableRom2Adapter.getMap().get(3).get(14))
                            + "&right_bd15=" + isNull(TableRom2Adapter.getMap().get(3).get(15))
                            + "&right_bd16=" + isNull(TableRom2Adapter.getMap().get(3).get(16))
                            + "&right_bd17=" + isNull(TableRom2Adapter.getMap().get(3).get(17))
                            + "&right_bd18=" + isNull(TableRom2Adapter.getMap().get(3).get(18))
                            + "&right_bd19=" + isNull(TableRom2Adapter.getMap().get(3).get(19))
                            + "&right_bd20=" + isNull(TableRom2Adapter.getMap().get(3).get(20))
                            + "&right_bd21=" + isNull(TableRom2Adapter.getMap().get(3).get(21))
                            + "&right_bd22=" + isNull(TableRom2Adapter.getMap().get(3).get(22))
                            + "&right_bd23=" + isNull(TableRom2Adapter.getMap().get(3).get(23))
                            + "&right_bd24=" + isNull(TableRom2Adapter.getMap().get(3).get(24))
                            + "&right_bd25=" + isNull(TableRom2Adapter.getMap().get(3).get(25))
                            + "&right_bd26=" + isNull(TableRom2Adapter.getMap().get(3).get(26))
                            + "&right_bd27=" + isNull(TableRom2Adapter.getMap().get(3).get(27))
                            + "&right_bd28=" + isNull(TableRom2Adapter.getMap().get(3).get(28))
                            + "&right_bd29=" + isNull(TableRom2Adapter.getMap().get(3).get(29))
                            + "&right_bd30=" + isNull(TableRom2Adapter.getMap().get(3).get(30))
                            + "&right_bd31=" + isNull(TableRom2Adapter.getMap().get(3).get(31))
                            + "&right_bd32=" + isNull(TableRom2Adapter.getMap().get(3).get(32))
                            + "&right_bd33=" + isNull(TableRom2Adapter.getMap().get(3).get(33))
                            + "&right_bd34=" + isNull(TableRom2Adapter.getMap().get(3).get(34))
                            + "&right_bd35=" + isNull(TableRom2Adapter.getMap().get(3).get(35))
                            + "&right_bd36=" + isNull(TableRom2Adapter.getMap().get(3).get(36))
                            + "&right_bd37=" + isNull(TableRom2Adapter.getMap().get(3).get(37))
                            + "&right_bd38=" + isNull(TableRom2Adapter.getMap().get(3).get(38))
                            + "&right_bd39=" + isNull(TableRom2Adapter.getMap().get(3).get(39))
                            + "&right_bd40=" + isNull(TableRom2Adapter.getMap().get(3).get(40))
                            + "&right_bd41=" + isNull(TableRom2Adapter.getMap().get(3).get(41))
                            + "&right_bd42=" + isNull(TableRom2Adapter.getMap().get(3).get(42))
                            + "&right_bd43=" + isNull(TableRom2Adapter.getMap().get(3).get(43))
                            + "&right_bd44=" + isNull(TableRom2Adapter.getMap().get(3).get(44))
                            + "&right_bd45=" + isNull(TableRom2Adapter.getMap().get(3).get(45))
                            + "&right_bd46=" + isNull(TableRom2Adapter.getMap().get(3).get(46))
                            + "&right_bd47=" + isNull(TableRom2Adapter.getMap().get(3).get(47))
                            + "&right_bd48=" + isNull(TableRom2Adapter.getMap().get(3).get(48))
                            + "&right_bd49=" + isNull(TableRom2Adapter.getMap().get(3).get(49))
                            + "&right_bd50=" + isNull(TableRom2Adapter.getMap().get(3).get(50))
                            + "&right_bd51=" + isNull(TableRom2Adapter.getMap().get(3).get(51))
                            + "&right_bd52=" + isNull(TableRom2Adapter.getMap().get(3).get(52))
                            + "&right_bd53=" + isNull(TableRom2Adapter.getMap().get(3).get(53))
                            + "&right_bd54=" + isNull(TableRom2Adapter.getMap().get(3).get(54))
                            + "&right_bd55=" + isNull(TableRom2Adapter.getMap().get(3).get(55))
                            + "&right_bd56=" + isNull(TableRom2Adapter.getMap().get(3).get(56))
                            + "&right_bd57=" + isNull(TableRom2Adapter.getMap().get(3).get(57))
                            + "&right_bd58=" + isNull(TableRom2Adapter.getMap().get(3).get(58))
                            + "&right_bd59=" + isNull(TableRom2Adapter.getMap().get(3).get(59))
                            + "&right_bd60=" + isNull(TableRom2Adapter.getMap().get(3).get(60))
                            + "&right_bd61=" + isNull(TableRom2Adapter.getMap().get(3).get(61))
                            + "&right_bd62=" + isNull(TableRom2Adapter.getMap().get(3).get(62))
                            + "&right_bd63=" + isNull(TableRom2Adapter.getMap().get(3).get(63))
                            + "&right_bd64=" + isNull(TableRom2Adapter.getMap().get(3).get(64))
                            + "&right_bd65=" + isNull(TableRom2Adapter.getMap().get(3).get(65))
                            + "&right_bd66=" + isNull(TableRom2Adapter.getMap().get(3).get(66))
                            + "&right_bd67=" + isNull(TableRom2Adapter.getMap().get(3).get(67))
                            + "&right_bd68=" + isNull(TableRom2Adapter.getMap().get(3).get(68))
                            + "&right_bd69=" + isNull(TableRom2Adapter.getMap().get(3).get(69))


                            + "&token=" + NetUrlAddress.token;


                    new MyThread().start();
                }
                break;
            case R.id.date:

                L.d("position", TableRom2Adapter.getMap().get(0).size() + "==position0");

                for (Integer key : TableRom2Adapter.getMap().get(0).keySet()) {
                    L.d("positon", "key==" + key + "//" + "value=" + TableRom2Adapter.getMap().get(0).get(key));
                }

                L.d("position", TableRom2Adapter.getMap().get(1).size() + "==position1");
                L.d("position", TableRom2Adapter.getMap().get(2).size() + "==position2");
                L.d("position", TableRom2Adapter.getMap().get(3).size() + "==position3");

                pwTime.showAtLocation(date, Gravity.BOTTOM, 0, 0, new Date());
                break;

            default:
                break;
        }

    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            Looper.prepare();
            try {
                if (isAdd) {
                    create_code = TableRomInfo.doPost(NetUrlAddress.Rom_create_url, param);
                } else {
                    String url = NetUrlAddress.Rom_update_url.replaceAll("aaa", info.getRecord_id() + "");
                    update_code = TableRomInfo.doPost(url, param);
                }
                if (create_code == 200) {
                    TableRomInfo2Activity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableRomInfo2Activity.this.finish();
                    ShowToast.Short(getString(R.string.msg_update_success));
                } else if (create_code == 100 || create_code == 500) {
                    ShowToast.Short(getString(R.string.msg_save_fail));
                } else if (update_code == 100 || create_code == 500) {
                    ShowToast.Short(getString(R.string.msg_update_fail));
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            } catch (Exception e) {
                L.d("text", "出错了");
                e.printStackTrace();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            Looper.loop();
        }
    }

    /**
     * 判断是否有没有完整填写数据
     *
     * @return
     */
    boolean dataIsEmpty() {
        if (TextUtils.isEmpty(date.getText())) {
            ShowToast.Short(getString(R.string.msg_date));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是null值
     *
     * @param s
     * @return
     */
    String isNull(Object s) {
        if (s == null) {
            return "";
        } else {
            return s.toString();
        }
    }

}
