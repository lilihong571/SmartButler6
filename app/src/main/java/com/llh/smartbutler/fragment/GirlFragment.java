package com.llh.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llh.smartbutler.R;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.fragment
 * 文件名:    ButlerFragment
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:26
 * 描述:      TODO
 */
public class GirlFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl,container,false);
        return view;
    }
}
