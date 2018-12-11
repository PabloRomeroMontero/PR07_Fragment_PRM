package com.iessaladillo.pablo.pr07_fragment_prm.ui.activity;

import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelActivityMain extends ViewModel {
    private Database database;
    private Avatar avatar;
    private User userIntent;
    private LiveData<List<User>> users = null;


    //Creamos todos estos booleans por boton para notificar pulsaciones en el mismo.
    private MutableLiveData<Boolean> addNewuserButtonListener = new MutableLiveData<>();

    public ViewModelActivityMain(Database database) {
        this.database = database;
    }

    public MutableLiveData<Boolean> getAddNewuserButtonListener() {
        return addNewuserButtonListener;
    }

    public void setAddNewuserButtonListener(MutableLiveData<Boolean> addNewuserButtonListener) {
        this.addNewuserButtonListener = addNewuserButtonListener;
    }

    public void addUser(User user) {
        database.addUser(user);
    }

    public void deleteUser(User user) {
        database.deleteUser(user);
    }

    public void editUser(User newUser, User oldUser) {
        oldUser.setAvatar(newUser.getAvatar());
        oldUser.setNumber(newUser.getNumber());
        oldUser.setWeb(newUser.getWeb());
        oldUser.setName(newUser.getName());
        oldUser.setAddress(newUser.getAddress());
        oldUser.setEmail(newUser.getEmail());
    }

    public LiveData<List<User>> getUsers(boolean forceLoad) {
        if (users == null || forceLoad)
            users = database.getUsers();
        return users;

    }

    public void addNewUser() {
        addNewuserButtonListener.postValue(true);
    }

    public void editUser(User item, int position) {

    }

    /*************************Profile User****************************************/



    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = database.getDefaultAvatar();
        }

        return avatar;
    }

    public User getUserIntent() {
        if(userIntent == null){
            setUser(new User(database.getDefaultAvatar(),"hola","","",0,""));
        }


        return userIntent;
    }

    public void changeAvatar(long id) {
        if (avatar == null) {
            avatar = database.getDefaultAvatar();
        } else {
            avatar = database.queryAvatar(id);
        }

    }
    public void setUser(User user) {
        userIntent = user;
    }
}