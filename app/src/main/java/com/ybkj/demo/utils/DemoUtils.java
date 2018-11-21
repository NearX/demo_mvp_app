package com.ybkj.demo.utils;

import java.util.ArrayList;
import java.util.List;

public class DemoUtils {

    public static List<String> performanceList;

    private static List<String> stateList;


    public static List<String> getStateList() {
        if (stateList == null) {
            stateList = new ArrayList<>();
            stateList.add("待处理");
            stateList.add("退回上级");
            stateList.add("已处理");
            stateList.add("驳回");
        }
        return stateList;
    }

    public static String getState(int id) {
        switch (id) {
            case 1:
                return "待处理 ";
            case 2:
                return "退回上级 ";
            case 3:
                return "已处理 ";
            case 4:
                return "驳回";
        }
        return "";
    }

    /**
     * 申述状态转化
     *
     * @param type
     * @return
     */
    public static int stateStringToInt(String type) {

        for (int i = 0; i < getStateList().size(); i++) {
            if (type.equals(getStateList().get(i))) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * 职位列表
     *
     * @param positionName
     * @return
     */
    public static List<String> getPositionNameList(List<String> positionName) {
        if (positionName != null) {
            return positionName;
        }
        return positionName;
    }

    /**
     * 部门列表
     *
     * @param positionName
     * @return
     */
    public static List<String> getDepartmentNameList(List<String> positionName) {
        return positionName;
    }

    /**
     * 绩效状态
     *
     * @return
     */
    public static List<String> getPerformanceNameList() {
        if (performanceList == null) {
            performanceList = new ArrayList<>();
            performanceList.add("全部");
            performanceList.add("指标上级审核");
            performanceList.add("指标待修改");
            performanceList.add("指标待最终审核");
            performanceList.add("指标审核拒绝");
        }
        return performanceList;
    }

    /**
     * 获取绩效状态
     *
     * @param name
     * @return
     */
    public static String getValue(String name) {
        switch (name) {
            case "全部":
                return "";
            case "指标上级审核":
                return "2";
            case "指标待修改":
                return "3";
            case "指标待最终审核":
                return "4";
            case "指标审核拒绝":
                return "8";
        }
        return "";
    }
    /*绩效评估状态
     *1：待提交
     *2：上级审核
     *3：待修改
     *4：最终审核
     *5：审核拒绝
     *6：待自评
     *7：上级考核
     *8：重新自评
     *9：绩效审批
     *10：审批拒绝
     *11：员工确认
     *12：申诉中
     *13：上级考核
     *14：已完成*/

    /**
     * 下属绩效 或 指标 状态
     *
     * @param state
     * @return
     */
    public static String toAppraisalStateStr(int state) {
        switch (state) {
            case 1:
                return "待提交";
            case 2:
                return "上级审核";
            case 3:
                return "待修改";
            case 4:
                return "最终审核";
            case 5:
                return "审核拒绝";
            case 6:
                return "待自评";
            case 7:
                return "上级考核";
            case 8:
                return "重新自评";
            case 9:
                return "绩效审批";
            case 10:
                return "审批拒绝";
            case 11:
                return "员工确认";
            case 12:
                return "申诉中";
            case 13:
                return "已完成";
        }
        return "未知";
    }

    /**
     * 状态列表
     *
     * @param statusName
     * @return
     */
    public static List<String> getStateList(List<String> statusName) {
        return statusName;
    }

    /**
     * list转string 逗号分隔
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

}
