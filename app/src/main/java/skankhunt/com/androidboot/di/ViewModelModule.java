package skankhunt.com.androidboot.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;



import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import skankhunt.com.androidboot.ViewModel.GithubViewModelFactory;
import skankhunt.com.androidboot.ui.LiveDataTimerViewModel;
import skankhunt.com.androidboot.ui.UserViewModel;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LiveDataTimerViewModel.class)
    abstract ViewModel bindLiveDataTimerViewModel(LiveDataTimerViewModel liveDataTimerViewModel);
   /* @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel.class)
    abstract ViewModel bindRepoViewModel(RepoViewModel repoViewModel);*/

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
