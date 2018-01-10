package com.meiliangzi.app.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class TestData {

    public static List<String>  getData(int n){
        List<String> data = new ArrayList<>();
        for (int i=0;i<n;i++){
            data.add(String.valueOf(i));
        }
        return data;
    }
}
