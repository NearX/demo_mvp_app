package com.ybkj.demo.bean.response;

import com.ybkj.demo.bean.BaseResponse;

public class MineDataRes extends BaseResponse {


    /**
     * myInfoVO : {"userId":299,"userName":"三三","userAccount":"chenminhua","userCode":"123233p","departmentName":"二级部门111111111111","postName":"新增岗位四四四四四四四四四四四四","renameState":1}
     */

    private MyInfoVOBean myInfoVO;

    public MyInfoVOBean getMyInfoVO() {
        return myInfoVO;
    }

    public void setMyInfoVO(MyInfoVOBean myInfoVO) {
        this.myInfoVO = myInfoVO;
    }

    public static class MyInfoVOBean {
        /**
         * userId : 299
         * userName : 三三
         * userAccount : chenminhua
         * userCode : 123233p
         * departmentName : 二级部门111111111111
         * postName : 新增岗位四四四四四四四四四四四四
         * renameState : 1
         */

        private String userId;
        private String userName;
        private String userAccount;
        private String userCode;
        private String departmentName;
        private String postName;
        private String renameState;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getRenameState() {
            return renameState;
        }

        public void setRenameState(String renameState) {
            this.renameState = renameState;
        }
    }
}
