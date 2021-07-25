package com.example.pema_projekt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapter extends FragmentStateAdapter {

    private boolean isGoogle;
    public FragmentAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle, boolean isGoogle) {
        super(fragmentManager, lifecycle);
        this.isGoogle = isGoogle;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new GroupFragment(isGoogle);
        }
        return new ContactFragment(isGoogle);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
