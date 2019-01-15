package com.ybkj.demo;

/**
 * 生成一个BUG
 */
public class BugClassDemo {
    public String bug() {

        // 这段代码会报空指针异常
        String str = null;

//        return str.toString();
        return "测试完毕";
    }
}
