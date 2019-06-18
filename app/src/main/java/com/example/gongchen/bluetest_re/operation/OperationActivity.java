package com.example.gongchen.bluetest_re.operation;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleRssiCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.example.gongchen.bluetest_re.DemarcateDebugActivity;
import com.example.gongchen.bluetest_re.ParameterSettingActivity;
import com.example.gongchen.bluetest_re.R;
import com.example.gongchen.bluetest_re.base.BaseActivity;
import com.example.gongchen.bluetest_re.util.ClockView;
import com.example.gongchen.bluetest_re.util.LogUtil;
import com.example.gongchen.bluetest_re.util.SpUtil;
import com.example.gongchen.bluetest_re.util.Util;
import com.lzy.okgo.cookie.store.SPCookieStore;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class OperationActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_DATA = "key_data";
    public static final String SERVER_ID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String CHAR_ID = "0000ffe1-0000-1000-8000-00805f9b34fb";

    private BleDevice bleDevice;
    private BluetoothGattService bluetoothGattService;
    private BluetoothGattCharacteristic characteristic;


    private int frame_count;
    private int dur_frame_count;
    public static final byte[] xor_key = {(byte) 0xF3, (byte) 0xE2};
    private double unit_price;
    //Text view
    private TextView text_error;
    private TextView text_error0;
    private TextView text_error1;
    private TextView text_error2;
    private TextView text_error3;
    private TextView text_error4;
    private TextView text_error5;
    private TextView text_error6;
    private TextView text_error7;
    private TextView text_unit;
    private TextView text_stable;
    private TextView text_function;
    private TextView text_add;
    private TextView text_count;
    private TextView text_zero;
    private TextView text_rssi;
    private TextView text_result;
    private TextView text_all_weight;
    private TextView text_tare;
    private TextView text_frequency;
    private EditText edit_unit_price;
    private TextView text_all_price;
    private RelativeLayout btn_count;
    private RelativeLayout btn_enter;
    private RelativeLayout btn_tare;
    private RelativeLayout btn_setzero;
    private RelativeLayout btn_function;
    private RelativeLayout btn_add;
    private RelativeLayout btn_set;
    private RelativeLayout btn_dema;
    private TextView powerV;
    private ClockView mClockView;
    Timer timer = new Timer();

//    @BindView(R.id.select_recycler_top)
//    RecyclerView mSelect_recycler_top;
//
//    @BindView(R.id.select_recycler_buttom)
//    RecyclerView mSelect_recycler_buttom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operactions);
        ButterKnife.bind(this);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
        BluetoothGatt gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);
//        btn_dema.setEnabled(true);
//        btn_set.setEnabled(true);
        //List<BluetoothGattService> serviceList = gatt.getServices();

