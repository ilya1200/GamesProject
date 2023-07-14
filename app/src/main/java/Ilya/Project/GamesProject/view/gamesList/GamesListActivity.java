package Ilya.Project.GamesProject.view.gamesList;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.view.login.LoginActivity;

public class GamesListActivity extends AppCompatActivity {
    GamesListViewModel gamesListViewModel;
    private MaterialTextView gamesUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        MaterialButton logOutBtn = findViewById(R.id.logOutBtn);
        gamesUserName = findViewById(R.id.games_user);

        gamesListViewModel = new ViewModelProvider(this).get(GamesListViewModel.class);
        initObservers();
        gamesListViewModel.displayUserName();


        logOutBtn.setOnClickListener(v -> {
            gamesListViewModel.handleLogOut();
            moveToActivity(new Intent(GamesListActivity.this, LoginActivity.class));
        });

    }

    private void initObservers() {
        gamesListViewModel.username.observe(this, username -> {
            gamesUserName.setText(username);
        });
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}