package com.awaitu.easymusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.awaitu.easymusic.R;


public class Personresetpwdfp extends Fragment implements View.OnClickListener{
    private Personresetpwdsp personresetpwdsp;
    private EditText inputphonenumber;

    private Button nextsteps;
    private String resetString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personresetpwdfp, container, false);
        initview(view);
        oncliklistener();
        return view;
    }
    private void initview(View view){
        inputphonenumber = (EditText)view.findViewById(R.id.inputphonenumber);
        nextsteps = (Button)view.findViewById(R.id.nextsteps);
    }

    private void oncliklistener(){
        nextsteps.setOnClickListener(this);
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.nextsteps:
                if (!TextUtils.isEmpty(inputphonenumber.getText().toString())) {
                    resetString = inputphonenumber.getText().toString();
                    personresetpwdsp = new Personresetpwdsp();
                    transferdata(resetString);
                    replaceFragment(personresetpwdsp);
                } else {
                    Toast.makeText(getContext(), "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }
    //向下一页传递数据
    public void transferdata(String str){
        Bundle bundle = new Bundle();
        bundle.putString("key_str",str);
        personresetpwdsp.setArguments(bundle);
    }
}
