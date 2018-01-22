package skankhunt.com.androidboot.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import skankhunt.com.androidboot.AppExecutors;
import skankhunt.com.androidboot.api.ApiResponse;
import skankhunt.com.androidboot.api.GithubService;
import skankhunt.com.androidboot.db.UserDao;
import skankhunt.com.androidboot.models.User;
import skankhunt.com.androidboot.vo.Resource;

/**
 * Created by skankhunt on 2018/1/22.
 */

@Singleton
public class UserRepository {

    private final UserDao userDao;
    private final GithubService githubService;
    private final AppExecutors appExecutors;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao,GithubService githubService){
        this.userDao = userDao;
        this.appExecutors = appExecutors;
        this.githubService = githubService;
    }

    public LiveData<Resource<User>> loadUser(){

        return new NetworkBoundResource<User,User>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.insert(item);

            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data==null;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByLogin() ;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return githubService.getUser();
            }
        }.asLiveData();

    }


}
