package com.soundcloud.android.pic;

/**
 * Created by Kellan on 2017/11/9.
 */

public interface ResultInfo<T> {
    void err(String msg);

    void result(T t);
}
