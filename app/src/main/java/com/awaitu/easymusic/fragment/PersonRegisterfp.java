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

public class PersonRegisterfp extends Fragment implements View.OnClickListener {
    private PersonRegistersp personregistersp;
    private EditText inputphonenumberrg;
    private Button nextstepsrg;
    public String phString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_person_registerfp, container, false);
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


    //向下一页传递数据
    public void transferdata(String str){
        Bundle bundle = new Bundle();
        bundle.putString("key_str",str);
        personregistersp.setArguments(bundle);
    }
}
