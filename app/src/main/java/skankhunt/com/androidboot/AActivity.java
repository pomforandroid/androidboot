package skankhunt.com.androidboot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;

import skankhunt.com.androidboot.dagger.Poetry;

public class AActivity extends AppCompatActivity {

    TextView mTextView;

    @Inject
    Gson mGson;



    // 匹配Module中同样注解的方法
    @Named("A")
    @Inject
    Poetry mPoetry;

    // 匹配Module中同样注解的方法
    @Named("B")
    @Inject
    Poetry mPoetryB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        GithubApp.getInstance()
                .getAComponent()
                .inject(this);

        mTextView = (TextView) findViewById(R.id.text);
        String text = mPoetry.getmPemo()+",mPoetryA:"+mPoetry+
                mPoetryB.getmPemo()+",mPoetryB:"+mPoetryB+
                (mGson == null ? "Gson没被注入" : "Gson已经被注入");
        mTextView.setText(text);
    }
}
