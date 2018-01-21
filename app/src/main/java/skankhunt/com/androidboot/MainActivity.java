package skankhunt.com.androidboot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.inject.Inject;

import skankhunt.com.androidboot.dagger.MainComponent;
import skankhunt.com.androidboot.dagger.Poetry;

public class MainActivity extends AppCompatActivity {

    //添加@Inject注解，表示这个mPoetry是需要注入的
    @Inject
    Poetry mPoetry;

    @Inject
    Gson mGson;

    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用Dagger2生成的类 生成组件进行构造，并注入
//        DaggerMainComponent.builder()
//                .build()
//                .inject(this);

//        MainComponent.getInstance()
//                .inj
        MainComponent.getInstance().inject(this);

        initView();
       /* GithubService githubService = GithubApp.get(this).component().getGithubService();
        Log.e("TAG","githubService:"+ githubService);

       *//* githubService.getOrder().enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                MyOrder body = response.body();
                String error = body.getError();
                List<MyOrder.OrderlistBean> orderlist = body.getOrderlist();
            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {

            }
        });*//*
        githubService.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                int age = body.getAge();
                int id = body.getId();
                String name = body.getName();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });*/
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv_poetry);
        String json = mGson.toJson(mPoetry);
        String text = json + ",mPoetry:"+mPoetry;
        mTextView.setText(text);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OtherActivity.class);
                startActivity(intent);
            }
        });

    }
}
