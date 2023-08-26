package Ilya.Project.GamesProject.view.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.view.gameList.GameListActivity;
import Ilya.Project.GamesProject.view.login.LoginActivity;


public class RegisterActivity extends AppCompatActivity {
    RegisterViewModel registerViewModel;
    private TextInputLayout registerUsernameLayout;
    private TextInputLayout registerPasswordLayout;
    private Button registerBtn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsernameLayout = findViewById(R.id.registerUsernameLayout);
        registerPasswordLayout = findViewById(R.id.registerPasswordLayout);
        TextInputEditText registerUsernameField = findViewById(R.id.registerUsername);
        TextInputEditText registerPasswordField = findViewById(R.id.registerPassword);
        MaterialTextView alreadyHaveUser = findViewById(R.id.alreadyHaveUserLink);
        progressBar = findViewById(R.id.registerProgressBar);
        registerBtn = findViewById(R.id.registerBtn);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        initObservers();

        registerUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.onUsernameChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.onPasswordChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        alreadyHaveUser.setOnClickListener(v -> {
            moveToActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        registerBtn.setOnClickListener(v -> {
            String userName = registerUsernameField.getText().toString();
            String password = registerPasswordField.getText().toString();
            registerViewModel.handleRegister(new User(userName, password));
        });
    }

    private void initObservers() {
        registerViewModel.usernameValid.observe(this, isUsernameValid -> {
            if (isUsernameValid) {
                registerUsernameLayout.setErrorEnabled(false);
                registerUsernameLayout.setError(null);
            } else {
                registerUsernameLayout.setErrorEnabled(true);
                registerUsernameLayout.setError(this.getString(R.string.error_username_is_invalid));
                registerBtn.setEnabled(false);
            }
        });

        registerViewModel.passwordValid.observe(this, isPasswordValid -> {
            if (isPasswordValid) {
                registerPasswordLayout.setErrorEnabled(false);
                registerPasswordLayout.setError(null);
            } else {
                registerPasswordLayout.setErrorEnabled(true);
                registerPasswordLayout.setError(getResources().getString(R.string.error_password_is_invalid));
                registerBtn.setEnabled(false);
            }
        });

        registerViewModel.shouldEnableRegisterBtn.observe(this, shouldEnableRegisterBtn -> registerBtn.setEnabled(shouldEnableRegisterBtn));

        registerViewModel.shouldProgressBarBeVisible.observe(this, shouldProgressBarBeVisible -> {
            progressBar.setVisibility(shouldProgressBarBeVisible ? View.VISIBLE : View.INVISIBLE);
        });

        registerViewModel.registerLiveData.observe(this, isLoggedInSuccess -> {
            if (isLoggedInSuccess) {
                moveToActivity(new Intent(RegisterActivity.this, GameListActivity.class));
            }
        });
        registerViewModel.showErrorMessageToastLiveData.observe(this, errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show());
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}