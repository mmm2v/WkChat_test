package com.weike.test.test;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.media.MediaDrm;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.weike.test.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class BluetoothTestActivity extends AppCompatActivity {

    private String TAG = "tag12";

    private BluetoothLeAdvertiser advertiser;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // 设备不支持蓝牙
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "请开启蓝牙" , Toast.LENGTH_SHORT).show();
        }

        bluetoothAdapter.setName("4444"); // 设置BLE服务端的名称
        advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();

        Button btn1 = findViewById(R.id.btn_test_start);
        AdvertiseCallback advertiseCallback = genCallback();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAdvertising) {
                    btn1.setText("关闭广播");
                    advertiser.startAdvertising(genSetting(), genAdvertiseData3(), scanResponse(), advertiseCallback);
                    isAdvertising = true;
                } else {
                    advertiser.stopAdvertising(advertiseCallback);
                    btn1.setText("开始广播");
                    isAdvertising = false;
                }
            }
        });

//        Button btn2 = findViewById(R.id.btn_test_stop);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isAdvertising) {
//                    advertiser.stopAdvertising(advertiseCallback);
//                }
//            }
//        });
    }

    AdvertiseSettings genSetting() {
        // 设置广播参数
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setTimeout(180000) // 设置超时时间
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .build();
        return settings;
    }

    int seqno = 0;
    public int genSeqno() {
        if(++seqno > 400)
            seqno = 1;
        return seqno;
    }

    AdvertiseData genAdvertiseData() {
        UUID uuid = UUID.randomUUID();
        Log.d(TAG, "genAdvertiseData: " + uuid);
//        byte[] manufacturerData = new byte[2];
//        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
//        bb.put((byte) 0x02);
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x02);
        bb.put((byte) 0x15);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        bb.putShort((short)genSeqno());
        bb.putShort((short)61166);
        bb.put((byte)0xc8);
//        AdvertiseData.Builder builder = new AdvertiseData.Builder();
//        builder.addManufacturerData(0x004c, manufacturerData);
        AdvertiseData advertiseData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true) // 是否把设备名称也广播出去
                .setIncludeTxPowerLevel(true) // 是否把功率电平也广播出去
                // 名称和uuid同时发送超长
//                .addServiceData(new ParcelUuid(uuid), "Ddddd".getBytes())
//                .addManufacturerData(0x004c, manufacturerData)
                .build();
        Log.d(TAG, "id: " + uuid);
        return advertiseData;
    }

    AdvertiseData genAdvertiseData2() {
        byte[] deviceuuid = {};
        //create UUID
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            deviceuuid = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            //String strUuid = new String(deviceuuid);
            System.out.println("deviceuuid:" + UUID.nameUUIDFromBytes(deviceuuid).toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

        UUID proximityUuid = UUID.nameUUIDFromBytes(deviceuuid);
//        UUID uuid = UUID.randomUUID();
        Log.d(TAG, "genAdvertiseData: " + proximityUuid);
//        byte[] manufacturerData = new byte[23];
//        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
//        bb.order(ByteOrder.BIG_ENDIAN);
//        bb.put((byte) 0x03);
//        bb.put((byte) 0x13);
//        bb.putLong(uuid.getMostSignificantBits());
//        bb.putLong(uuid.getLeastSignificantBits());
//        bb.putShort((short)genSeqno());
//        bb.putShort((short)61166);
//        bb.put((byte)0xc8);
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x03);
        bb.put((byte) 0x18);
        bb.putLong(proximityUuid.getMostSignificantBits());
        bb.putLong(proximityUuid.getLeastSignificantBits());
        bb.putShort((short)genSeqno());
        bb.putShort((short)61166);
        bb.put((byte)0xc8);
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x004c, manufacturerData);  //Apple identify
        AdvertiseData adv = builder.build();
        Log.d(TAG, "id: " + proximityUuid);
        return adv;
    }

    public AdvertiseData genAdvertiseData3(){
        UUID uuid = UUID.randomUUID();
        Log.d(this.TAG, "genAdvertiseData: " + uuid);
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x02);
        bb.put((byte) 0x15);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        bb.putShort((short)genSeqno());
        bb.putShort((short)61166);
        bb.put((byte)0xc8);
        new AdvertiseData.Builder();
        AdvertiseData advertiseData = new AdvertiseData.Builder().addManufacturerData(0x004c, manufacturerData).build();
        Log.d(this.TAG, "id: " + uuid);
        return advertiseData;

    }

    public String generateShortUUID() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long combinedBits = mostSignificantBits ^ leastSignificantBits;
        return Long.toHexString(combinedBits);
    }

    public AdvertiseData scanResponse() {
        AdvertiseData.Builder mDataBuilder = new AdvertiseData.Builder();
        byte[] serviceData = {11};
        AdvertiseData mAdvertiseData = mDataBuilder.build();
        if(mAdvertiseData != null){
            Log.d(TAG, "------蓝牙AdvertiseData------: " + mAdvertiseData.getServiceUuids());
        }
        return mAdvertiseData;
    }

    private boolean isAdvertising = false;
    AdvertiseCallback genCallback(){
        AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settings) {
                isAdvertising = true;
                Log.d(TAG, "低功耗蓝牙广播成功："+settings.toString());
//                addService(); // 添加读写服务UUID，特征值等
//                String desc = String.format("BLE服务端“%s”正在对外广播", et_name.getText().toString());
//                tv_hint.setText(desc);
            }

            @Override
            public void onStartFailure(int errorCode) {
                Log.d(TAG, "低功耗蓝牙广播失败，错误代码为"+errorCode);
//                tv_hint.setText("低功耗蓝牙广播失败，错误代码为"+errorCode);
            }
        };
        return mAdvertiseCallback;
    }
}