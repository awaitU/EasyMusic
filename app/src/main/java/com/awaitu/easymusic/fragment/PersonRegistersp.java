package com.awaitu.easymusic.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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


public class PersonRegistersp extends Fragment implements View.OnClickListener {
    public String phString;
    private EditText inputverificationcode;
    private Button confirmregister;
    private EditText inputpwd;
    private EditText inputusername;

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
        inputpwd = (EditText) view.findViewById(R.id.inputpwd);
        inputusername = (EditText) view.findViewById(R.id.inputusername);
        confirmregister = (Button) view.findViewById(R.id.confirmregister);
    }

    public void oncliklinerregister() {
        confirmregister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmregister:
                if (!TextUtils.isEmpty(inputverificationcode.getText().toString())) {
                    SharedPreferences sp = getActivity().getSharedPreferences("userAccount", Context.MODE_PRIVATE);
                    String userPassword = inputpwd.getText().toString();
                    String username = inputusername.getText().toString();
                    if (TextUtils.isEmpty(username) | TextUtils.isEmpty(userPassword)) {
                        Toast.makeText(getActivity(), "请您输入正确的信息", Toast.LENGTH_SHORT).show();
                    } else {
                        sp.edit().putString("userPassword", userPassword).apply();
                        sp.edit().putString("userName", username).apply();
                    }

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
