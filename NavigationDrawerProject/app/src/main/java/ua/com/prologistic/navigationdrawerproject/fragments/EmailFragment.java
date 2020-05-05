package ua.com.prologistic.navigationdrawerproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.com.prologistic.navigationdrawerproject.R;

public class EmailFragment extends Fragment {

    public EmailFragment() {}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        return view;
    }
}