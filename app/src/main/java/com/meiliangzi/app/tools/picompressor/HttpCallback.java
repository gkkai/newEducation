package com.meiliangzi.app.tools.picompressor;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by kk on 2017/9/19.
 */

public interface HttpCallback extends Callback {
    public void onFailure(Call call, IOException e);
    public void onResponse(Call call, okhttp3.Response response);
}
