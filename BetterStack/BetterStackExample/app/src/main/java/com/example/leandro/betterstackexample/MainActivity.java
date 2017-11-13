package com.example.leandro.betterstackexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.leandro.betterstack.BetterStack;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private BetterStack stackTab; //You can have many stacks as you want

    protected static String[] tabList = {"Tab1", "Tab2", "Tab3"};
    private long firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stackTab = new BetterStack(
                getSupportFragmentManager(),
                R.id.fragment_container,
                Arrays.asList(tabList));

        Tab1Fragment tab1 = new Tab1Fragment();
        Tab2Fragment tab2 = new Tab2Fragment();
        Tab3Fragment tab3 = new Tab3Fragment();
        stackTab.addToStack(tab1,tabList[0]);
        stackTab.addToStack(tab2,tabList[1]);
        stackTab.addToStack(tab3,tabList[2]);

        stackTab.switchToStack(tabList[0]);

        findViewById(R.id.btn_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackTab.switchToStack(tabList[0]);
            }
        });
        findViewById(R.id.btn_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackTab.switchToStack(tabList[1]);
            }
        });
        findViewById(R.id.btn_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackTab.switchToStack(tabList[2]);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!stackTab.popCurrent()) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    public BetterStack getStackTab() {
        return stackTab;
    }
}