//        for (BluetoothGattService service : serviceList) {
//            uuid_service = service.getUuid();
//            Log.d("OperationActivity", service.toString());
//            Log.d("OperationActivity", uuid_service.toString());
//            List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
//            for (BluetoothGattCharacteristic characteristic : characteristicList) {
//                uuid_chara = characteristic.getUuid();
//                Log.d("OperationActivity", characteristic.toString());
//                Log.d("OperationActivity", uuid_chara.toString());
//            }
//        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BleManager.getInstance().notify(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        Log.d("OperationActivity", "notify success");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        if (data.length > 0) {
                            Log.d("OperationActivity", "hello");
                            receive_data_process(data);

                        }
                        // 打开通知后，设备发过来的数据将在这里出现
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        btn_dema.setEnabled(true);
//        btn_set.setEnabled(true);
        Log.d("OperationActivity", "resume");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BleManager.getInstance().notify(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        Log.d("OperationActivity", "notify success");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        if (data.length > 0) {
                            receive_data_process(data);
                        }
                        // 打开通知后，设备发过来的数据将在这里出现
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        btn_dema.setEnabled(true);
//        btn_set.setEnabled(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BleManager.getInstance().notify(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        Log.d("OperationActivity", "notify success");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        if (data.length > 0) {
                            Log.d("OperationActivity", "hello");
                            receive_data_process(data);
                        }
                        // 打开通知后，设备发过来的数据将在这里出现
                    }
                });
    }

    @Override
    protected void onStop() {
//        BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
        Log.d("OperationActivity", "stop");
        super.onStop();

    }

    @Override
    protected void onPause() {
//        BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
//        Log.d("OperationActivity", "pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        byte[] sent_packet;
        sent_packet = new byte[18];
        switch (v.getId()) {
            case R.id.btn_count:
                sent_packet = sent_data_pack(0, 1);
                break;
            case R.id.btn_enter:
                sent_packet = sent_data_pack(1, 1);
                break;
            case R.id.btn_tare:
                sent_packet = sent_data_pack(2, 1);
                break;
            case R.id.btn_setzero:
                sent_packet = sent_data_pack(3, 1);
                break;
//            case R.id.btn_function:
//                sent_packet = sent_data_pack(4,1);
//                break;
//            case R.id.btn_add:
//                sent_packet = sent_data_pack(5,1);
//                break;
            case R.id.btn_set:
//                btn_dema.setEnabled(false);
                Intent intent = new Intent(OperationActivity.this, ParameterSettingActivity.class);
                intent.putExtra(ParameterSettingActivity.KEY_DATA, bleDevice);
                startActivity(intent);
                break;
            case R.id.btn_dema:
//                btn_set.setEnabled(false);
                Intent intent1 = new Intent(OperationActivity.this, DemarcateDebugActivity.class);
                intent1.putExtra(ParameterSettingActivity.KEY_DATA, bleDevice);
                startActivity(intent1);
                break;
            default:
                break;
        }
        if (sent_packet[0] != 0x00) {
            BleManager.getInstance().write(
                    bleDevice,
                    SERVER_ID,
                    CHAR_ID,
                    sent_packet,
                    new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                            //Log.d("OperationActivity", "successful");
                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                        }
                    });
        }

    }

    private void initData() {
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        if (bleDevice == null) {
            finish();
        }
        mClockView = findViewById(R.id.ClockView);
        edit_unit_price = (EditText) findViewById(R.id.edit_unit_price);
        text_error = (TextView) findViewById(R.id.text_error);
        text_error0 = (TextView) findViewById(R.id.text_error0);
        text_error1 = (TextView) findViewById(R.id.text_error1);
        text_error2 = (TextView) findViewById(R.id.text_error2);
        text_error3 = (TextView) findViewById(R.id.text_error3);
        text_error4 = (TextView) findViewById(R.id.text_error4);
        text_error5 = (TextView) findViewById(R.id.text_error5);
        text_error6 = (TextView) findViewById(R.id.text_error6);
        powerV = findViewById(R.id.powerV);
        text_error7 = (TextView) findViewById(R.id.text_error7);
        text_unit = (TextView) findViewById(R.id.text_unit);
        text_stable = (TextView) findViewById(R.id.text_stable);
        text_function = (TextView) findViewById(R.id.text_function);
        text_add = (TextView) findViewById(R.id.text_add);
        text_count = (TextView) findViewById(R.id.text_count);
        text_zero = (TextView) findViewById(R.id.text_zero);
        text_rssi = (TextView) findViewById(R.id.text_rssi);
        text_result = (TextView) findViewById(R.id.text_result);
        text_all_weight = (TextView) findViewById(R.id.text_all_weight);
        text_tare = (TextView) findViewById(R.id.text_tare);
        text_frequency = (TextView) findViewById(R.id.text_frequency);
        text_all_price = (TextView) findViewById(R.id.text_all_price);
        btn_count = (RelativeLayout) findViewById(R.id.btn_count);
        btn_count.setOnClickListener(this);
        btn_enter = (RelativeLayout) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);
        btn_tare = (RelativeLayout) findViewById(R.id.btn_tare);
        btn_tare.setOnClickListener(this);
        btn_setzero = (RelativeLayout) findViewById(R.id.btn_setzero);
        btn_setzero.setOnClickListener(this);
