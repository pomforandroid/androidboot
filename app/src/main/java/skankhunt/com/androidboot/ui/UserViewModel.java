package skankhunt.com.androidboot.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import skankhunt.com.androidboot.models.User;
import skankhunt.com.androidboot.repository.UserRepository;
import skankhunt.com.androidboot.vo.Resource;

/**
 * Created by skankhunt on 2018/1/22.
 */

public class UserViewModel extends ViewModel {

    private final LiveData<Resource<User>> user;

    @Inject
    public  UserViewModel(UserRepository userRepository){
        user = userRepository.loadUser();
    }

    public LiveData<Resource<User>> getUser() {
        return user;
    }
}
