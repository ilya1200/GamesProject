package Ilya.Project.GamesProject.view.gamesList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.rest.GameResponse;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private final List<GameResponse> games;
    private final Context context;
    private final GameItemClickListener gameItemClickListener;


    public GamesAdapter(List<GameResponse> games, Context context, GameItemClickListener gameItemClickListener) {
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
        GameResponse gameResponse = games.get(holder.getAdapterPosition());
        holder.textViewGameId.setText(gameResponse.getId().toString());
        holder.textViewGameType.setText(gameResponse.getType());
        holder.textViewUserName.setText(gameResponse.getUserFirstName());
        holder.textViewCreationDate.setText(String.valueOf(gameResponse.getCreationDate()));
        holder.itemGameParent.setOnClickListener(v -> gameItemClickListener.onItemClicked(gameResponse));
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