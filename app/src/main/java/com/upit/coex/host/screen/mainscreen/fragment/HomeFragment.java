package com.upit.coex.host.screen.mainscreen.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.CoexApplication;
import com.upit.coex.host.R;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.screen.mainscreen.adapter.roomadapter.RoomAdapter;
import com.upit.coex.host.screen.room.activity.NewRoom;
import com.upit.coex.host.viewmodel.mainscreen.HomeFragmentViewModel;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public final static int REQUEST_ADD_ROOM = 103;
    RecyclerView recyclerView;
    TextView txtAddRoom;
    CoData co = new CoData();

    private HomeFragmentViewModel mHomeFragmentViewModel;




    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mHomeFragmentViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);


        Log.d("bao.nt", "nguyen Thien Bao");
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeFragmentViewModel.listRoom();
        mHomeFragmentViewModel.getCoDataLiveData().observe(this, coData -> showRoom(coData));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtAddRoom = view.findViewById(R.id.addRoom);
        recyclerView = view.findViewById(R.id.listRoom);

        txtAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewRoom.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    public void showRoom(CoData co){
        Log.d("aaaaaaaaaa", "sadf" + co.getName() + "|" + co.getmListRoom().size());
        RoomAdapter roomAdapter =  new RoomAdapter(co.getmListRoom(), getActivity());
        Log.d("aaaaaaaaaa1", co.getmListRoom().get(0).getName());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CoexApplication.self());
        ((LinearLayoutManager) mLayoutManager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        roomAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(roomAdapter);

    }


    public void addRoom(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.new_style_room);
        EditText edtName, edtAbout, edtPrice, edtMax;
        edtName = dialog.findViewById(R.id.edtStyleRoom);
        edtAbout = dialog.findViewById(R.id.edtAboutRoom);
        edtPrice = dialog.findViewById(R.id.edtPrice);
        edtMax = dialog.findViewById(R.id.edtMaxPer);

        dialog.show();
    }
}
