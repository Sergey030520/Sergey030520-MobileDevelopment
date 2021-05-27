package ru.mirea.makarov.mireaproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<History> histories;

    public HistoryAdapter(Context context, List<History> copy_history) {
        this.histories = copy_history;
        this.inflater = LayoutInflater.from(context);
    }
    @NotNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        History history = histories.get(position);
        holder.nameHistory.setText(history.getName_history());
        holder.content_history.setText(history.getContent_history());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameHistory;
        final TextView content_history;

        ViewHolder(View view) {
            super(view);
            nameHistory = (TextView) view.findViewById(R.id.nameHistory);
            content_history = (TextView) view.findViewById(R.id.content_history);
        }
    }
}
