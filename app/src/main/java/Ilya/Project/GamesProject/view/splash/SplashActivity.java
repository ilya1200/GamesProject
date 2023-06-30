package Ilya.Project.GamesProject.view.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.view.gamesList.GamesListActivity;
import Ilya.Project.GamesProject.view.login.LoginActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        initObservers();

        splashViewModel.handleLogin();
    }

    private void initObservers() {
        splashViewModel.loginLiveData.observe(this, isLoggedInSuccess -> {
            if (isLoggedInSuccess) {
                moveToActivity(new Intent(SplashActivity.this, GamesListActivity.class));
            } else {
                moveToActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}