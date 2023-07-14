package Ilya.Project.GamesProject.view.gamesList;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.rest.GameResponse;
import Ilya.Project.GamesProject.utils.Constants;
import Ilya.Project.GamesProject.view.TicTacToe.TicTacToeActivity;
import Ilya.Project.GamesProject.view.login.LoginActivity;

public class GamesListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    GamesListViewModel gamesListViewModel;
    private MaterialTextView gamesUsername;
    private RecyclerView gamesRV;
    private GamesAdapter gamesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton createGameFAB;

    private GameItemClickListener gameItemClickListener = (gameResponse) -> gamesListViewModel.handleGameClick(gameResponse);

    private List<GameResponse> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        MaterialButton logOutBtn = findViewById(R.id.logOutBtn);
        gamesUsername = findViewById(R.id.games_user);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        createGameFAB = findViewById(R.id.createGameFAB);
        swipeRefreshLayout.setOnRefreshListener(this);

        gamesAdapter = new GamesAdapter(games, GamesListActivity.this,gameItemClickListener);
        gamesRV.setAdapter(gamesAdapter);

        gamesListViewModel = new ViewModelProvider(this).get(GamesListViewModel.class);
        initObservers();
        gamesListViewModel.displayUsername();
        gamesListViewModel.handleGetGamesList();


        logOutBtn.setOnClickListener(v -> {
            gamesListViewModel.handleLogOut();
            moveToActivity(new Intent(GamesListActivity.this, LoginActivity.class));
        });

    }

    private void initObservers() {
        gamesListViewModel.username.observe(this, username -> {
            gamesUsername.setText(username);
        });
        gamesListViewModel.gamesList.observe(this, gamesList -> {
            games = gamesList;
            gamesAdapter.notifyDataSetChanged();
        });
        gamesListViewModel.joinGameSuccess.observe(this, joinGameSuccess->{
            if(joinGameSuccess){
                Intent intent = new Intent(GamesListActivity.this, TicTacToeActivity.class);
                intent.putExtra(Constants.EXTRA_IS_GAME_CREATE, false);
                moveToActivity(intent);
            }
        });
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void onRefresh() {
        gamesListViewModel.handleGetGamesList();
    }
}