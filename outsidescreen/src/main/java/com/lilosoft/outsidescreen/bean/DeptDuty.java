package com.lilosoft.outsidescreen.bean;

/**
 * Created by chablis on 2016/11/14.
 */

public class DeptDuty {
    /**
     * projectname : 建设项目审批
     * id : 5b09f8dc08644e8b862c8c0f92e93fba
     * projecturl : http://hn.whsp.gov.cn/onlineService/project/queryZl.action?id=5b09f8dc08644e8b862c8c0f92e93fba
     * wechatprojecturl : http://hn.whsp.gov.cn/onlineService/project/queryZl.action?id=5b09f8dc08644e8b862c8c0f92e93fba
     */

    private String projectname;
    private String id;
    private String projecturl;
    private String wechatprojecturl;

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjecturl() {
        return projecturl;
    }

    public void setProjecturl(String projecturl) {
        this.projecturl = projecturl;
    }

    public String getWechatprojecturl() {
        return wechatprojecturl;
    }

    public void setWechatprojecturl(String wechatprojecturl) {
        this.wechatprojecturl = wechatprojecturl;
    }


    @Override
    public String toString() {
        return "DeptDuty{" +
                "projectname='" + projectname + '\'' +
                ", id='" + id + '\'' +
                ", projecturl='" + projecturl + '\'' +
                ", wechatprojecturl='" + wechatprojecturl + '\'' +
                '}';
    }
}
