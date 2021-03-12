package com.fmi.examples.projectfinalexam;

import android.nfc.FormatException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class registrationFragment2 extends Fragment {

    EditText userName, userEmail, userPass;
    Button regButton;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration2, container, false);
    }

    private Boolean validateUserName() {
        String userNameClass = userName.getText().toString();
        if (userNameClass.isEmpty()) {
            userName.setError("Field can't be empty");
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }
    private Boolean validateUserEmail() {
        String userEmailClass = userEmail.getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (userEmailClass.isEmpty()) {
            userEmail.setError("Field can't be empty");
            return false;
        } else if (!userEmailClass.matches(emailPattern)) {
            userEmail.setError("Invalid Email");
            return false;
        } else {
            userEmail.setError(null);
            return true;
        }
    }
    private Boolean validateUserPass()
    {
        String userPassClass = userPass.getText().toString();
        if(userPassClass.isEmpty()){
            userPass.setError("Field can't be empty");
            return false;
        }
        else {
            userPass.setError(null);
            return true;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.nameTextViewRegister);
        userEmail = view.findViewById(R.id.emailTextViewRegister);
        userPass = view.findViewById(R.id.passTextViewRegister);
        regButton = view.findViewById(R.id.registerButton2);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validateUserEmail() | !validateUserPass()){
                    return;
                }
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                String userNameClass = userName.getText().toString();
                String userEmailClass = userEmail.getText().toString();
                String userPassClass = userPass.getText().toString();
                int userHighScoreClass = 0;
                int userTimeTrialBestScoreClass = 0;
                UserClass userClass = new UserClass(userNameClass, userEmailClass, userPassClass, userHighScoreClass, userTimeTrialBestScoreClass);
                reference.child(userNameClass).setValue(userClass);
                Toast.makeText(getActivity(),"User has been registered", Toast.LENGTH_LONG).show();
            }
        });

    }
}