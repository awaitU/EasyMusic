package com.awaitu.easymusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.awaitu.easymusic.R;

import cn.smssdk.SMSSDK;

public class PersonRegistersp extends Fragment implements View.OnClickListener {
    public String phString;
    private EditText inputverificationcode;
    private Button confirmregister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_registersp, container, false);
        initView(view);
        getTransitiveData();// 获取上一个碎片传递过来的数据
        oncliklinerregister();
        return view;

    }

    public void initView(View view) {
        inputverificationcode = (EditText) view.findViewById(R.id.inputverificationcode);
        confirmregister = (Button) view.findViewById(R.id.confirmregister);
    }

    public void oncliklinerregister() {
        confirmregister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmregister:
                if (!TextUtils.isEmpty(inputverificationcode.getText().toString())) {
                    SMSSDK.submitVerificationCode("86", phString, inputverificationcode.getText().toString());

                } else {
                    Toast.makeText(getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void getTransitiveData() {
        phString = getArguments().getString("key_str");
    }

}
