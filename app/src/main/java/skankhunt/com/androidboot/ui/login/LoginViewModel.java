package skankhunt.com.androidboot.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import skankhunt.com.androidboot.db.LoginDao;
import skankhunt.com.androidboot.db.UserDao;
import skankhunt.com.androidboot.models.User;
import skankhunt.com.androidboot.vo.Login;

/**
 * Created by skankhunt on 2018/1/26.
 */

public class LoginViewModel extends ViewModel {

    private final LoginDao loginDao;

    //登陆状态
    //private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();
    private MutableLiveData<Login>  myLogin = new MutableLiveData<>();
    private LiveData<Login> loginBydao;

    @Inject
    public LoginViewModel(LoginDao loginDao) {
        this.loginDao = loginDao;

        loginBydao = loginDao.findByLogin();

    }

    public LiveData<Login> getLoginByDao(){
        return  loginBydao;
    }

   /* public LiveData<Login> getLoginByDao(){
        /return loginDao.findByLogin();
    }*/

    //更新状态

    public void setMyLogin(Login login){
        myLogin.postValue(login);
        loginDao.insert(login);
    }

    //获得状态

    public MutableLiveData<Login> getMyLogin() {
        return myLogin;
    }
}
