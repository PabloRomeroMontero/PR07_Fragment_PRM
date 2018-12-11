package com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.EditUserFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.iessaladillo.pablo.pr07_fragment_prm.R;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;
import com.iessaladillo.pablo.pr07_fragment_prm.databinding.FragmentEditUserBinding;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.activity.ViewModelActivityMain;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.activity.ViewModelFactoryActivityMain;
import com.iessaladillo.pablo.pr07_fragment_prm.utils.ValidationUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class EditUserFragment extends Fragment {
    private FragmentEditUserBinding b;
    private ViewModelActivityMain vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savecInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_user, container, false);
        vm = ViewModelProviders.of(getActivity(), new ViewModelFactoryActivityMain(Database.getInstance())).get(ViewModelActivityMain.class);
        setHasOptionsMenu(true);
        setupToolbar();
        initView();
        return b.getRoot();
    }

    private void initView() {
        initUser();

        b.include.imgWeb.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH)
                    .putExtra(SearchManager.QUERY, b.include.txtWeb.getText().toString());


            if (validateWhitIcon(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()))
                    && isAvailable(getContext(), intent))
                startActivity(intent);


        });
        b.include.imgAddress.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + b.include.txtAddress.getText().toString()));

            if (validateWhitIcon(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, !TextUtils.isEmpty(b.include.txtAddress.getText()))
                    && isAvailable(getContext(), intent))
                startActivity(intent);

        });
        b.include.imgPhonenumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + b.include.txtPhonenumber.getText().toString()));

            if (validateWhitIcon(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber,
                    ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString())) &&
                    isAvailable(getContext(), intent))
                startActivity(intent);

        });
        b.include.imgEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO)
                    .setData(Uri.parse("mailto:" + b.include.txtEmail.getText().toString()));

            if (validateWhitIcon(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()))
                    && isAvailable(getContext(), intent))
                startActivity(intent);

        });
        b.imgAvatarMain.setOnClickListener(v -> {
            //Llama al observable del Main activity que inicia el fragmento
            vm.getChangeAvatarFragmentEvent().setValue(true);
        });


        b.include.txtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblAddress.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblAddress.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblName.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblName.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblPhonenumber.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblPhonenumber.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtWeb.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblWeb.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblWeb.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblEmail.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblEmail.setTypeface(Typeface.DEFAULT);
        });


        b.include.txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.getUserVM().setWeb(b.include.txtWeb.getText().toString());
                validateWhitIcon(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.getUserVM().setName(b.include.txtName.getText().toString());
                validateWhitoutIcon(b.include.txtName, b.include.lblName, !TextUtils.isEmpty(b.include.txtName.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.getUserVM().setAddress(b.include.txtAddress.getText().toString());
                validateWhitIcon(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, !TextUtils.isEmpty(b.include.txtAddress.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateWhitIcon(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber, ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(b.include.txtPhonenumber.getText().toString())) {
                    vm.getUserVM().setNumber(0);
                } else {
                    vm.getUserVM().setNumber(Integer.parseInt(b.include.txtPhonenumber.getText().toString()));
                }

            }
        });
        b.include.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.getUserVM().setEmail(b.include.txtEmail.getText().toString());
                validateWhitIcon(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return false;
        });


    }

    private void initUser() {
            b.include.txtName.setText(vm.getUserVM().getName());
            b.lblAvatar.setText(vm.getUserVM().getAvatar().getName());
            b.include.txtAddress.setText(vm.getUserVM().getAddress());
            b.include.txtEmail.setText(vm.getUserVM().getEmail());
            b.include.txtWeb.setText(vm.getUserVM().getWeb());
            b.include.txtPhonenumber.setText(String.valueOf(vm.getUserVM().getNumber()));
            b.imgAvatarMain.setImageResource(vm.getUserVM().getAvatar().getImageResId());

    }

    //Metodo intent de los iconos
    private static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> appList =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return appList.size() > 0;
    }

    private boolean validateWhitoutIcon(EditText editText, TextView textView, boolean validate) {

        if (!validate) {
            textView.setEnabled(false);
            editText.setError(getString(R.string.main_invalid_data));
        } else {
            textView.setEnabled(true);
            editText.setError(null);
        }
        return validate;
    }


    private boolean validateWhitIcon(EditText editText, ImageView imgView, TextView textView, boolean validate) {
        if (!validate) {
            imgView.setEnabled(false);
            textView.setEnabled(false);
            editText.setError(getString(R.string.main_invalid_data));
        } else {
            imgView.setEnabled(true);
            textView.setEnabled(true);
            editText.setError(null);
        }
        return validate;
    }


    private boolean validateAll() {
        boolean result = false;
        boolean address = validateWhitIcon(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, !TextUtils.isEmpty(b.include.txtAddress.getText()));
        boolean email = validateWhitIcon(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()));
        boolean name = validateWhitoutIcon(b.include.txtName, b.include.lblName, !TextUtils.isEmpty(b.include.txtName.getText()));
        boolean phoneNumber = validateWhitIcon(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber, ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString()));
        boolean web = validateWhitIcon(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()));

        if (address && name && email && phoneNumber && web) {
            result = true;
        }
        return result;
    }


    private void save() {
        if (validateAll()) {
            Snackbar.make(b.include.txtWeb, getString(R.string.main_saved_succesfully), LENGTH_SHORT).show();
            vm.deleteUserVM();
        } else {
            Snackbar.make(b.include.txtWeb, getString(R.string.main_error_saving), LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
