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
import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.view.gameList.GameListActivity;
import Ilya.Project.GamesProject.view.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    private TextInputLayout loginUsernameLayout;
    private TextInputLayout loginPasswordLayout;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameLayout = findViewById(R.id.loginUsernameLayout);
        loginPasswordLayout = findViewById(R.id.loginPasswordLayout);
        TextInputEditText loginUsernameField = findViewById(R.id.loginUsername);
        TextInputEditText loginPasswordField = findViewById(R.id.loginPassword);
        MaterialTextView doNotHaveUser = findViewById(R.id.doNotHaveUser);
        loginBtn = findViewById(R.id.loginBtn);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initObservers();

        loginUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.onUsernameChanged(s.toString());
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
            moveToActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        loginBtn.setOnClickListener(v -> {
            String userName = loginUsernameField.getText().toString();
            String password = loginPasswordField.getText().toString();
            loginViewModel.handleLogin(new User(userName, password));
        });
    }


    private void initObservers() {

        loginViewModel.usernameValid.observe(this, isUsernameValid -> {
            if (isUsernameValid) {
                loginUsernameLayout.setErrorEnabled(false);
                loginUsernameLayout.setError(null);
            } else {
                loginUsernameLayout.setErrorEnabled(true);
                loginUsernameLayout.setError(this.getString(R.string.error_username_is_invalid));
                loginBtn.setEnabled(false);
            }
        });

        loginViewModel.passwordValid.observe(this, isPasswordValid -> {
            if (isPasswordValid) {
                loginPasswordLayout.setErrorEnabled(false);
                loginPasswordLayout.setError(null);
            } else {
                loginPasswordLayout.setErrorEnabled(true);
                loginPasswordLayout.setError(getResources().getString(R.string.error_password_is_invalid));
                loginBtn.setEnabled(false);
            }
        });

        loginViewModel.shouldEnableLoginBtn.observe(this, shouldEnableLoginBtn -> {
            loginBtn.setEnabled(shouldEnableLoginBtn);
        });

        loginViewModel.loginLiveData.observe(this, isLoggedInSuccess -> {
            if (isLoggedInSuccess) {
                moveToActivity(new Intent(LoginActivity.this, GameListActivity.class));
            }
        });
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}