package com.lilosoft.retrofit;

/**
 * Created by chablis on 2016/10/20.
 */
public class Dept {

    /**
     * is_order : 4
     * is_bus : 1
     * coordinate_type : 0
     * dept_id : 1024
     * area_code : 420104000000
     * dept_code : GTFJ
     * simply_name : 规土分局
     * dept_name : 区规土分局
     * dept_name_py : Q
     * is_use : 0
     * parent_id : 1
     */

    private String is_order;
    private String is_bus;
    private int coordinate_type;
    private String dept_id;
    private String area_code;
    private String dept_code;
    private String simply_name;
    private String dept_name;
    private String dept_name_py;
    private String is_use;
    private String parent_id;

    public String getIs_order() {
        return is_order;
    }

    public void setIs_order(String is_order) {
        this.is_order = is_order;
    }

    public String getIs_bus() {
        return is_bus;
    }

    public void setIs_bus(String is_bus) {
        this.is_bus = is_bus;
    }

    public int getCoordinate_type() {
        return coordinate_type;
    }

    public void setCoordinate_type(int coordinate_type) {
        this.coordinate_type = coordinate_type;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getSimply_name() {
        return simply_name;
    }

    public void setSimply_name(String simply_name) {
        this.simply_name = simply_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_name_py() {
        return dept_name_py;
    }

    public void setDept_name_py(String dept_name_py) {
        this.dept_name_py = dept_name_py;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "is_order='" + is_order + '\'' +
                ", is_bus='" + is_bus + '\'' +
                ", coordinate_type=" + coordinate_type +
                ", dept_id='" + dept_id + '\'' +
                ", area_code='" + area_code + '\'' +
                ", dept_code='" + dept_code + '\'' +
                ", simply_name='" + simply_name + '\'' +
                ", dept_name='" + dept_name + '\'' +
                ", dept_name_py='" + dept_name_py + '\'' +
                ", is_use='" + is_use + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
