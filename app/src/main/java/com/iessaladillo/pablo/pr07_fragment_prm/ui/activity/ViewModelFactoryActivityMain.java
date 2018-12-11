package com.iessaladillo.pablo.pr07_fragment_prm.ui.activity;

import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactoryActivityMain implements ViewModelProvider.Factory{
    private Database database;

    public ViewModelFactoryActivityMain(Database database) {
        this.database=database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class <T> modelClass){
        return (T) new ViewModelActivityMain(database);
    }
}
