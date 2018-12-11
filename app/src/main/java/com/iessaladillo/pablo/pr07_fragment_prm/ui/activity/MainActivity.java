package com.iessaladillo.pablo.pr07_fragment_prm.ui.activity;

import android.os.Bundle;

import com.iessaladillo.pablo.pr07_fragment_prm.R;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;
import com.iessaladillo.pablo.pr07_fragment_prm.databinding.ActivityMainBinding;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.EditUserFragment.EditUserFragment;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.listFragment.ListFragment;
import com.iessaladillo.pablo.pr07_fragment_prm.utils.FragmentUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ViewModelActivityMain vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_main);
        vm = ViewModelProviders.of(this, new ViewModelFactoryActivityMain(Database.getInstance())).get(ViewModelActivityMain.class);
        // We load initial fragment
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.listFragmentFL,
                    new ListFragment(), ListFragment.class.getSimpleName());
        }
        vm.getAddNewuserButtonListener().observe(this, v -> addNewUser());
    }


    public void addNewUser() {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(),R.id.listFragmentFL,new EditUserFragment(), EditUserFragment.class.getSimpleName(),"backstackMain",FragmentTransaction.TRANSIT_FRAGMENT_FADE);
       //Despues de ejecutarse lo de arriba, no me vuelvas a observar porque ya me he eejecutado,recupera el LiveData anterior
        vm.setAddNewuserButtonListener(new MutableLiveData<>());
    }

    public void editUser() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}