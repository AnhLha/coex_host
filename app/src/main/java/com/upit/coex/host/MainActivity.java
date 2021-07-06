package com.upit.coex.host;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.upit.coex.host.template.widget.CalendarView;
import com.upit.coex.host.template.widget.ScannerOverlay;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    CalendarView mCalendarView;

    BarcodeReader mBarcodeReader;

    ScannerOverlay mScannerOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_coo_step_1);

//        mCalendarView = findViewById(R.id.calendarView);
//        mCalendarView.onCalendarViewListener(new IActionCalendar.CalendarViewListener() {
//            @Override
//            public void onClickedPrevious(Calendar calendar) {
//                mCalendarView.setChoosedDaysByMonth(new int[]{2, 3, 4});
//            }
//
//            @Override
//            public void onClickedNext(Calendar calendar) {
//                mCalendarView.setChoosedDaysByMonth(new int[]{2, 4});
//
//            }
//
//            @Override
//            public void onClickedItemDay(Calendar calendar) {
//                Log.d("bao.xyz", calendar.toString());
//            }
//        });
//        int [] a = {1,2,3};
//        mCalendarView.setChoosedDaysByMonth(a);


        // get the barcode reader instance
//        mBarcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
//
//        mScannerOverlay = findViewById(R.id.scanner_overlay);
//        mScannerOverlay.setSquareWidth(10);


    }

    @Override
    public void onScanned(Barcode barcode) {
        mBarcodeReader.playBeep();
        Log.d("cin", "barcode.displayValue : " + barcode.displayValue);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }
}
