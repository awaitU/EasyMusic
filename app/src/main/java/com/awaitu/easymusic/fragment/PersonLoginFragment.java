package com.awaitu.easymusic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.awaitu.easymusic.R;


public class PersonLoginFragment extends Fragment implements View.OnClickListener {
    private PersonHomeFragment personHomeFragment ;
    private Personresetpwdfp personresetpwdfp ;
    private PersonRegisterfp personregisterfp;
    private EditText number;
    private EditText password;
    private TextView forgivepassword;
    private TextView register;
    private Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_person, null);
        initview(view);
        registerlistener();
        return view;
    }

    public void initview(View view) {
        number = (EditText) view.findViewById(R.id.phonenumber);
        password = (EditText) view.findViewById(R.id.inputpassword);
        forgivepassword = (TextView) view.findViewById(R.id.forgivepws);
        register = (TextView) view.findViewById(R.id.register);
        login = (Button) view.findViewById(R.id.loginbuttontext);
    }

    public void registerlistener() {
        forgivepassword.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }
    private void judgelogin(){

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
            case R.id.forgivepws:
                personresetpwdfp = new Personresetpwdfp();
                replaceFragment(personresetpwdfp);
                break;
            case R.id.register:
                personregisterfp = new PersonRegisterfp();
                replaceFragment(personregisterfp);
                break;
            case R.id.loginbuttontext:
//                judgelogin();
                personHomeFragment = new PersonHomeFragment();
                replaceFragment(personHomeFragment);
                break;
            default:
                break;
        }
    }




}
