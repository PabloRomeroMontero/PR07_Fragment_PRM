package com.iessaladillo.pablo.pr07_fragment_prm.utils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentUtils {

    private FragmentUtils() {
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).commit();
    }

    public static void addFragment(@NonNull FragmentManager fragmentManager,@IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag){
        fragmentManager.beginTransaction().add(parentResId,fragment,tag);
    }


    @SuppressWarnings("SameParameterValue")
    public static void replaceFragmentAddToBackstack(@NonNull FragmentManager fragmentManager,
                                                     @IdRes int parentResId, @NonNull Fragment fragment, @NonNull String tag,
                                                     @NonNull String backstackTag, int transition) {
        fragmentManager.beginTransaction().replace(parentResId, fragment, tag).setTransition(
                transition).addToBackStack(backstackTag).commit();
    }

}