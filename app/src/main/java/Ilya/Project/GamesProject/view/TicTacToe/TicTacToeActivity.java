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
import Ilya.Project.GamesProject.utils.Constants;
import Ilya.Project.GamesProject.view.gameList.GameListActivity;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {
    private final List<Button> buttons = new ArrayList<>();
    TicTacToeViewModel ticTacToeViewModel;
    private MaterialTextView firstUserTextView;
    private MaterialTextView secondUserTextView;
    private MaterialButton quitGameBtn;
    private UUID gameId;

    @Override
    protected void onPause() {
        super.onPause();
        ticTacToeViewModel.stopPollingGameUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ticTacToeViewModel.startPollingGameUpdates(gameId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticTacToeViewModel.stopPollingGameUpdates();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        if (!getIntent().getExtras().containsKey(Constants.GAME_ID)) {
            Toast.makeText(TicTacToeActivity.this, R.string.internal_error, Toast.LENGTH_LONG).show();
            finish();
        }

        ticTacToeViewModel = new ViewModelProvider(this).get(TicTacToeViewModel.class);
        initObservers();

        gameId = UUID.fromString(getIntent().getStringExtra(Constants.GAME_ID));
        initLayout();
        ticTacToeViewModel.startPollingGameUpdates(gameId);

        quitGameBtn.setOnClickListener(v -> showQuitConfirmationDialog());
    }

    @Override
    public void onBackPressed() {
        showQuitConfirmationDialog();
    }

    private void initObservers() {
        ticTacToeViewModel.showErrorMessageToastLiveData.observe(this, errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show());
        ticTacToeViewModel.gameUpdates.observe(this, game -> {
            firstUserTextView.setText(game.getUserFirstName());
            secondUserTextView.setText(game.getUserSecondName());
            
        });
        ticTacToeViewModel.leaveGameSuccess.observe(this, leaveGameSuccess -> {
            moveToActivity(new Intent(TicTacToeActivity.this, GameListActivity.class));
        });
    }

    private void initLayout() {
        quitGameBtn = findViewById(R.id.quit_game_btn);
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
        }
        setButtonsEnable(false);
    }

    @Override
    public void onClick(View v) {
        String move = (String) v.getTag();

    }

    public void setButtonsEnable(boolean isEnable) {
        for (Button b : buttons) {
            b.setEnabled(isEnable);
        }
    }

    private void moveToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    private void showEndGameDialog(Boolean gameStatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TicTacToeActivity.this);
        String message = "";
        if (gameStatus == null) {
            message = "draw";
        } else if (gameStatus) {
            message = "win";
        } else {
            message = "lose";
        }
        builder.setMessage(message);
        builder.setTitle("Game Ended");
        builder.setCancelable(false);
        builder.setPositiveButton("Go to Games Menu", (dialog, which) -> finish());//todo extract to strings
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showQuitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit the game?")//todo extract to strings
                .setPositiveButton("Yes", (dialog, id) -> ticTacToeViewModel.leaveGame(gameId))
                .setNegativeButton("No", (dialog, id) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}