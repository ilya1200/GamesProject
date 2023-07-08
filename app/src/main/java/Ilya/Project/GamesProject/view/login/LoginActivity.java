package Ilya.Project.GamesProject.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.view.gamesList.GamesListActivity;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    private TextInputLayout loginUserNameLayout;
    private TextInputLayout loginPasswordLayout;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUserNameLayout = findViewById(R.id.loginUserNameLayout);
        loginPasswordLayout = findViewById(R.id.loginPasswordLayout);
        TextInputEditText loginUserNameField = findViewById(R.id.loginUserName);
        TextInputEditText loginPasswordField = findViewById(R.id.loginPassword);
        MaterialTextView doNotHaveUser = findViewById(R.id.doNotHaveUser);
        loginBtn = findViewById(R.id.loginBtn);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initObservers();

        loginUserNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.onUserNameChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.onPasswordChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        doNotHaveUser.setOnClickListener(v -> {
            moveToActivity(new Intent(LoginActivity.this, GamesListActivity.class));
        });
    }


    private void initObservers() {

        loginViewModel.usernameValid.observe(this, isUsernameValid -> {
            if (isUsernameValid) {
                loginUserNameLayout.setErrorEnabled(false);
                loginUserNameLayout.setError(null);
            }else{
                loginUserNameLayout.setErrorEnabled(true);
                loginUserNameLayout.setError(this.getString(R.string.error_username_is_invalid));
                loginBtn.setEnabled(false);
            }
        });

        loginViewModel.passwordValid.observe(this, isPasswordValid -> {
            if (isPasswordValid) {
                loginPasswordLayout.setErrorEnabled(false);
                loginPasswordLayout.setError(null);
            } else{
                loginPasswordLayout.setErrorEnabled(true);
                loginPasswordLayout.setError(getResources().getString(R.string.error_password_is_invalid));
                loginBtn.setEnabled(false);
            }
        });

        loginViewModel.shouldEnableLoginBtn.observe(this, shouldEnableLoginBtn -> {
            loginBtn.setEnabled(shouldEnableLoginBtn);
        });
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}