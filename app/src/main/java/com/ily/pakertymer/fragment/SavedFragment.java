package com.ily.pakertymer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ily.pakertymer.R;
import com.ily.pakertymer.adapter.SavedTourneysAdapter;
import com.ily.pakertymer.adapter.SimpleItemTouchHelperCallback;
import com.ily.pakertymer.model.Tournament;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ily on 20.10.2016.
 */

public class SavedFragment extends Fragment {

    @BindView(R.id.rv_saved)
    RecyclerView rvSaved;
    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    private Realm realm;
    private SavedTourneysAdapter adapter;

    public static SavedFragment newInstance() {
        Bundle args = new Bundle();
        SavedFragment fragment = new SavedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container, false);

        realm = Realm.getDefaultInstance();

        ButterKnife.bind(this, root);

        setUpRecyclerView();

        return root;
    }

    private void setUpRecyclerView() {
        RealmResults<Tournament> tournaments = realm.where(Tournament.class).findAllSorted("index", Sort.ASCENDING);
        List<Tournament> tournamentsList = realm.copyFromRealm(tournaments);
        adapter = new SavedTourneysAdapter(getContext(), tournaments, tournamentsList);
        rvSaved.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSaved.setLayoutManager(manager);
        rvSaved.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSaved);
        rvSaved.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && btnAdd.isShown())
                    btnAdd.hide();
                if (dy < 0 && !btnAdd.isShown())
                    btnAdd.show();
            }
        });
    }

}
