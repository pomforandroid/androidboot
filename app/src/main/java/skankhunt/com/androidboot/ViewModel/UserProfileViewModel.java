package skankhunt.com.androidboot.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import skankhunt.com.androidboot.models.User;

/**
 * Created by skankhunt on 2018/1/20.
 */

public class UserProfileViewModel extends ViewModel {

    private String userId;

    private LiveData<User> user;

    public LiveData<User> getUser(){
        return user;
    }
    public void init(String userId){
        this.userId = userId;
    }

}
