package skankhunt.com.androidboot.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import skankhunt.com.androidboot.R;
import skankhunt.com.androidboot.di.Injectable;
import skankhunt.com.androidboot.ui.main.account.AccountFragment;
import skankhunt.com.androidboot.ui.main.home.HomeFragment;
import skankhunt.com.androidboot.ui.main.serve.ServeFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements Injectable {

    private ArrayList<Fragment> fragments;
    private int prePos;
    private String PRE = "PREPOS";

    @BindView(R.id.bottom_navi)
    BottomNavigationView bottomNavi;
    Unbinder unbinder;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bottomNavi.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState==null){
            //默认为0
            prePos = 0;
            fragments = new ArrayList<>();
            buildFragmentList();
        }else{
            //内存被回收了，fragments的list也被回收了，重新add进去
            prePos = savedInstanceState.getInt(PRE);
            fragments = new ArrayList<>();

            HomeFragment homeFragment = (HomeFragment) getFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            ServeFragment serveFragment = (ServeFragment) getFragmentManager().findFragmentByTag(ServeFragment.class.getName());
            AccountFragment accountFragment = (AccountFragment) getFragmentManager().findFragmentByTag(AccountFragment.class.getName());

            fragments.add(homeFragment);
            fragments.add(serveFragment);
            fragments.add(accountFragment);
        }
        //设置默认
        setDefaultFragment(prePos);
    }

    //以后要处理下反复使用setIcon的内存优化，用glide
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_gps:
                    switchFragment(0);
                    return true;
                case R.id.navigation_home:
                    switchFragment(1);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(2);
                    return true;
            }
            return false;
        }

    };

    private void buildFragmentList(){
        //用fragment的静态工厂方法，因为内存回收的时候会自动调用无参数的构造方法，然后调用oncreate，在NewInstance的参数就会从新在bundle上获取

        HomeFragment homeFragment = new HomeFragment();
        ServeFragment serveFragment = new ServeFragment();
        AccountFragment accountFragment = new AccountFragment();


        fragments.add(homeFragment);
        fragments.add(serveFragment);
        fragments.add(accountFragment);

    }

    private void switchFragment(int pos) {
        //Toast.makeText(this,prePos+" -> "+pos,Toast.LENGTH_LONG).show();
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        Fragment from = fragments.get(prePos);
        Fragment to = fragments.get(pos);
        if(!to.isAdded()){
            transaction.hide(from)
                    .add(R.id.main_container,to,to.getClass().getName())//用classname做tag
                    .commit();
        }else{
            transaction.hide(from)
                    .show(to)
                    .commit();
        }
        prePos = pos;
    }

    //设置默认
    private void setDefaultFragment(int pos){
        Fragment now = fragments.get(pos);
        if(!now.isAdded()){
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container,fragments.get(prePos),fragments.get(prePos).getClass().getName())
                    .commit();
        }else{
            getFragmentManager()
                    .beginTransaction()
                    .show(now)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存上一个位置
        outState.putInt(PRE,prePos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
