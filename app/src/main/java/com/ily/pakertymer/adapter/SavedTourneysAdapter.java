package com.ily.pakertymer.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ily.pakertymer.R;
import com.ily.pakertymer.listener.ItemTouchHelperAdapter;
import com.ily.pakertymer.model.Tournament;
import com.ily.pakertymer.util.TournamentsUtil;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ily on 21.10.2016.
 */

public class SavedTourneysAdapter extends RecyclerView.Adapter<SavedTourneysAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private Realm realm;
    private RealmResults<Tournament> tournaments;
    private List<Tournament> tournamentsList;
    private Context context;
    private FloatingActionButton activeBtn;

    public SavedTourneysAdapter(Context context, RealmResults<Tournament> tournaments, List<Tournament> tournamentsList) {
        this.tournaments = tournaments;
        this.tournamentsList = tournamentsList;
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        SavedTourneysAdapter.ViewHolder vh = new SavedTourneysAdapter.ViewHolder(rootview);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tournament tournament = tournamentsList.get(position);
        holder.tvName.setText(tournament.getName());
        holder.tvBlinds.setText(TournamentsUtil.getBlindsInfo(context, tournament));
        holder.tvLevels.setText(TournamentsUtil.getLevelsInfo(context, tournament));
        setPlayBtn(tournament, holder.btnPlayPause);
        holder.btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Tournament t : tournamentsList) {
                    if (t.getIndex() != tournament.getIndex()) {
                        t.setActive(false);
                        t.setRunning(false);
                    } else {
                        tournament.setRunning(!tournament.isRunning());
                        tournament.setActive(true);
                    }
                }

                realm.beginTransaction();
                for (Tournament t : tournaments) {
                    if (t.getIndex() != tournament.getIndex()) {
                        t.setActive(false);
                        t.setRunning(false);
                    } else {
                        t.setRunning(!t.isRunning());
                        t.setActive(true);
                    }
                }
                realm.commitTransaction();

                if (activeBtn != null)
                    activeBtn.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                setPlayBtn(tournament, holder.btnPlayPause);
            }
        });
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open Tournament", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPlayBtn(Tournament tournament, FloatingActionButton btn) {
        if (tournament.isRunning()) {
            activeBtn = btn;
            btn.setImageResource(R.drawable.ic_pause_white_24dp);
        } else
            btn.setImageResource(R.drawable.ic_play_arrow_white_24dp);
    }

    @Override
    public int getItemCount() {
        return tournamentsList.size();
    }

    @Override
    public boolean onItemMove(final int fromPosition, final int toPosition) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    realm.where(Tournament.class).equalTo("index", fromPosition).findFirst().setIndex(toPosition);
                    realm.where(Tournament.class).equalTo("index", toPosition).findFirst().setIndex(fromPosition);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        });

        tournamentsList.get(fromPosition).setIndex(toPosition);
        tournamentsList.get(toPosition).setIndex(fromPosition);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(tournamentsList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(tournamentsList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    realm.where(Tournament.class).equalTo("index", position).findFirst().deleteFromRealm();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        tournamentsList.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView tvName, tvBlinds, tvLevels;
        FloatingActionButton btnPlayPause;

        ViewHolder(View v) {
            super(v);
            root = v;
            tvName = (TextView) root.findViewById(R.id.tv_name);
            tvBlinds = (TextView) root.findViewById(R.id.tv_blinds);
            tvLevels = (TextView) root.findViewById(R.id.tv_levels);
            btnPlayPause = (FloatingActionButton) root.findViewById(R.id.btn_play_pause);
        }
    }

}