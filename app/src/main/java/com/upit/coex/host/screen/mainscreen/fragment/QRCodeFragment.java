package com.upit.coex.host.screen.mainscreen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.R;
import com.upit.coex.host.commons.ScanActivityPortrait;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.service.helper.CoexHelper;
import com.upit.coex.host.template.widget.DialogSuccess;
import com.upit.coex.host.viewmodel.mainscreen.QRCodeFragmentViewModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.upit.coex.host.constants.mainscreen.fragment.QRCodeFragmentConstant.CAN_NOT_GET_TRANSACTION_KEY;
import static com.upit.coex.host.constants.mainscreen.fragment.QRCodeFragmentConstant.CHECK_IN;
import static com.upit.coex.host.constants.mainscreen.fragment.QRCodeFragmentConstant.CHECK_IN_FAIL_KEY;
import static com.upit.coex.host.constants.mainscreen.fragment.QRCodeFragmentConstant.CHECK_IN_SUCCESS_KEY;
import static com.upit.coex.host.constants.mainscreen.fragment.QRCodeFragmentConstant.CHECK_OUT_SUCCESS_KEY;

public class QRCodeFragment extends Fragment implements View.OnClickListener, DialogSuccess.Listener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QRCodeFragment() {
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

    private Button mBtnScanQRCode, mBtnCancle, mBtnCheckInOut;

    private Button mBtnCheckOut;
    private TextView mFinish;

    private TextView mTvEmail, mTvPhoneNumber, mTvNumberGuests, mTvTimeAppointment, mTvDuration, mTvTotalAmount, mTvPayment;
    private TextView mTvNoti;
    private String mTransactionId = "";
    private boolean mIsActivitiForResult = false;

    private QRCodeFragmentViewModel mQRCodeFragmentViewModel;

    private RelativeLayout mRlErrorInfo, mRlTransactionInformation;

