package com.awaitu.easymusic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.awaitu.easymusic.R;

public class PersonRegisterfp extends Fragment implements View.OnClickListener {
    private PersonRegistersp personregistersp;
    private EditText inputphonenumberrg;
    private Button nextstepsrg;
    public String phString;
    private static String APPKEY = "14ce7f774ef10";
    private static String APPSECRET = "04e0f08d04a22edd307247a4e947c88d";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_person_registerfp, container, false);
        SMSSDK.initSDK(getActivity(), APPKEY, APPSECRET);
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
        initview(view);
        oncliklistener();
        return view;
    }

    private void initview(View view) {
        inputphonenumberrg = (EditText) view.findViewById(R.id.inputphonenumberrg);
        nextstepsrg = (Button) view.findViewById(R.id.nextstepsrg);
    }

    private void oncliklistener() {
        nextstepsrg.setOnClickListener(this);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextstepsrg:
                if (!TextUtils.isEmpty(inputphonenumberrg.getText().toString())) {
                    SMSSDK.getVerificationCode("86", inputphonenumberrg.getText().toString());
                    phString = inputphonenumberrg.getText().toString();
                    Toast.makeText(getContext(),phString, Toast.LENGTH_SHORT).show();
                    personregistersp = new PersonRegistersp();
                    transferdata(phString);
                    replaceFragment(personregistersp);
                } else {
                    Toast.makeText(getContext(), "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        Toast.makeText(getContext(), "依然走短信验证", Toast.LENGTH_SHORT).show();
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
                    Toast.makeText(getContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.RESULT_ERROR) {
                    Toast.makeText(getContext(), "------", Toast.LENGTH_SHORT).show();
                }
            } else {
                ((Throwable) data).printStackTrace();
                Toast.makeText(getContext(), "错误" + data, Toast.LENGTH_SHORT).show();
            }

        }

    };
    //向下一页传递数据
    public void transferdata(String str){
        Bundle bundle = new Bundle();
        bundle.putString("key_str",str);
        personregistersp.setArguments(bundle);
    }
}
