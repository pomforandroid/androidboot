package skankhunt.com.androidboot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skankhunt.com.androidboot.dagger.Poetry;
import skankhunt.com.androidboot.di.GithubService;
import skankhunt.com.androidboot.models.MyOrder;
import skankhunt.com.androidboot.models.User;

public class MainActivity extends AppCompatActivity {

    //添加@Inject注解，表示这个mPoetry是需要注入的
    @Inject
    Poetry mPoetry;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        GithubService githubService = GithubApp.get(this).component().getGithubService();
        Log.e("TAG","githubService:"+ githubService);

       /* githubService.getOrder().enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                MyOrder body = response.body();
                String error = body.getError();
                List<MyOrder.OrderlistBean> orderlist = body.getOrderlist();
            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {

            }
        });*/
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
        });
    }

    private void initView() {
        mTextView = (TextView)findViewById(R.id.tv_poetry);
        mTextView.setText(mPoetry.getmPemo());

    }
}