    public static QRCodeFragment newInstance(String param1, String param2) {
        QRCodeFragment fragment = new QRCodeFragment();
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

        mQRCodeFragmentViewModel = ViewModelProviders.of(this.requireActivity()).get(QRCodeFragmentViewModel.class);

        this.mQRCodeFragmentViewModel.getTransactionLiveData().observe(this, transactionData -> {

            Log.d("pbm",transactionData.getPayment()+"");
            Log.d("cin---", "this.mQRCodeFragmentViewModel.getTransactionLiveData().observe");
            if (!mIsActivitiForResult) {
                return;
            }
            this.mRlTransactionInformation.setVisibility(View.VISIBLE);
            this.mRlErrorInfo.setVisibility(View.GONE);
            this.mTvEmail.setText(transactionData.getEmail());
            this.mTvPhoneNumber.setText(transactionData.getPhoneNumber());
            this.mTvNumberGuests.setText(transactionData.getNumberPerson() == 1 ? "1 guest" : transactionData.getNumberPerson() + " guests");
            this.mTvTimeAppointment.setText(CoexHelper.showTime(transactionData.getStartTime()));
            this.mTvDuration.setText(transactionData.getDuration() == 1 ? "1 hour" : transactionData.getDuration() + " hours");
            this.mTvTotalAmount.setText(CoexHelper.showPrice("" + transactionData.getPrice()) + " VND");
            this.mTvPayment.setText(transactionData.getPayment()?"Yes":"No");
//            this.mBtnCheckInOut.setText(CHECK_IN.equals(transactionData.getKey()) ? R.string.check_in : R.string.check_out);
//            this.mBtnCheckInOut.setText("Check in");
            Log.d("cin--------", "b" + transactionData.getKey());
            if (CHECK_IN.equals(transactionData.getKey())) {
//                this.mBtnCheckInOut.setText("Check in");
                this.mBtnCheckInOut.setVisibility(View.VISIBLE);
                this.mBtnCheckOut.setVisibility(View.GONE);
                this.mFinish.setVisibility(View.GONE);
            } else if (CommonConstants.CHECK_OUT.equals(transactionData.getKey())) {
                Log.d("cin--------", "b" + transactionData.getKey());
                this.mBtnCheckInOut.setVisibility(View.GONE);
                this.mFinish.setVisibility(View.GONE);
                this.mBtnCheckOut.setVisibility(View.VISIBLE);
//                this.mBtnCheckOut.setText("Check out");
            } else {
                Log.d("cin--------", "a");
                this.mBtnCheckOut.setVisibility(View.GONE);
                this.mBtnCheckInOut.setVisibility(View.GONE);
                this.mBtnCancle.setVisibility(View.GONE);
                this.mFinish.setVisibility(View.VISIBLE);


            }


        });

        this.mQRCodeFragmentViewModel.getErrorLiveData().observe(this, text -> {
            if (!mIsActivitiForResult) {
                return;
            }
            if (CAN_NOT_GET_TRANSACTION_KEY.equals(text)) {
                this.mRlErrorInfo.setVisibility(View.VISIBLE);
                this.mRlTransactionInformation.setVisibility(View.GONE);
                this.mBtnScanQRCode.setText("Scan QR code again");
                this.mTvNoti.setText("Can not get information transaction");
            } else {
                Toast.makeText(this.getContext(), text, Toast.LENGTH_LONG).show();
            }
        });

        this.mQRCodeFragmentViewModel.getKeyCheckInOutLiveData().observe(this, key -> {
            if (key.first.toString().equals(CommonConstants.CHECK_IN)) {
                Log.d("cin", "--------------CHECK IN -cO VAO DAY");
                if (CHECK_IN_SUCCESS_KEY.equals(key.second.toString())) {
                    Log.d("cin", "-----------inNNNNNN----cO VAO DAY");
                    showDialog(this.getContext(), true, true);
                    mBtnCheckInOut.setVisibility(View.GONE);
                    mBtnCheckOut.setVisibility(View.VISIBLE);
                } else {
                    showDialog(this.getContext(), true, false);
                }
            } else if (key.first.toString().equals(CommonConstants.CHECK_OUT)){

                Log.d("cin", "---------------cO VAO DAY");
                if (CHECK_OUT_SUCCESS_KEY.equals(key.second.toString())) {
                    showDialog(this.getContext(), false, true);
                    mBtnCheckInOut.setVisibility(View.VISIBLE);
                    mBtnCheckOut.setVisibility(View.GONE);
                    Log.d("cin", "-----------AAAAAAAA----cO VAO DAY");

                } else {
                    showDialog(this.getContext(), false, false);
                }
            }else {
                mFinish.setVisibility(View.VISIBLE);


            }
        });

//        this.mQRCodeFragmentViewModel.getKeyCheckInOutLiveData().observe(this, key -> {
//            if (CHECK_IN_SUCCESS_KEY.equals(key)) {
//                showDialog(this.getContext(), true);
//            } else if (CHECK_OUT_SUCCESS_KEY.equals(key)) {
//                showDialog(this.getContext(), false);
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("cin---", "public void onStart()");
        Log.d("cin---", "mIsActivitiForResult   " + mIsActivitiForResult);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("cin---", "-----------A-------------");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);

        mBtnScanQRCode = view.findViewById(R.id.btn_scan_qrcode);
        mBtnScanQRCode.setOnClickListener(this);

        mTvEmail = view.findViewById(R.id.tv_email);
        mTvPhoneNumber = view.findViewById(R.id.tv_phone_number);
        mTvNumberGuests = view.findViewById(R.id.tv_number_guests);
        mTvDuration = view.findViewById(R.id.tv_duration);
        mTvTotalAmount = view.findViewById(R.id.tv_total_amount);
        mTvTimeAppointment = view.findViewById(R.id.tv_time_appointment);
        mTvPayment = view.findViewById(R.id.tv_payment);

        mRlErrorInfo = view.findViewById(R.id.rl_error_info);
        mRlTransactionInformation = view.findViewById(R.id.rl_information);

