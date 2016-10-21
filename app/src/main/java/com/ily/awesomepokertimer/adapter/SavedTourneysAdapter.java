package com.ily.awesomepokertimer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ily.awesomepokertimer.R;
import com.ily.awesomepokertimer.listener.ItemTouchHelperAdapter;
import com.ily.awesomepokertimer.model.Tournament;

import java.util.Collections;
import java.util.List;

/**
 * Created by ily on 21.10.2016.
 */

public class SavedTourneysAdapter  extends RecyclerView.Adapter<SavedTourneysAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private List<Tournament> tournaments;

    public SavedTourneysAdapter(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        SavedTourneysAdapter.ViewHolder vh = new SavedTourneysAdapter.ViewHolder(rootview);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(tournaments, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(tournaments, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        tournaments.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
    View root;
    TextView tvPaymentType, tvCreditCard, tvPaymentId;
    ImageView ivPaymentType;

    ViewHolder(View v) {
        super(v);
        root = v;
    }
}

}