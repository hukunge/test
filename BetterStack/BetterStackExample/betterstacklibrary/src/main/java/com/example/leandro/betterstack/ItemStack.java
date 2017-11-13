package com.example.leandro.betterstack;

import android.support.v4.app.Fragment;

import java.util.Map;

/**
 * Created by Leandro on 07/01/2016.
 * Internal class that represents and stack item. Shouldn't be used by mortals.
 */
public class ItemStack {
    private Class fragmentType;
    private Map<String,Object> info;

    public ItemStack(Class fragmentClass, Map<String,Object> info){
        this.fragmentType = fragmentClass;
        this.info = info;
    }

    public Fragment recreate() {
        Fragment f;
        try {
            f = (Fragment) fragmentType.newInstance();

            if (f instanceof StackInfo)
                ((StackInfo)f).restoreState(info);

            return f;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
