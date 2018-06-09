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

public class Personresetpwdsp extends Fragment implements View.OnClickListener{
    private EditText resetverificationcode;
    private Button resetpwd;
    private String resetString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_personresetpwdsp, container, false);
        initView(view);
        getTransitiveData();
        listenerreset();
        return view;
    }
    public void listenerreset(){
        resetpwd.setOnClickListener(this);
    }
    public void initView(View view){
        resetverificationcode = (EditText)view.findViewById(R.id.resetverificationcode);
        resetpwd = (Button)view.findViewById(R.id.resetpwd);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.resetpwd:
                if (!TextUtils.isEmpty(resetverificationcode.getText().toString())) {

                    SMSSDK.submitVerificationCode("86", resetString, resetverificationcode.getText().toString());
                    Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }

    private void getTransitiveData() {
        resetString = getArguments().getString("key_str");
    }


}