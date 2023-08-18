package Ilya.Project.GamesProject.view.gameList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.gameItem.GameItem;
import Ilya.Project.GamesProject.utils.Constants;
import Ilya.Project.GamesProject.view.TicTacToe.TicTacToeActivity;
import Ilya.Project.GamesProject.view.login.LoginActivity;
import timber.log.Timber;

public class GameListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    GameListViewModel gameListViewModel;
    private MaterialTextView gamesUsername;

    private MaterialTextView gamesUserScore;

    private RecyclerView gamesRV;
    private GamesAdapter gamesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton createGameFAB;

    private final GameItemClickListener gameItemClickListener = (gameId) -> gameListViewModel.handleGameClick(gameId);

    private final List<GameItem> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        MaterialButton logOutBtn = findViewById(R.id.logOutBtn);
        gamesUsername = findViewById(R.id.games_user);
        gamesUserScore = findViewById(R.id.games_user_score);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        createGameFAB = findViewById(R.id.createGameFAB);
        swipeRefreshLayout.setOnRefreshListener(this);
        gamesRV = findViewById(R.id.gamesRV);
        gamesRV.setLayoutManager(new LinearLayoutManager(this));
        gamesAdapter = new GamesAdapter(games, GameListActivity.this, gameItemClickListener);
        gamesRV.setAdapter(gamesAdapter);

        gameListViewModel = new ViewModelProvider(this).get(GameListViewModel.class);
        initObservers();
        gameListViewModel.displayUsername();
        gameListViewModel.handleGetGameList();
        gameListViewModel.handleGetUserScore();

        logOutBtn.setOnClickListener(v -> {
            gameListViewModel.handleLogOut();
            moveToActivity(new Intent(GameListActivity.this, LoginActivity.class));
        });

        createGameFAB.setOnClickListener(v -> {
            gameListViewModel.handleCreateGame();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initObservers() {
        gameListViewModel.username.observe(this, username -> {
            gamesUsername.setText(username);
        });
        gameListViewModel.gameList.observe(this, gameList -> {
            games.clear();
            games.addAll(gameList);
            Timber.d("GameList modified: %s", games.size());
            gamesAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
        gameListViewModel.joinGameSuccess.observe(this, gameId -> {
            if (gameId != null) {
                Intent intent = new Intent(GameListActivity.this, TicTacToeActivity.class);
                intent.putExtra(Constants.GAME_ID_EXTRA, gameId.toString());
                moveToActivity(intent);
            }
        });
        gameListViewModel.showErrorMessageToastLiveData.observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        });

        gameListViewModel.createGameSuccess.observe(this, gameId -> {
            if (gameId != null) {
                Intent intent = new Intent(GameListActivity.this, TicTacToeActivity.class);
                intent.putExtra(Constants.GAME_ID_EXTRA, gameId.toString());
                moveToActivity(intent);
            }
        });
        gameListViewModel.userScoreMutableLiveData.observe(this, userScore -> {
            String user_score = String.format(Locale.ENGLISH, getResources().getString(R.string.user_score), userScore.getWins(), userScore.getLosses());
            gamesUserScore.setText(user_score);
        });
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void onRefresh() {
        gameListViewModel.handleGetGameList();
    }
}