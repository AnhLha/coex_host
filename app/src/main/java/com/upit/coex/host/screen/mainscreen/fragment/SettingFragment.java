package com.upit.coex.host.screen.mainscreen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.screen.login.activity.LoginActivity;
import com.upit.coex.host.screen.setting.DetailCo;
import com.upit.coex.host.screen.setting.Profile;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.template.view.SquareView;
import com.upit.coex.host.viewmodel.mainscreen.SettingActivityViewModel;

public class SettingFragment extends Fragment {
    Dialog dialogConfirm;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public final static int REQUEST_ADD_ROOM = 103;
    GridView gridView;

    RecyclerView recyclerView;

    // view
    SquareView mProfile, mMySpace, mGeneral, mAboutUs, mPolicy;

    TextView txtLogout;
    CoData co = new CoData();

    private SettingActivityViewModel mSettingFragmentViewModel;




    public SettingFragment() {
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
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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

        mSettingFragmentViewModel = ViewModelProviders.of(this).get(SettingActivityViewModel.class);
//        mSettingFragmentViewModel.logout();
        mSettingFragmentViewModel.getCoDataLiveData().observe(this, s -> doLogin());

        mSettingFragmentViewModel.getmCoDataFailData().observe(this, s -> {
            CoexToast.makeToast(getActivity(), Toast.LENGTH_LONG, s);
        });

        Log.d("bao.nt", "nguyen Thien Bao");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

//        gridView = view.findViewById(R.id.gridViewProfile);
        // anh xa view
        mProfile = view.findViewById(R.id.fragment_setting_profile);
        mAboutUs = view.findViewById(R.id.fragment_setting_about_us);
        mGeneral = view.findViewById(R.id.fragment_setting_general);
        mMySpace = view.findViewById(R.id.fragment_setting_my_space);
        mPolicy = view.findViewById(R.id.fragment_setting_policy);
        txtLogout = view.findViewById(R.id.logout);

        // event
        txtLogout.setOnClickListener(v -> {
            startDialogConfirm();
        });

        mProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Profile.class);
            getActivity().startActivity(intent);
        });

        mMySpace.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailCo.class);
            getActivity().startActivity(intent);
        });
        return view;
    }


    public void doLogin(){

        CoexSharedPreference.getInstance().put(CommonConstants.TOKEN, "");
        CoexSharedPreference.getInstance().put(CommonConstants.SERVICE_CO, "");
        CoexSharedPreference.getInstance().put(CommonConstants.COEX_ID, "");
        CoexSharedPreference.getInstance().put(CommonConstants.CO,"");
        Log.d("bao.nt", "aaaaaaaaaaa");
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    public void startDialogConfirm(){
        Log.d("bao.nt", "Co vao day nay1");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.confirm_change, null);
        builder.setView(view);
        builder.setCancelable(true);
        //
        Button btnCancel, btnAccepted;
        TextView txtTitle, txtMessage;

        txtMessage = view.findViewById(R.id.txtMessage);
        txtTitle = view.findViewById(R.id.txtTitle);
        btnAccepted = view.findViewById(R.id.btnAccepted);
        btnCancel = view.findViewById(R.id.btnCancel);

        txtMessage.setText("Do you sure want to logout??\nAfter logout, it will return to login.");
        txtTitle.setText("Confirm your want to logout");

        btnAccepted.setOnClickListener(v -> {
            mSettingFragmentViewModel.logout();//.editRoom(roomData.getId(), name.getText().toString(), about.getText().toString(), price.getText().toString(), maxPersion.getText().toString() );
            dialogConfirm.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialogConfirm.dismiss();
        });

        dialogConfirm = builder.create();
        dialogConfirm.show();
    }
}
