package com.fmi.examples.projectfinalexam;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TimeTrial extends Fragment {

    TimeTrialArgs args;
    NavController navigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                navigation.navigate(R.id.action_timeTrial_to_gameFragment);
                if(!args.getUserBoolTT()) {
                    getHashMap();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_trial, container, false);
    }

    int ttv = 60;
    int cPmTV = 0;
    boolean startTimer = true;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
    TextView timerTextView;
    TextView clickPerMinTextView;
    TextView userTextViewTT;
    TextView userBestScoreTextView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button clickTimeTrialBtn = view.findViewById(R.id.clickTimeTrialBtn);
        final Button clickTimeTrialRestartBtn = view.findViewById(R.id.clickTimeTrialRestartBtn);
        args = TimeTrialArgs.fromBundle(getArguments());
        navigation = Navigation.findNavController(view);
        timerTextView = view.findViewById(R.id.timerTextView);
        clickPerMinTextView = view.findViewById(R.id.clickPerMinTextView);
        userBestScoreTextView = view.findViewById(R.id.userBestScoreTextView);
        userTextViewTT = view.findViewById(R.id.userTextViewTT);
        userTextViewTT.setText(args.getUserStringTT());

        final CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                ttv--;
                timerTextView.setText(ttv + "");
            }

            @Override
            public void onFinish() {
                clickTimeTrialBtn.setEnabled(false);
                if(!args.getUserBoolTT()) {
                    getHashMap();
                }
            }
        };

        clickTimeTrialBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (startTimer) {
                    timer.start();
                }
                startTimer = false;
                cPmTV++;
                clickPerMinTextView.setText(cPmTV + "");
                if(!args.getUserBoolTT()) {
                    String val = userBestScoreTextView.getText().toString();
                    int intVal = Integer.parseInt(val);
                    if (intVal <= cPmTV) {
                        userBestScoreTextView.setText(String.valueOf(cPmTV));
                    }
                }
            }
        });

        clickTimeTrialRestartBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ttv = 60;
                cPmTV = 0;
                timerTextView.setText(ttv + "");
                clickPerMinTextView.setText(cPmTV + "");
                clickTimeTrialBtn.setEnabled(true);
                timer.cancel();
                startTimer = true;
            }
        });
        final String userTV = userTextViewTT.getText().toString().trim();
        Query checkUser = reference.orderByChild("userNameClass").equalTo(userTV);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    int highScoreFromDB = dataSnapshot.child(userTV).child("userTimeTrialBestScoreClass").getValue(Integer.class);
                    String val = String.valueOf(highScoreFromDB);
                    userBestScoreTextView.setText(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    private void getHashMap(){
        HashMap hashMap = new HashMap();
        String stringVal1 = userBestScoreTextView.getText().toString();
        int intVal = Integer.parseInt(stringVal1);
        if(intVal>cPmTV) hashMap.put("userTimeTrialBestScoreClass", intVal);
        else hashMap.put("userTimeTrialBestScoreClass", cPmTV);
        String stringVal2 = userTextViewTT.getText().toString();
        reference.child(stringVal2).updateChildren(hashMap);
    }
}