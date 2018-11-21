package com.ybkj.demo.net.api;

import com.ybkj.demo.bean.BaseResponse;
import com.ybkj.demo.bean.request.LoginReq;
import com.ybkj.demo.bean.request.ModifyPassReq;
import com.ybkj.demo.bean.request.ModifyReq;
import com.ybkj.demo.bean.response.LoginRes;
import com.ybkj.demo.bean.response.MineDataRes;
import com.ybkj.demo.bean.response.VersionRes;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  ApiService
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public interface ApiService {

    /**
     * 用户登录
     *
     * @param req 请求实体类
     * @return
     */
    @POST("userApp/login/login")
    Observable<BaseResponse<LoginRes>> startLogin(@Body LoginReq req);

    /**
     * 找回密码
     *
     * @return
     */
    @POST("userApp/frogetPassword/froget")
    Observable<BaseResponse<Object>> forgetPass(@Body ModifyPassReq modifyPassReq);

    /**
     * 修改默认密码
     *
     * @returna
     */
    @POST("userApp/resetPassword//updatePwd")
    Observable<BaseResponse<Object>> modifyNormalPwd(@Body ModifyPassReq modifyPassReq);


    /**
     * 我的
     *
     * @return
     */
    @POST("userApp/myInfo/show")
    Observable<BaseResponse<MineDataRes>> getMineData();

    /**
     * 修改密码
     *
     * @return
     */
    @POST("userApp/myInfo/modifyPwd")
    Observable<BaseResponse<MineDataRes>> modifyPsd(@Body ModifyReq req);

    /**
     * 获取验证码
     */
    @POST("sendCode/sendPhoneCode")
    Observable<BaseResponse<Object>> getVerificationCode(@Body ModifyReq req);

    /**
     * 修改手机号码
     *
     * @param req
     * @return
     */
    @POST("userApp/myInfo/modifyPhone")
    Observable<BaseResponse<Object>> modifyPhone(@Body ModifyReq req);


    /**
     * 版本更新
     *
     * @return
     */
    @POST("userApp/version/updateVersion")
    Observable<BaseResponse<VersionRes>> getVersion();

    /**
     * 修改账号
     *
     * @param
     * @return
     */
    @POST("userApp/myInfo/modifyUserAccount")
    Observable<BaseResponse<Object>> modifyAccount(@Body ModifyReq req);


}
