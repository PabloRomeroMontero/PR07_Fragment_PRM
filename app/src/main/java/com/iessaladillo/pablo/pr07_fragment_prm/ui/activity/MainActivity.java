package com.iessaladillo.pablo.pr07_fragment_prm.ui.activity;

import android.os.Bundle;

import com.iessaladillo.pablo.pr07_fragment_prm.R;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;
import com.iessaladillo.pablo.pr07_fragment_prm.databinding.ActivityMainBinding;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.EditUserFragment.EditUserFragment;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.listFragment.ListFragment;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.selectAvatarFragment.SelectedAvatarFragment;
import com.iessaladillo.pablo.pr07_fragment_prm.utils.FragmentUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ViewModelActivityMain vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = ViewModelProviders.of(this, new ViewModelFactoryActivityMain(Database.getInstance())).get(ViewModelActivityMain.class);
        // We load initial fragment
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.listFragmentFL,
                    new ListFragment(), ListFragment.class.getSimpleName());
        }
        vm.getEditUserFragmentEvent().observe(this, this::loadEditUserFragment);
        vm.getChangeAvatarFragmentEvent().observe(this, this::loadChangeAvatarFragment);
    }


    public void loadEditUserFragment(Boolean bool) {

        /*El observable se ejecuta cada vez que hay un cmabio en la variable, entonces el primer problemas, algirar y volver a
         construirse la vista vuelve a observar lo que tenia, entonces se ejecuta de nuevo ese metodo cuando no deberia.
         Tambien si es nulo pasa lo mismo, la unica forma de que sea nulo es porque esa variable se ejecuta por segunda
         vez por lo tanto evitamos q se ejecute mas veces de las necesarias.*/

        if (bool != null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.listFragmentFL, new EditUserFragment(), EditUserFragment.class.getSimpleName(), "backstackMain", FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //Despues de ejecutarse lo de arriba, no me vuelvas a observar porque ya me he eejecutado,recupera el LiveData anterior
            //Le cambiamos el valor porq si le creamos un nuevo mutableLiveData ya no estariamos observando al mismo mutableLiveData.
            vm.getEditUserFragmentEvent().setValue(null);
        }
    }

    public void loadChangeAvatarFragment(Boolean bool) {
        /*El observable se ejecuta cada vez que hay un cmabio en la variable, entonces el primer problemas, algirar y volver a
         construirse la vista vuelve a observar lo que tenia, entonces se ejecuta de nuevo ese metodo cuando no deberia.
         Tambien si es nulo pasa lo mismo, la unica forma de que sea nulo es porque esa variable se ejecuta por segunda
         vez por lo tanto evitamos q se ejecute mas veces de las necesarias.*/

        if (bool != null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.listFragmentFL, new SelectedAvatarFragment(), SelectedAvatarFragment.class.getSimpleName(), "backstackMain", FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //Despues de ejecutarse lo de arriba, no me vuelvas a observar porque ya me he eejecutado,recupera el LiveData anterior
            //Le cambiamos el valor porq si le creamos un nuevo mutableLiveData ya no estariamos observando al mismo mutableLiveData.
            vm.getChangeAvatarFragmentEvent().setValue(null);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().findFragmentById(R.id.listFragmentFL).getTag().equals(EditUserFragment.class.getSimpleName())) {
            vm.deleteUserVM();
        }
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.listFragmentFL).getTag().equals(SelectedAvatarFragment.class.getSimpleName())) {
            vm.setIdImagen(false);
            super.onBackPressed();
        }
    }


}