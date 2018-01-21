package skankhunt.com.androidboot.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import skankhunt.com.androidboot.R;
import skankhunt.com.androidboot.ViewModel.UserProfileViewModel;
import skankhunt.com.androidboot.models.User;

public class UserProfileFragment extends Fragment {
    private static final String UID_KEY = "uid";
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_lastname)
    TextView tvLastname;
    @BindView(R.id.tv_id)
    TextView tvId;
    Unbinder unbinder;
    private UserProfileViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(userId);
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                //更新ui
                tvId.setText(user.getId()+"=-=");
                tvLastname.setText(user.getAge()+"-0-");
                tvName.setText(user.getName());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
