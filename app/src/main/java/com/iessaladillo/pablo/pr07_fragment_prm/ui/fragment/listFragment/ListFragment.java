package com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.listFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iessaladillo.pablo.pr07_fragment_prm.R;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;
import com.iessaladillo.pablo.pr07_fragment_prm.databinding.FragmentListBinding;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.activity.ViewModelFactoryActivityMain;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.activity.ViewModelActivityMain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

public class ListFragment extends Fragment {
    private FragmentListBinding b;
    private ViewModelActivityMain vm;
    private ListFragmentAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savecInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        vm = ViewModelProviders.of(getActivity(), new ViewModelFactoryActivityMain(Database.getInstance())).get(ViewModelActivityMain.class);
        setupToolbar();
        setupViews();
        observeUsers();
        return b.getRoot();
    }


    private void observeUsers() {
        vm.getUsers(false).observe(this, users -> {
            listAdapter.submitList(users);
            b.noUsers.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews() {
        setupRecyclerView();
        b.fab.setOnClickListener(v -> vm.clickAddNewUser());
        b.noUsers.setOnClickListener(v -> vm.clickAddNewUser());
    }

    private void setupRecyclerView() {
        listAdapter = new ListFragmentAdapter(new OnUserListener() {

            @Override
            public void onItemClickDelete(int position) {
                vm.deleteUser(listAdapter.getItem(position));
            }

            @Override
            public void onItemClickEdit(int position) {
                vm.clickEditUserRV(listAdapter.getItem(position));
            }
        });
        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lstUsers_colums)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
