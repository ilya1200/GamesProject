package Ilya.Project.GamesProject.view.TicTacToe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.game.GameStatus;
import Ilya.Project.GamesProject.utils.Constants;
import Ilya.Project.GamesProject.view.gameList.GameListActivity;
import timber.log.Timber;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {
    private final List<Button> buttons = new ArrayList<>();
    TicTacToeViewModel ticTacToeViewModel;
    private MaterialTextView firstUserTextView;
    private MaterialTextView firstUserWins;
    private MaterialTextView firstUserLosses;
    private MaterialTextView secondUserTextView;
    private MaterialTextView secondUserWins;
    private MaterialTextView secondUserLosses;
    private UUID gameId;

    @Override
    protected void onPause() {
        Timber.d("onPause");
        ticTacToeViewModel.stopPollingGameUpdates();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Timber.d("onResume");
        ticTacToeViewModel.startPollingGameUpdates(gameId);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Timber.d("onDestroy");
        ticTacToeViewModel.stopPollingGameUpdates();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        firstUserWins = findViewById(R.id.tic_tac_toe_first_user_score_wins);
        firstUserLosses = findViewById(R.id.tic_tac_toe_first_user_score_losses);

        secondUserWins = findViewById(R.id.tic_tac_toe_second_user_score_wins);
        secondUserLosses = findViewById(R.id.tic_tac_toe_second_user_score_losses);

        ticTacToeViewModel = new ViewModelProvider(this).get(TicTacToeViewModel.class);

        if (getIntent().getExtras() == null || !getIntent().getExtras().containsKey(Constants.GAME_ID_EXTRA)) {
            Toast.makeText(TicTacToeActivity.this, R.string.internal_error, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        gameId = UUID.fromString(getIntent().getStringExtra(Constants.GAME_ID_EXTRA));
        initLayout();
        initObservers();
    }

    @Override
    public void onBackPressed() {
        showQuitConfirmationDialog();
    }

    private void initObservers() {
        ticTacToeViewModel.showErrorMessageToastLiveData.observe(this, errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show());
        ticTacToeViewModel.gameUpdatesLiveData.observe(this, game -> {
            GameStatus gameStatus = game.getGameStatus();
            firstUserTextView.setText(game.getUserFirstName());
            secondUserTextView.setText((gameStatus == GameStatus.WAITING_TO_START) ? getString(R.string.start_game_waiting_for_second_user) : game.getUserSecondName());
            if (ticTacToeViewModel.isTechnicalGameFinish(gameStatus) || ticTacToeViewModel.isLegitGameFinish(gameStatus)) {
                ticTacToeViewModel.stopPollingGameUpdates();
                showEndGameDialog(game);

                if (ticTacToeViewModel.isLegitGameFinish(gameStatus)) {
                    ticTacToeViewModel.updateBoard(game, buttons);
                }
            } else {
                ticTacToeViewModel.updateBoard(game, buttons);
            }
        });
    }

    private void initLayout() {
        MaterialButton quitGameBtn = findViewById(R.id.quit_game_btn);
        quitGameBtn.setOnClickListener(v -> showQuitConfirmationDialog());
        firstUserTextView = findViewById(R.id.first_user);
        secondUserTextView = findViewById(R.id.second_user);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);

        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);

        for (Button b : buttons) {
            b.setOnClickListener(this);
            b.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        String move = (String) v.getTag();
        ticTacToeViewModel.makeMove(gameId, move);
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    private void showEndGameDialog(Game game) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TicTacToeActivity.this)
                .setTitle(getString(R.string.end_game_dialog_title))
                .setMessage(ticTacToeViewModel.getEndGameMessage(game))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.end_game_dialog_go_to_menu_button), (dialog, which) -> {
                    ticTacToeViewModel.leaveGame(gameId);
                    moveToActivity(new Intent(TicTacToeActivity.this, GameListActivity.class));
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showQuitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.quit_dialog_message))
                .setTitle(getString(R.string.quit_dialog_title))
                .setPositiveButton(getString(R.string.quit_dialog_positive_button), (dialog, id) -> {
                    ticTacToeViewModel.leaveGame(gameId);
                    moveToActivity(new Intent(TicTacToeActivity.this, GameListActivity.class));
                })
                .setNegativeButton(getString(R.string.quit_dialog_negative_button), (dialog, id) -> {
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}