package com.chatapp.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chatapp.adapters.RecentUpdateAdapter;
import com.chatapp.pixly.pix.Options;
import com.chatapp.pixly.pix.Pix;

import java.util.ArrayList;

import im.vector.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment implements View.OnClickListener {

    private Activity context;
    private RecyclerView rv;
    private ConstraintLayout statusContainer;
    private RecentUpdateAdapter adapter;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rvList);
        statusContainer = view.findViewById(R.id.statusContainer);
        rv.setLayoutManager(new LinearLayoutManager(context));
        statusContainer.setOnClickListener(this);
        setData();

    }

    public void setData() {
        ArrayList<RecentModel> list = new ArrayList<>();
        list.add(new RecentModel());
        list.add(new RecentModel());
        adapter = new RecentUpdateAdapter(list, context);
        rv.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> returnValue = new ArrayList<>();
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                }
            }
            break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.statusContainer) {
            Pix.start(this, Options.init().
                    setCount(3).
                    setRequestCode(100));
        }
    }
}
