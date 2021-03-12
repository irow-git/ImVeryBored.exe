package com.fmi.examples.projectfinalexam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameFragment extends Fragment {

    TextView userTextView;
    TextView textView;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String BOOL = "bool";
    boolean userBool;
    boolean bool = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button leadersButton = view.findViewById(R.id.leadersBtn);
        Button mainGameBtn = view.findViewById(R.id.mainGameBtn);
        Button timeTrialBtn = view.findViewById(R.id.timeTrialBtn);
        Button quitBtn = view.findViewById(R.id.quitBtn);
        Button accountBtn = view.findViewById(R.id.accountBtn);
        userTextView = view.findViewById(R.id.userTextView);
        final NavController navigation = Navigation.findNavController(view);
        final GameFragmentArgs args = GameFragmentArgs.fromBundle(getArguments());
        userBool = args.getUserBool();
        userTextView.setText(args.getUserString());

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool = true;
                Context context = getContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, userTextView.getText().toString());
                editor.putBoolean(BOOL, bool);
                editor.apply();
                navigation.navigate(R.id.action_gameFragment_to_homeScreenFragment);
            }
        });

        loadDataBool(getContext());
        if (bool) {
            saveData(getContext());
        }
        loadDataString(getContext());

        leadersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (args.getUserBool()) {
                    Toast.makeText(getActivity(), "Sign In to view the LeaderBoards", Toast.LENGTH_LONG).show();
                } else {
                    navigation.navigate(R.id.action_gameFragment_to_leaderBoardFragment);
                }
            }
        });

        mainGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEnteredUsername = userTextView.getText().toString();
                GameFragmentDirections.ActionGameFragmentToMainGame action = GameFragmentDirections.actionGameFragmentToMainGame();
                action.setUserStringMG(userEnteredUsername);
                action.setUserBoolMG(args.getUserBool());
                navigation.navigate(action);
            }
        });
        timeTrialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEnteredUsername = userTextView.getText().toString();
                GameFragmentDirections.ActionGameFragmentToTimeTrial action = GameFragmentDirections.actionGameFragmentToTimeTrial();
                action.setUserStringTT(userEnteredUsername);
                action.setUserBoolTT(args.getUserBool());
                navigation.navigate(action);
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (args.getUserBool()) {
                    Toast.makeText(getActivity(), "Sign In to access account settings", Toast.LENGTH_LONG).show();
                }
                else{
                    String userEnteredUsername = userTextView.getText().toString();
                    GameFragmentDirections.ActionGameFragmentToAccount action = GameFragmentDirections.actionGameFragmentToAccount();
                    action.setUserStringA(userEnteredUsername);
                    navigation.navigate(action);
                }
            }
        });

        if (userTextView.getText().equals("null")) {
            homeBtn.performClick();
        }
        String settings = getString(R.string.Settings);
        accountBtn.setText(userTextView.getText().toString() + settings);
    }


    private void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, userTextView.getText().toString());
        editor.putBoolean(BOOL, userBool);
        editor.apply();
    }


    public void loadDataString(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userTextView.setText(sharedPreferences.getString(TEXT, "text"));
    }
    public void loadDataBool(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        bool = (sharedPreferences.getBoolean(BOOL, true));
    }
}