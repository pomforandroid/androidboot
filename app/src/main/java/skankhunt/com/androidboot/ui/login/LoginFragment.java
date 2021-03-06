package skankhunt.com.androidboot.ui.login;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import skankhunt.com.androidboot.LoginActivity;
import skankhunt.com.androidboot.OldMainActivity;
import skankhunt.com.androidboot.R;
import skankhunt.com.androidboot.di.Injectable;
import skankhunt.com.androidboot.ui.LiveDataTimerViewModel;
import skankhunt.com.androidboot.ui.common.NavigationController;
import skankhunt.com.androidboot.util.Util;
import skankhunt.com.androidboot.vo.Login;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements Injectable, PlatformActionListener {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    @BindView(R.id.tv_sendcode)
    TextView tvSendcode;
    Unbinder unbinder;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LiveDataTimerViewModel liveDataTimerViewModel;
    private String usernmae;

    @Inject
    NavigationController navigationController;
    private LoginViewModel loginViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //计时器
        liveDataTimerViewModel = ViewModelProviders.of(this, viewModelFactory).get(LiveDataTimerViewModel.class);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        subcribe_login();

        //发送验证码
        tvSendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSmsCode();
                //subscribe_count();
            }
        });
        //验证并登陆
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndLogin();
                /*Intent intent = new Intent(LoginActivity.this,OldMainActivity.class);
                startActivity(intent);*/
            }
        });

        ivWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatLogin();
            }
        });

        ivQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqLogin();
            }
        });
    }

    //
    private void subcribe_login() {

        loginViewModel.getMyLogin().observe(this, new Observer<Login>() {
            @Override
            public void onChanged(@Nullable Login login) {
                if(login!=null){
                    boolean loginState = login.loginState;
                    String loginType = login.loginType;
                    if(loginState){
                        navigationController.navigateToMain();
                    }else{
                        navigationController.navigateToLogin();
                    }
                }
            }
        });

    }


    private void wechatLogin() {
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weixin.setPlatformActionListener(this);
//authorize与showUser单独调用一个即可
        weixin.authorize();//单独授权,OnComplete返回的hashmap是空的
        //weixin.showUser(null);//授权并获取用户信息
//移除授权
//weibo.removeAccount(true);
    }
    private void qqLogin(){
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(this);
//authorize与showUser单独调用一个即可
        qq.authorize();//单独授权,OnComplete返回的hashmap是空的
        //qq.showUser(null);//授权并获取用户信息
//移除授权
//weibo.removeAccount(true);
    }
    /**
     * 开始计时
     */
    private void subscribe_count(){
        tvSendcode.setOnClickListener(null);
        liveDataTimerViewModel.setElapsedTime();
        liveDataTimerViewModel.getElapsedTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {

                if(aLong!=null){
                    String newText = getContext().getResources().getString(
                            R.string.send_code_again, aLong);
                    tvSendcode.setText(newText);
                    if(aLong==0){
                        //倒计时结束
                        tvSendcode.setText("重新发送");
                        tvSendcode.setOnClickListener(view ->
                                //subscribe_count()
                                getSmsCode()
                        );
                    }
                }
            }
        });
    }

    /**
     * 获得短信验证码
     */
    private void getSmsCode() {
        //sendCode(this);
        //获得输入的手机号码
        usernmae = etUsername.getText().toString();

        //判断是否11位手机号
        if (Util.isPhoneNum(usernmae)) {
            sendCode("86", usernmae);
        } else {
            Util.showSnackbar(etUsername, "手机格式有误");
        }
    }


    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                //注意，这个回调会有几次，如果startActivity要finish，要不会有多个界面
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Util.showSnackbar(etPassword, "已发送验证码");
                    //开始倒计时
                    subscribe_count();

                } else {
                    // TODO 处理错误的结果
                    Util.showSnackbar(etPassword, data.toString());
                }
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }


    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Util.showSnackbar(etPassword,getString(R.string.login_success));
                    //登陆成功去main
                    //navigationController.navigateToMain();
                    Login login = new Login("phone", true);
                    loginViewModel.setMyLogin(login);
                } else {
                    // TODO 处理错误的结果
                    Util.showSnackbar(etPassword,getString(R.string.login_fail));
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    /**
     * 验证并登陆
     */
    private void checkAndLogin() {
        String code = etPassword.getText().toString();
        if(!TextUtils.isEmpty(code)){
            submitCode("86",usernmae,code);
        }else{
            Util.showSnackbar(etPassword,getString(R.string.empty_code_tips));
        }


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
        unbinder.unbind();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //输出所有授权信息
        String s = platform.getDb().exportData();
        Timber.w("skankhunt  %s", s);
        //navigationController.navigateToMain();
        Login login = new Login("qq_wechat", true);
        loginViewModel.setMyLogin(login);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
