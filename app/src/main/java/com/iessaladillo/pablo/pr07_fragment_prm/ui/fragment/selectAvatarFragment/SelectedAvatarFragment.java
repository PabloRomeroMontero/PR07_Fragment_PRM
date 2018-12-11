package com.iessaladillo.pablo.pr07_fragment_prm.ui.fragment.selectAvatarFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iessaladillo.pablo.pr07_fragment_prm.R;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.Database;
import com.iessaladillo.pablo.pr07_fragment_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr07_fragment_prm.databinding.FragmentChangeAvatarBinding;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.activity.ViewModelActivityMain;
import com.iessaladillo.pablo.pr07_fragment_prm.ui.activity.ViewModelFactoryActivityMain;
import com.iessaladillo.pablo.pr07_fragment_prm.utils.ResourcesUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SelectedAvatarFragment extends Fragment {

    private FragmentChangeAvatarBinding b;
    private ViewModelActivityMain vm;
    private List<Avatar> listCat;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savecInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_change_avatar, container, false);
        vm = ViewModelProviders.of(getActivity(), new ViewModelFactoryActivityMain(Database.getInstance())).get(ViewModelActivityMain.class);
        setHasOptionsMenu(true);
        setupToolbar();
        initView();
        return b.getRoot();
    }




    /****************************************/

    private void initView() {
        listCat = vm.getCatList();
        setAvatarCats();
        if(vm.getIdImagen()) {
            vm.changeAvatar(vm.getUserVM().getAvatar().getId());
            vm.setIdImagen(true);
        }
        selectImageView(null,checkImgView(vm.getAvatar()));

        b.imgAvatar1.setOnClickListener(v -> clickAvatar(b.imgAvatar1,0));
        b.imgAvatar2.setOnClickListener(v -> clickAvatar(b.imgAvatar2,1));
        b.imgAvatar3.setOnClickListener(v -> clickAvatar(b.imgAvatar3,2));
        b.imgAvatar4.setOnClickListener(v -> clickAvatar(b.imgAvatar4,3));
        b.imgAvatar5.setOnClickListener(v -> clickAvatar(b.imgAvatar5,4));
        b.imgAvatar6.setOnClickListener(v -> clickAvatar(b.imgAvatar6,5));
    }

    private void clickAvatar(ImageView imgView, int position){
        selectImageView(checkImgView(vm.getAvatar()),imgView);;
        vm.changeAvatar(listCat.get(position).getId());
    }

    private void selectImageView(ImageView imageViewOld,ImageView imageViewNew) {
        if(imageViewOld!=null)
            imageViewOld.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));

        imageViewNew.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
    }

    private void setAvatarCats() {
        b.imgAvatar1.setImageResource(listCat.get(0).getImageResId());
        b.imgAvatar2.setImageResource(listCat.get(1).getImageResId());
        b.imgAvatar3.setImageResource(listCat.get(2).getImageResId());
        b.imgAvatar4.setImageResource(listCat.get(3).getImageResId());
        b.imgAvatar5.setImageResource(listCat.get(4).getImageResId());
        b.imgAvatar6.setImageResource(listCat.get(5).getImageResId());

        b.lblAvatar1.setText(listCat.get(0).getName());
        b.lblAvatar2.setText(listCat.get(1).getName());
        b.lblAvatar3.setText(listCat.get(2).getName());
        b.lblAvatar4.setText(listCat.get(3).getName());
        b.lblAvatar5.setText(listCat.get(4).getName());
        b.lblAvatar6.setText(listCat.get(5).getName());
    }

    private ImageView checkImgView(Avatar avatar) {
        if (avatar.getName().equals(b.lblAvatar1.getText())) {
            return b.imgAvatar1;
        } else if (avatar.getName().equals(b.lblAvatar2.getText())) {
            return b.imgAvatar2;
        } else if (avatar.getName().equals(b.lblAvatar3.getText())) {
            return b.imgAvatar3;
        } else if (avatar.getName().equals(b.lblAvatar4.getText())) {
            return b.imgAvatar4;
        } else if (avatar.getName().equals(b.lblAvatar5.getText())) {
            return b.imgAvatar5;
        } else {
            return b.imgAvatar6;
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
            getActivity().onBackPressed();
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

    private void save(){
        vm.getUserVM().setAvatar(vm.getAvatar());
        vm.setIdImagen(false);
    }

}
