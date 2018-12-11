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
    private User userVM;
    private User userToEdit;
    private LiveData<List<User>> users = null;
    private boolean idImagen=false;


    //Creamos todos estos booleans por boton para notificar pulsaciones en el mismo.
    private MutableLiveData<Boolean> editUserFragmentEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> changeAvatarFragmentEvent = new MutableLiveData<>();
    private boolean editable = false;

    public ViewModelActivityMain(Database database) {
        this.database = database;
    }

    public MutableLiveData<Boolean> getEditUserFragmentEvent() {
        return editUserFragmentEvent;
    }

    public void addUser(User user) {
        database.addUser(user);
    }

    public void deleteUser(User user) {
        database.deleteUser(user);
    }

    public void copyUser(User userToEdit) {
        userVM = new User( userToEdit.getAvatar(),userToEdit.getName(),userToEdit.getEmail(),userToEdit.getAddress(),userToEdit.getNumber(),userToEdit.getWeb());
    }



    public LiveData<List<User>> getUsers(boolean forceLoad) {
        if (users == null || forceLoad)
            users = database.getUsers();
        return users;

    }

    public void clickAddNewUser() {
        setEditable(false);
        editUserFragmentEvent.postValue(true);
    }

    public void setUserToEdit(User userToEdit) {
        this.userToEdit = userToEdit;
    }

    public void saveUser(User newUser){
        userToEdit.setAvatar(newUser.getAvatar());
        userToEdit.setNumber(newUser.getNumber());
        userToEdit.setWeb(newUser.getWeb());
        userToEdit.setName(newUser.getName());
        userToEdit.setAddress(newUser.getAddress());
        userToEdit.setEmail(newUser.getEmail());
    }

    public void clickEditUserRV(User user) {
        setEditable(true);
        userToEdit =user;
        copyUser(userToEdit);
        this.editUserFragmentEvent.setValue(false);
    }
    public MutableLiveData<Boolean> getChangeAvatarFragmentEvent() {
        return changeAvatarFragmentEvent;
    }


    /*************************Profile User****************************************/
    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = database.getDefaultAvatar();
        }
        return avatar;
    }
    public User getUserVM() {
        if(userVM == null){
            setUserVM(new User(database.getDefaultAvatar(),"","","",0,""));
        }
        return userVM;
    }


    public void changeAvatar(long id) {
        if (avatar == null) {
            avatar = database.getDefaultAvatar();
        } else {
            avatar = database.queryAvatar(id);
        }

    }

    public void setUserVM(User userVM) {
        this.userVM = userVM;
    }

    public void deleteUserVM(){
        userVM =null;
    }
    /***************************Change Avatar*********************************/

    public List<Avatar> getCatList(){
        return database.queryAvatars();
    }

    public boolean getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(boolean idImagen) {
        this.idImagen = idImagen;
    }

    public void setEditable(boolean editable){
        this.editable=editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public User getUserToEdit() {
        return userToEdit;
    }
}