//        btn_function = (RelativeLayout) findViewById(R.id.btn_function);
//        btn_function.setOnClickListener(this);
//        btn_add = (Button) findViewById(R.id.btn_add);
//        btn_add.setOnClickListener(this);
        btn_set = (RelativeLayout) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(this);
        btn_dema = (RelativeLayout) findViewById(R.id.btn_dema);
        btn_dema.setOnClickListener(this);
        unit_price = 0.;
    }

    public int receive_data_process(byte[] receive_data) {
        BleManager.getInstance().readRssi(
                bleDevice,
                new BleRssiCallback() {

                    @Override
                    public void onRssiFailure(BleException exception) {
                        // 读取设备的信号强度失败
                    }

                    @Override
                    public void onRssiSuccess(int rssi) {
                        text_rssi.setText(Integer.toString(rssi));
                        // 读取设备的信号强度成功
                    }
                });

        if (receive_data.length != 16) {
            Log.d("OperationActivity", "Length error");
            return 0;
        }
        if (receive_data[0] != (byte) 0xFC) {
            Log.d("OperationActivity", "Header error");
            return 0;
        }

        if (receive_data[15] != (byte) 0x19) {
            Log.d("OperationActivity", "End error");
            return 0;
        }
        if (receive_data[3] == 0x00) {
            text_error.setVisibility(View.INVISIBLE);
            text_error0.setVisibility(View.INVISIBLE);
            text_error1.setVisibility(View.INVISIBLE);
            text_error2.setVisibility(View.INVISIBLE);
            text_error3.setVisibility(View.INVISIBLE);
            text_error4.setVisibility(View.INVISIBLE);
            text_error5.setVisibility(View.INVISIBLE);
            text_error6.setVisibility(View.INVISIBLE);
            text_error7.setVisibility(View.INVISIBLE);

            //if (receive_data[2])
            byte[] data_valid = new byte[12];
            for (int i = 0; i < 12; i++) {
                data_valid[i] = receive_data[2 + i];
            }
            if (xor_calculate(data_valid) == receive_data[14]) {
                validata_process(receive_data[1], data_valid);
            }
            return 1;
        } //data
        else {
            text_error.setVisibility(View.VISIBLE);
            for (int i = 0; i < 8; i++) {
                if (((receive_data[3] >> i) & 0x01) == 0x01) {
                    error_process(i);
                } else {
                    error_off_process(i);
                }
            }
            return 0;
        } //order


//        int data_index = 0;
//        while (data_index < receive_data.length) {
//            if (receive_data[data_index] == (byte)0xFC) {
//                if (data_index + 15 <= receive_data.length) {
//                    if (receive_data[data_index + 1] == 0x01) {
//                        //Log.d("OperationActivity", receive_data[data_index + 2])
//                        byte[] data_valid = new byte[5];
//                        for (int i=0; i<5; i++) {
//                            data_valid[i] = receive_data[data_index + 4];
//                        }
//                        if (xor_calculate(data_valid) != receive_data[data_index + 13]) {
//                            Log.d("OperationActivity", "CRC error");
//                        }
//                        validata_process(data_valid);
//                        return 1;
//                    } //数据帧处理
//                    else if (receive_data[data_index + 1] == 0x02) {
//                        if (receive_data[data_index + 2] == 0x01) {
//                            return 1;
//                        } else {
//                            return 1;
//                        }
//                    } //应答帧处理
//                    else {
//                        Log.d("OperationActivity", "frame type error");
//                        return 0;
//                    }
//                } else {
//                    Log.d("OperationActivity", "Length error");
//                    return 0;
//                }
//            }
//            else {
//                data_index++;
//            }
//        }
//        Log.d("OperationActivity", "no header");
//        return 0;
    }
    int int_weight = 0;
    int int_full_weight;
    int int_per_weight;
    public byte[] sent_data_pack(int marker, int type_marker) {
        byte[] sent_data = new byte[18];
        sent_data[0] = (byte) 0xFE;
        if (type_marker == 1) {
            sent_data[1] = 0x30;
            sent_data[2] = (byte) (1 << marker);
            for (int i = 0; i < 13; i++) {
                sent_data[3 + i] = (byte) 0x55;
            }
            byte[] tmp_xor_data = new byte[14];
            for (int i = 0; i < 14; i++) {
                tmp_xor_data[i] = sent_data[i + 2];
            }
            sent_data[16] = xor_calculate(tmp_xor_data);
            sent_data[17] = 0x19;
        } else {
            sent_data[1] = 0x40;
            sent_data[2] = (byte) dur_frame_count;
            for (int i = 0; i < 14; i++) {
                sent_data[3 + i] = 0x00;
            }
            sent_data[17] = 0x19;
        }
        return sent_data;
    }

    public byte xor_calculate(byte[] xor_data) {
        byte xor_result = 0;
        byte[] xor_data_all = new byte[2 + xor_data.length];
        xor_data_all[0] = xor_key[0];
        xor_data_all[1] = xor_key[1];
        for (int i = 0; i < xor_data.length; i++) {
            xor_data_all[2 + i] = xor_data[i];
        }
        for (byte xor_data_tmp : xor_data_all) {
            xor_result ^= xor_data_tmp;
        }
        return xor_result;
    }

    public void validata_process(byte frame_type, byte[] valid_data) {
        //error帧
        //状态帧
        int is_sign = 0;
        for (int i = 0; i < 8; i++) {
            if (((valid_data[0] >> i) & 0x01) == 0x01) {
                state_process(i);
            } else {
                state_process_off(i);
            }
        }
        if (((valid_data[0]) & 0x03) == 0x00) {
            text_unit.setText("件");
            mClockView.setDeviceUnit("件");
            SpUtil.setParam(OperationActivity.this, SpUtil.unit, "件");
        }
        String unit_pri = edit_unit_price.getText().toString();
        if (unit_pri.equals("")) {
            unit_price = 0.;
        } else {
            try {
                unit_price = Double.parseDouble(unit_pri);
            } catch (NumberFormatException e) {
                unit_price = 0.;
                e.printStackTrace();
            }
        }

        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(4);

        //去掉帧头两位
        //去掉帧尾


        if (frame_type == 0x01) {
            int_weight = (valid_data[3] & 0xFF)
                    | ((valid_data[4] & 0xFF) << 8)
                    | ((valid_data[5] & 0xFF) << 16);

            //右边位移两位 是符号位   0x01负号  0x00正
            if (((valid_data[0] >> 2) & 0x01) == 0x01) {
                int_weight = -int_weight;
            }
//            mClockView.setTitleDial(Integer.toString(int_weight));

            if (((valid_data[0] >> 6) & 0x01) == 0x01) {
                text_result.setText(Integer.toString(int_weight));
            mClockView.setTitleDial(Integer.toString(int_weight));
                text_all_price.setText(ddf1.format(unit_price * int_weight));
            } else {
                double weight_re = (double) int_weight / Math.pow(10., 5 - (int) valid_data[2]);
                text_result.setText(Double.toString(weight_re));
            mClockView.setTitleDial(Double.toString(weight_re));
                text_all_price.setText(ddf1.format(unit_price * weight_re));
            }
            int int_tare_weight = (valid_data[6] & 0xFF)
                    | ((valid_data[7] & 0xFF) << 8)
                    | ((valid_data[8] & 0xFF) << 16);
            double tare_weight_re = (double) int_tare_weight / Math.pow(10., 5 - (int) valid_data[2]);
            text_tare.setText(Double.toString(tare_weight_re));
            double power = Double.valueOf(valid_data[9] & 0xFF) / 10;
            StringBuffer sb = new StringBuffer();
            sb.append("当前电压");
            sb.append(power);
            sb.append("V");
            powerV.setText(sb.toString());
            mClockView.setDevicePower((float) power);
        } else if (frame_type == 0x02) {
            int_full_weight = (valid_data[3] & 0xFF)
                    | ((valid_data[4] & 0xFF) << 8)
                    | ((valid_data[5] & 0xFF) << 16);
            int_per_weight = int_weight*100 / int_full_weight;
            mClockView.setCompleteDegree(int_per_weight);
            int int_all_weight = (valid_data[6] & 0xFF)
                    | ((valid_data[7] & 0xFF) << 8)
                    | ((valid_data[8] & 0xFF) << 16);
            double all_weight_re = (double) int_all_weight / Math.pow(10., 5 - (int) valid_data[2]);
            text_all_weight.setText(Double.toString(all_weight_re));
            text_frequency.setText(Integer.toString((int) (valid_data[9] & 0xFF)));

//            int int_full_weight = (valid_data[3] & 0xFF)
//                    | ((valid_data[4] & 0xFF) << 8)
//                    | ((valid_data[5] & 0xFF) << 16);
//            double full_weight_re = (double) int_all_weight / Math.pow(10., 5 - (int) valid_data[2]);
//            setCompleteDegree = (int) (full_weight_re / int_full_weight * 100);
//
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    Message message = handler.obtainMessage();
//                    message.what = 1;
//                    handler.sendMessage(message);
//                }
//            }, 500, 500);
        }

    }

    int setCompleteDegree;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mClockView.setCompleteDegree(setCompleteDegree);
            }
        }
    };


