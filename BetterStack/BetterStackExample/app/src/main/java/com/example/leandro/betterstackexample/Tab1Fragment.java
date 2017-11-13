package com.example.leandro.betterstackexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leandro.betterstack.StackInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kellan on 2017/9/11.
 */

public class Tab1Fragment extends Fragment implements StackInfo, View.OnClickListener {
    private Button btTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        btTest = (Button) view.findViewById(R.id.id_test);
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

    @Override
    public void onClick(View v) {
//        TextFragment fragment = new TextFragment();
//        HashMap<String,Object> info = new HashMap<>();
//        info.put("tab", "Tab1");
//        info.put("number", 1);
//        fragment.restoreState(info);
//
//        stack.addToStack(fragment,"Tab1");
    }
}
