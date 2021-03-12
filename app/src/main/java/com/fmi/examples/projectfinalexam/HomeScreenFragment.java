package com.fmi.examples.projectfinalexam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeScreenFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                System.exit(0);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button guestBtn = view.findViewById(R.id.guestBtn);
        Button regBtn = view.findViewById(R.id.regBtn);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) guestBtn.getBackground();
        animationDrawable1.setEnterFadeDuration(2000);
        animationDrawable1.setExitFadeDuration(4000);
        animationDrawable1.start();
        AnimationDrawable animationDrawable2 = (AnimationDrawable) regBtn.getBackground();
        animationDrawable2.setEnterFadeDuration(2000);
        animationDrawable2.setExitFadeDuration(4000);
        animationDrawable2.start();
        final NavController navigation = Navigation.findNavController(view);

        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreenFragmentDirections.ActionHomeScreenFragmentToGameFragment action = HomeScreenFragmentDirections.actionHomeScreenFragmentToGameFragment();
                action.setUserBool(true);
                Random rand = new Random();
                action.setUserString("guest" + rand.nextInt(9999));
                navigation.navigate(action);
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigation.navigate(R.id.action_homeScreenFragment_to_registrationFragment);
            }
        });
    }
}