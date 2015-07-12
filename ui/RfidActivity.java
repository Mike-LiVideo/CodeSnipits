package com.dataticket.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dataticket.R;
import com.dataticket.db.RfidPermitDatabaseHelper;
import com.dataticket.db.tables.RfidPermitTable;
import com.trimble.mcs.rfid.v1.RfidConstants;
import com.trimble.mcs.rfid.v1.RfidException;
import com.trimble.mcs.rfid.v1.RfidManager;
import com.trimble.mcs.rfid.v1.RfidParameters;

/**
 * This activity was designed to read UHF RFID tags specifically with a Trimble Juno T41
 *
 * This activity relies on the hardware button to initialize all scans
 * 
 * Once a tag is scanned it looks for it in the database and then populates the screen 
 * with the appropriate information.
 */

public class RfidActivity
        extends Activity{
    private BroadcastReceiver mRecvr;
    private IntentFilter mFilter;
    private boolean mScanning = false;
    private TextView scanningStatus;
    private TextView tagIdContainer;
    private TextView permitTypeContainer;
    private TextView plateContainer;
    private TextView makeContainer;
    private TextView yearContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);

        mRecvr = new BroadcastReceiver(){
            public void onReceive(Context context, Intent intent){
                onScanComplete(context, intent);
            }
        };
        scanningStatus = (TextView)findViewById(R.id.btn_scan);
        tagIdContainer = ((TextView) findViewById(R.id.rfidPermitContainer));
        permitTypeContainer = ((TextView) findViewById(R.id.rfidTypeContainer));
        plateContainer = ((TextView) findViewById(R.id.rfidPlateContainer));
        makeContainer = ((TextView) findViewById(R.id.rfidMakeContainer));
        yearContainer = ((TextView) findViewById(R.id.rfidYearContainer));

        mFilter = new IntentFilter();
        mFilter.addAction(RfidConstants.ACTION_RFID_TAG_SCANNED);
        mFilter.addAction(RfidConstants.ACTION_RFID_STOP_SCAN_NOTIFICATION);
        mFilter.addAction(RfidConstants.ACTION_RFID_START_SCAN_NOTIFICATION);
        mFilter.addAction(RfidConstants.ACTION_RFID_STATUS_NOTIFICATION);

    }

    private void onRfidReady() {
        try {
            // Set output mode to 'Intent' mode so that broadcast
            // intents will be fired tags are scanned
            RfidParameters parms = RfidManager.getParameters();
            parms.setOutputMode(RfidConstants.OUTPUT_MODE_INTENT);
            RfidManager.setParameters(parms);
        } catch (RfidException e) {
            Log.e(LOG_TAG, "Error setting RFID parameters.", e);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(mRecvr, mFilter);

    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(mRecvr);
    }

    private void onScanComplete(Context context, Intent intent){
        String act = intent.getAction();

        if(act.equals(RfidConstants.ACTION_RFID_TAG_SCANNED)){
            String tagId = intent.getStringExtra(RfidConstants.RFID_FIELD_ID);
            proccessReading(tagId);

        }
        else if (act.equals(RfidConstants.ACTION_RFID_START_SCAN_NOTIFICATION)){
            scanningStatus.setText(getString(R.string.scanning));
        }
        else if(act.equals(RfidConstants.ACTION_RFID_STOP_SCAN_NOTIFICATION)){
            scanningStatus.setText(getString(R.string.idle));
        }
        else if(act.equals(RfidConstants.ACTION_RFID_STATUS_NOTIFICATION)){
        }
    }

    private void proccessReading(String tag){
        StringBuilder output = new StringBuilder();
        RfidPermitTable mRfidPermitTable = new RfidPermitTable();
        RfidPermitDatabaseHelper mRfidPermitDatabaseHelper = new RfidPermitDatabaseHelper(getApplicationContext());
        SQLiteDatabase rfiddb = mRfidPermitDatabaseHelper.getReadableDatabase();
        for(int i = 0; i < tag.length(); i += 2){
            String str = tag.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        String modOutput = output.toString().replaceFirst("^0+(?!$)", "");
        Log.i("MIKE", modOutput);
        Cursor rfidPermit = mRfidPermitDatabaseHelper.getRfidPermitCursor(rfiddb, modOutput);

        try{
            if(rfidPermit.moveToFirst()){
                tagIdContainer.setText(rfidPermit.getString(rfidPermit.getColumnIndex(mRfidPermitTable.COLUMN_NAME_ONE)));
                permitTypeContainer.setText(rfidPermit.getString(rfidPermit.getColumnIndex(mRfidPermitTable.COLUMN_NAME_TWO)));
                plateContainer.setText(rfidPermit.getString(rfidPermit.getColumnIndex(mRfidPermitTable.COLUMN_NAME_THREE)));
                makeContainer.setText(rfidPermit.getString(rfidPermit.getColumnIndex(mRfidPermitTable.COLUMN_NAME_FOUR)));
                yearContainer.setText(rfidPermit.getString(rfidPermit.getColumnIndex(mRfidPermitTable.COLUMN_NAME_FIVE)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
