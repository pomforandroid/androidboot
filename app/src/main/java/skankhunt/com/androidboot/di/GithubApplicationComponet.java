package skankhunt.com.androidboot.di;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by skankhunt on 2018/1/19.
 */

@GithubApplicationScope
@Component(modules = {GithubServiceModule.class})
public interface GithubApplicationComponet {

        GithubService getGithubService();
}
