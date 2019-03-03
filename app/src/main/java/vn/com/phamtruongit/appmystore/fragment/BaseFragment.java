package vn.com.phamtruongit.appmystore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    Unbinder knife;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(layout(),container,false);
        knife=ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    public abstract  int layout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // knife.unbind();
    }
}
