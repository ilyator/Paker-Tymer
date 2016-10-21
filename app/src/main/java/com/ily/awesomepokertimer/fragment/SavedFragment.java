package com.ily.awesomepokertimer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ily.awesomepokertimer.R;
import com.ily.awesomepokertimer.adapter.SavedTourneysAdapter;
import com.ily.awesomepokertimer.adapter.SimpleItemTouchHelperCallback;
import com.ily.awesomepokertimer.model.Tournament;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by ily on 20.10.2016.
 */

public class SavedFragment extends Fragment {

    @BindView(R.id.rv_saved)
    RecyclerView rvSaved;

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

        adapter = new SavedTourneysAdapter(realm.copyFromRealm(realm.where(Tournament.class).findAll()));

        rvSaved.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSaved.setLayoutManager(manager);
        rvSaved.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSaved);

        return root;
    }



}
