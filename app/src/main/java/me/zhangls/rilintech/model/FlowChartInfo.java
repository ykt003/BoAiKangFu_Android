package me.zhangls.rilintech.model;

/**
 * Created by zsn on 15/12/1.
 */
public class FlowChartInfo {
    //名称
    private String name;
    //患者id
    private Integer[]id;
    //标志 true false
    private int flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getId() {
        return id;
    }

    public void setId(Integer[] id) {
        this.id = id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
