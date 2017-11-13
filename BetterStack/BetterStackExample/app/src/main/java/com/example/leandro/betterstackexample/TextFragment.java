package com.example.leandro.betterstackexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leandro.betterstack.StackInfo;

import java.util.HashMap;
import java.util.Map;

public class TextFragment extends Fragment implements StackInfo {
    boolean restored = false; //You should have a flag to know whenever there is an state to restore

    //State info
    String tab;
    Integer number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle arguments = getArguments();
        TextView infoText = (TextView) view.findViewById(R.id.iv_num_frag);

        //Check if there is an state to recover
        if (restored) {
            String text = tab + "." + number;
            infoText.setText(text);
        }

        view.findViewById(R.id.next_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextFragment fragment = new TextFragment();
                HashMap<String,Object> arguments = new HashMap<String, Object>();
                arguments.put("tab", tab);
                arguments.put("number", number+1);
                fragment.restoreState(arguments);

                ((MainActivity) getActivity()).getStackTab().pushCurrent(fragment);
            }
        });

        return view;
    }



    //region StackInfo implementation
    @Override
    public void restoreState(Map<String, Object> info) {
        if (info!=null) {
            restored = true;
            tab = (String) info.get("tab");
            number = (Integer) info.get("number");
        }
    }

    @Override
    public Map<String, Object> saveState() {
        HashMap<String,Object> info = new HashMap<>();
        info.put("tab", tab);
        info.put("number",number);
        return info;
    }
    //endregion
}