        mBtnCancle = view.findViewById(R.id.btn_cancle);
        mBtnCheckInOut = view.findViewById(R.id.btn_check_in_out);

        //
        mBtnCheckOut = view.findViewById(R.id.btn_check_out);
        mFinish = view.findViewById(R.id.txtFinish);

        mTvNoti = view.findViewById(R.id.tv_noti);

        this.mRlTransactionInformation.setVisibility(View.GONE);
        this.mRlErrorInfo.setVisibility(View.VISIBLE);

        mBtnCancle.setOnClickListener(this);
        mBtnCheckInOut.setOnClickListener(this);
        mBtnCheckOut.setOnClickListener(this);

//        mQRCodeFragmentViewModel.clearData();


        return view;
    }

    private void showQRCode() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(QRCodeFragment.this);
//        integrator.setDesiredBarcodeFormats("QR_CODE");
        integrator.setPrompt("Scan a QR code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setCaptureActivity(ScanActivityPortrait.class);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("cin---", "public void onActivityResult");
        mIsActivitiForResult = true;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String r = result.getContents();
                this.mTransactionId = r;
                mQRCodeFragmentViewModel.requestTransactionData(this.mTransactionId);
                return;
//                }

            } else {
            }
            this.mRlErrorInfo.setVisibility(View.VISIBLE);
            this.mRlTransactionInformation.setVisibility(View.GONE);
        }
        //can not set value

    }

    private void showDialog(Context context, boolean checkin, boolean status) {
        if (checkin) {
            if (status) {
                new AlertDialog.Builder(context)
                        .setTitle("")
                        .setMessage("You are check-in success")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Continue with delete operation
                            mRlErrorInfo.setVisibility(View.VISIBLE);
                            mRlTransactionInformation.setVisibility(View.GONE);
                            mQRCodeFragmentViewModel.clearData();
                        })
                        .show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("")
                        .setMessage("You are check-in success")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Continue with delete operation
                            mRlErrorInfo.setVisibility(View.VISIBLE);
                            mRlTransactionInformation.setVisibility(View.GONE);
                            mQRCodeFragmentViewModel.clearData();
                        })
                        .show();
            }
        }else {
            if (status) {
                new AlertDialog.Builder(context)
                        .setTitle("")
                        .setMessage("You are check-out success")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Continue with delete operation
                            mRlErrorInfo.setVisibility(View.VISIBLE);
                            mRlTransactionInformation.setVisibility(View.GONE);
                            mQRCodeFragmentViewModel.clearData();
                        })
                        .show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("")
                        .setMessage("You are check-out success")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Continue with delete operation
                            mRlErrorInfo.setVisibility(View.VISIBLE);
                            mRlTransactionInformation.setVisibility(View.GONE);
                            mQRCodeFragmentViewModel.clearData();
                        })
                        .show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_qrcode:
            case R.id.btn_cancle:
                showQRCode();
                break;
            case R.id.btn_check_in_out:
                Log.d("cin", "---------------" + mBtnCheckInOut.getText().toString());
//                if (mBtnCheckInOut.getText().toString().equals(R.string.check_in)) {
                this.mQRCodeFragmentViewModel.requestCheckInOut(this.mTransactionId, true);
//                } else {
//                    this.mQRCodeFragmentViewModel.requestCheckInOut(this.mTransactionId, false);
//                }
                break;

            case R.id.btn_check_out:
                Log.d("cin", "------11111---------" + mBtnCheckInOut.getText().toString());
                this.mQRCodeFragmentViewModel.requestCheckInOut(this.mTransactionId, false);
                break;
        }
    }


    @Override
    public void onDestroy() {
//        clearListener();
        mQRCodeFragmentViewModel.destroyView();
        super.onDestroy();
    }

    @Override
    public void respondWhenClickPositiveButton() {
        //when turn off dialog
        Log.d("cin", "-----------------------------");
        this.mRlErrorInfo.setVisibility(View.VISIBLE);
        this.mRlTransactionInformation.setVisibility(View.GONE);

    }
}
