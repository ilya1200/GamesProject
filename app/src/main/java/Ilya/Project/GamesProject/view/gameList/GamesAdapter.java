package Ilya.Project.GamesProject.view.gameList;

import static Ilya.Project.GamesProject.utils.Constants.GAMES;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.game.Game;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private final List<Game> games;
    private final Context context;
    private final GameItemClickListener gameItemClickListener;


    public GamesAdapter(List<Game> games, Context context, GameItemClickListener gameItemClickListener) {
        this.games = games;
        this.context = context;
        this.gameItemClickListener = gameItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(GAMES, String.format("onBindViewHolder was called, position: %d and viewHolder %s", position, holder.toString()));
        Game game = games.get(holder.getAdapterPosition());
        holder.textViewGameId.setText(game.getId().toString());
        holder.textViewGameType.setText(game.getType());
        holder.textViewUserName.setText(game.getUserFirstName());
        holder.textViewCreationDate.setText(String.valueOf(game.getCreationDate()));
        holder.itemGameParent.setOnClickListener(v -> gameItemClickListener.onItemClicked(game));
    }


    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewGameId;
        public TextView textViewGameType;
        public TextView textViewUserName;
        public TextView textViewCreationDate;
        public LinearLayout itemGameParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGameId = itemView.findViewById(R.id.textViewGameId);
            textViewGameType = itemView.findViewById(R.id.textViewGameType);
            textViewUserName = itemView.findViewById(R.id.textViewUsername);
            textViewCreationDate = itemView.findViewById(R.id.textViewCreationDate);
            itemGameParent = itemView.findViewById(R.id.itemGameParent);
        }
    }
}