//    int pcsTemp = 0;

    public void state_process(int state_marker) {
//        if (state_marker > 1 && pcsTemp == 0) {
//            text_unit.setText("件");
//        }
        switch (state_marker) {
            case 0:
//                pcsTemp = 1;
                text_unit.setText("N");
                mClockView.setDeviceUnit("N");
                SpUtil.setParam(OperationActivity.this, SpUtil.unit, "N");
                break;
            case 1:
//                pcsTemp = 1;
                text_unit.setText("kg");
                mClockView.setDeviceUnit("kg");
                SpUtil.setParam(OperationActivity.this, SpUtil.unit, "kg");
                break;
            case 2:
//                text_sign.setVisibility(View.VISIBLE);
                break;
            case 3:
                text_stable.setVisibility(View.VISIBLE);
                break;
            case 4:
                text_function.setVisibility(View.VISIBLE);
                break;
            case 5:
                text_add.setVisibility(View.VISIBLE);
                break;
            case 6:
//                text_function.setVisibility(View.VISIBLE);
                text_count.setVisibility(View.VISIBLE);
//                text_unit.setText("件");
                break;
            case 7:
                text_zero.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void state_process_off(int state_marker) {
        switch (state_marker) {
            case 2:
//                text_sign.setVisibility(View.INVISIBLE);
                break;
            case 3:
                text_stable.setVisibility(View.INVISIBLE);
                break;
            case 4:
                text_function.setVisibility(View.INVISIBLE);
                break;
            case 5:
                text_add.setVisibility(View.INVISIBLE);
                break;
            case 6:
                text_count.setVisibility(View.INVISIBLE);
                break;
            case 7:
                text_zero.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    public void error_process(int state_marker) {
        switch (state_marker) {
            case 0:
                text_error0.setVisibility(View.VISIBLE);
                break;
            case 1:
                text_error1.setVisibility(View.VISIBLE);
                break;
            case 2:
                text_error2.setVisibility(View.VISIBLE);
                break;
            case 3:
                text_error3.setVisibility(View.VISIBLE);
                break;
            case 4:
                text_error4.setVisibility(View.VISIBLE);
                break;
            case 5:
                text_error5.setVisibility(View.VISIBLE);
                break;
            case 6:
                text_error6.setVisibility(View.VISIBLE);
                break;
            case 7:
                text_error7.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    public void error_off_process(int state_marker) {
        switch (state_marker) {
            case 0:
                text_error0.setVisibility(View.INVISIBLE);
                break;
            case 1:
                text_error1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                text_error2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                text_error3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                text_error4.setVisibility(View.INVISIBLE);
                break;
            case 5:
                text_error5.setVisibility(View.INVISIBLE);
                break;
            case 6:
                text_error6.setVisibility(View.INVISIBLE);
                break;
            case 7:
                text_error7.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

}
