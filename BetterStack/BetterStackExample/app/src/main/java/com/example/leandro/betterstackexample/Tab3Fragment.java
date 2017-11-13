package com.example.leandro.betterstackexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leandro.betterstack.StackInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kellan on 2017/9/11.
 */

public class Tab3Fragment extends Fragment implements StackInfo {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        return view;
    }


    @Override
    public void restoreState(Map<String, Object> info) {
    }

    @Override
    public Map<String, Object> saveState() {
        HashMap<String, Object> info = new HashMap<>();
        return info;
    }
}
