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
import android.text.Editable;
import android.text.TextWatcher;
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

public class MainGame extends Fragment {


    public MainGame() {
        // Required empty public constructor
    }


    int numberOfClicks = 0;
    int upgOne = 0, upgTwo = 0, upgThree = 0, upgFour = 0;
    int alt1 = 1;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
    MainGameArgs args;
    NavController navigation;
    TextView userStringMainGame;
    TextView userHighScoreTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                navigation.navigate(R.id.action_mainGame_to_gameFragment);
                if(!args.getUserBoolMG() && numberOfClicks>0) {
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
        return inflater.inflate(R.layout.fragment_main_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button clickBtn = view.findViewById(R.id.clickBtn);
        final Button upgrade1Button = view.findViewById(R.id.button);
        final Button upgrade2Button = view.findViewById(R.id.button2);
        final Button upgrade3Button = view.findViewById(R.id.button3);
        final Button upgrade4Button = view.findViewById(R.id.button4);
        final TextView numberOfClicksText =view.findViewById(R.id.numberOfClicksText);
        final TextView upgradeTextView1 = view.findViewById(R.id.upgradeTexView10);
        final TextView upgradeTextView2 = view.findViewById(R.id.upgradeTexView11);
        final TextView upgradeTextView3 = view.findViewById(R.id.upgradeTexView12);
        final TextView upgradeTextView4 = view.findViewById(R.id.upgradeTexView13);
        args = MainGameArgs.fromBundle(getArguments());
        navigation = Navigation.findNavController(view);
        userHighScoreTextView = view.findViewById(R.id.userHighScoreTextView);
        userStringMainGame = view.findViewById(R.id.userTextViewMG);
        userStringMainGame.setText(args.getUserStringMG());
        final CountDownTimer upgradeTimer1 = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                numberOfClicks++;
                numberOfClicksText.setText(numberOfClicks + "");
            }

            @Override
            public void onFinish() {
                start();
            }
        };
        final CountDownTimer upgradeTimer2 = new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                numberOfClicks++;
                numberOfClicksText.setText(numberOfClicks + "");
            }

            @Override
            public void onFinish() {
                start();
            }
        };
        final CountDownTimer upgradeTimer3 = new CountDownTimer(500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                numberOfClicks++;
                numberOfClicksText.setText(numberOfClicks + "");
            }

            @Override
            public void onFinish() {
                start();
            }
        };
        upgrade1Button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                upgradeTimer1.start();
                numberOfClicks--;
                upgOne++;
                upgradeTextView1.setText("x" + upgOne);
                numberOfClicks = numberOfClicks - 10;
                if (numberOfClicks < 10) {
                    upgrade1Button.setEnabled(false);
                }
                numberOfClicksText.setText(numberOfClicks + "");
            }
        });
        upgrade2Button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                upgradeTimer2.start();
                numberOfClicks--;
                upgTwo++;
                upgradeTextView2.setText("x" + upgTwo);
                numberOfClicks = numberOfClicks - 40;
                if (numberOfClicks < 40) {
                    upgrade1Button.setEnabled(false);
                }
                numberOfClicksText.setText(numberOfClicks + "");
            }
        });
        upgrade3Button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                upgradeTimer3.start();
                numberOfClicks--;
                upgThree++;
                upgradeTextView3.setText("x" + upgThree);
                numberOfClicks = numberOfClicks - 100;
                if (numberOfClicks < 100) {
                    upgrade1Button.setEnabled(false);
                }
                numberOfClicksText.setText(numberOfClicks + "");
            }
        });
        upgrade4Button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                alt1++;
                upgFour++;
                upgradeTextView4.setText("x" + upgFour);
                numberOfClicks = numberOfClicks - 100;
                numberOfClicksText.setText(numberOfClicks + "");
                if (numberOfClicks < 100) {
                    upgrade1Button.setEnabled(false);
                }
                numberOfClicksText.setText(numberOfClicks + "");
            }
        });
        clickBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                numberOfClicks = numberOfClicks + alt1;
                numberOfClicksText.setText(numberOfClicks + "");
                if(!args.getUserBoolMG()) {
                    String val = userHighScoreTextView.getText().toString();
                    int intVal = Integer.parseInt(val);
                    if (intVal <= numberOfClicks) {
                        userHighScoreTextView.setText(String.valueOf(numberOfClicks));
                    }
                }
            }
        });

        numberOfClicksText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (numberOfClicks < 10) upgrade1Button.setEnabled(false);
                else if (numberOfClicks >= 10) upgrade1Button.setEnabled(true);
                if (numberOfClicks < 40) upgrade2Button.setEnabled(false);
                else if (numberOfClicks >= 40) upgrade2Button.setEnabled(true);
                if (numberOfClicks < 100) {
                    upgrade3Button.setEnabled(false);
                    upgrade4Button.setEnabled(false);
                } else if (numberOfClicks >= 100) {
                    upgrade3Button.setEnabled(true);
                    upgrade4Button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        userHighScoreTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final String userTV = userStringMainGame.getText().toString().trim();
        Query checkUser = reference.orderByChild("userNameClass").equalTo(userTV);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    int highScoreFromDB = dataSnapshot.child(userTV).child("userHighScoreClass").getValue(Integer.class);
                    String val = String.valueOf(highScoreFromDB);
                    userHighScoreTextView.setText(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getHashMap(){
        HashMap hashMap = new HashMap();
        String stringVal1 = userHighScoreTextView.getText().toString();
        int intVal = Integer.parseInt(stringVal1);
        if(intVal>numberOfClicks) hashMap.put("userHighScoreClass", intVal);
        else hashMap.put("userHighScoreClass", numberOfClicks);
        String stringVal2 = userStringMainGame.getText().toString();
        reference.child(stringVal2).updateChildren(hashMap);
    }
}