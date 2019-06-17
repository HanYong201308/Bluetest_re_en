package com.example.gongchen.bluetest_re;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.example.gongchen.bluetest_re.base.BaseActivity;

import java.sql.BatchUpdateException;

public class SetActivity extends BaseActivity implements View.OnClickListener{
    public static final String KEY_DATA = "key_data";
    public static final String SERVER_ID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String CHAR_ID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final byte[] send_request_pack = {(byte)0xFE, (byte)0x30, (byte)0x40, (byte)0x01,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x50, (byte)0x19};
    public static final byte[] send_setok_pack = {(byte)0xFE, (byte)0x30, (byte)0x40, (byte)0x02,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x53, (byte)0x19};
    public static final byte[] send_reset_pack = {(byte)0xFE, (byte)0x10, (byte)0x01, (byte)0x01,
            (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01,
            (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x11, (byte)0x19};
    public static final byte[] xor_key = {(byte)0xF3, (byte)0xE2};
    public BleDevice bleDevice;
    private Spinner spinner_unit;
    private Spinner spinner_auto_shutdown;
    private Spinner spinner_baudrate;
    private Spinner spinner_output_type;
    private Spinner spinner_output_mode;
    private Spinner spinner_ele_save;
    private Spinner spinner_zero_follow_range;
    private Spinner spinner_zero_set_range;
    private Spinner spinner_zero_start_range;
    private Spinner spinner_filter_time_speed;
    private Spinner spinner_time_stable;
    private Spinner spinner_amplitude_stable;
    private Spinner spinner_resolution_ratio;
    private Spinner spinner_dec_point;
    private Button btn_set_submit;
    private Button btn_set_recover;
    private Button btn_set_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initData();
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
                        Log.d("SetActivity", "notify success");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        Log.d("SetActivity", "notify unsuccess");
                        // 打开通知操作失败
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        if (data.length > 0) {
                            receive_set_data_process(data);
                        }
                        // 打开通知后，设备发过来的数据将在这里出现
                    }
                });
        initView();

    }
    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        BleManager.getInstance().notify(
//                bleDevice,
//                SERVER_ID,
//                CHAR_ID,
//                new BleNotifyCallback() {
//                    @Override
//                    public void onNotifySuccess() {
//                        // 打开通知操作成功
//                        Log.d("SetActivity", "notify success");
//                    }
//
//                    @Override
//                    public void onNotifyFailure(BleException exception) {
//                        // 打开通知操作失败
//                    }
//
//                    @Override
//                    public void onCharacteristicChanged(byte[] data) {
//                        if (data.length > 0) {
//                            receive_set_data_process(data);
//                        }
//                        // 打开通知后，设备发过来的数据将在这里出现
//                    }
//                });
    }
    @Override
    protected void onStart() {
        super.onStart();
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
                        Log.d("SetActivity", "notify success");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        if (data.length > 0) {
                            receive_set_data_process(data);
//                            }
                        }
                        // 打开通知后，设备发过来的数据将在这里出现
                    }
                });
    }
    @Override
    protected void onStop() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BleManager.getInstance().write(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                send_setok_pack,
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(int current, int total, byte[] justWrite) {
                        //Log.d("OperationActivity", "successful");
                    }
                    @Override
                    public void onWriteFailure(BleException exception) {
                    }
                });
        //BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
        Log.d("SetActivity", "stop");
        super.onStop();

    }

    @Override
    protected void onPause() {
//        BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
//        Log.d("SetActivity", "pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_submit:
                byte[] send_set_pack = new byte[18];
                send_set_pack[0] = (byte)0xFE;
                send_set_pack[1] = (byte)0x10;
                send_set_pack[17] = (byte)0x19;

                send_set_pack[2] = (byte)(spinner_unit.getSelectedItemPosition() + 1);
                send_set_pack[3] = (byte)(spinner_auto_shutdown.getSelectedItemPosition() + 1);
                send_set_pack[4] = (byte)(spinner_baudrate.getSelectedItemPosition() + 1);
                send_set_pack[5] = (byte)(spinner_output_type.getSelectedItemPosition() + 1);
                send_set_pack[6] = (byte)(spinner_output_mode.getSelectedItemPosition() + 1);
                send_set_pack[7] = (byte)(spinner_ele_save.getSelectedItemPosition() + 1);
                send_set_pack[8] = (byte)(spinner_zero_follow_range.getSelectedItemPosition() + 1);
                send_set_pack[9] = (byte)(spinner_zero_set_range.getSelectedItemPosition() + 1);
                send_set_pack[10] = (byte)(spinner_zero_start_range.getSelectedItemPosition() + 1);
                send_set_pack[11] = (byte)(spinner_filter_time_speed.getSelectedItemPosition() + 1);
                send_set_pack[12] = (byte)(spinner_time_stable.getSelectedItemPosition() + 1);
                send_set_pack[13] = (byte)(spinner_amplitude_stable.getSelectedItemPosition() + 1);
                send_set_pack[14] = (byte)(spinner_resolution_ratio.getSelectedItemPosition() + 1);
                send_set_pack[15] = (byte)(spinner_dec_point.getSelectedItemPosition() + 1);

                byte[] validdata = new byte[14];
                for (int i=0; i<14; i++) {
                    validdata[i] = send_set_pack[2 + i];
                }
                send_set_pack[16] = xor_calculate(validdata);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BleManager.getInstance().write(
                        bleDevice,
                        SERVER_ID,
                        CHAR_ID,
                        send_set_pack,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                //Log.d("OperationActivity", "successful");
                            }
                            @Override
                            public void onWriteFailure(BleException exception) {
                            }
                        });
                break;
            case R.id.btn_set_recover:
                initView();
                break;
            case R.id.btn_set_reset:
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BleManager.getInstance().write(
                        bleDevice,
                        SERVER_ID,
                        CHAR_ID,
                        send_reset_pack,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                //Log.d("OperationActivity", "successful");
                            }
                            @Override
                            public void onWriteFailure(BleException exception) {
                            }
                        });
                break;
            default:
                break;
        }
    }

    private void initData() {
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        if (bleDevice == null) {
            finish();
        }
        spinner_unit = (Spinner) findViewById(R.id.spinner_unit);
        spinner_auto_shutdown = (Spinner) findViewById(R.id.spinner_auto_shutdown);
        spinner_baudrate = (Spinner) findViewById(R.id.spinner_baudrate);
        spinner_output_type = (Spinner) findViewById(R.id.spinner_output_type);
        spinner_output_mode = (Spinner) findViewById(R.id.spinner_output_mode);
        spinner_ele_save = (Spinner) findViewById(R.id.spinner_ele_save);
        spinner_zero_follow_range = (Spinner) findViewById(R.id.spinner_zero_follow_range);
        spinner_zero_set_range = (Spinner) findViewById(R.id.spinner_zero_set_range);
        spinner_zero_start_range = (Spinner) findViewById(R.id.spinner_zero_start_range);
        spinner_filter_time_speed = (Spinner) findViewById(R.id.spinner_filter_time_speed);
        spinner_time_stable = (Spinner) findViewById(R.id.spinner_time_stable);
        spinner_amplitude_stable = (Spinner) findViewById(R.id.spinner_amplitude_stable);
        spinner_resolution_ratio = (Spinner) findViewById(R.id.spinner_resolution_ratio);
        spinner_dec_point = (Spinner) findViewById(R.id.spinner_dec_point);

        btn_set_submit = (Button) findViewById(R.id.btn_set_submit);
        btn_set_submit.setOnClickListener(this);
        btn_set_recover = (Button) findViewById(R.id.btn_set_recover);
        btn_set_recover.setOnClickListener(this);
        btn_set_reset = (Button) findViewById(R.id.btn_set_reset);
        btn_set_reset.setOnClickListener(this);
    }

    private void initView() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BleManager.getInstance().write(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                send_request_pack,
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
    public int receive_set_data_process(byte[] receive_data) {
        for (int i = 0; i < receive_data.length; i++) {
            Log.e("tag", "++++++++++:" + receive_data[i]);
        }
        if (receive_data.length != 16) {
            Log.d("SetActivity", "Length error");
            Log.d("SetActivity", Integer.toString(receive_data.length));
            return 0;
        }
        if (receive_data[0] != (byte)0xFC) {
            Log.d("SetActivity", "Header error");
            return 0;
        }
        if (receive_data[15] != (byte)0x19) {
            Log.d("SetActivity", "End error");
            return 0;
        }
        if (receive_data[1] != 0x03) {
            return 0;
        }
        if (receive_data[2] == 0x01) {
            spinner_unit.setSelection((int)receive_data[3] - 1);
            spinner_auto_shutdown.setSelection(receive_data[4] - 1);
            spinner_baudrate.setSelection(receive_data[5] - 1);
            spinner_output_type.setSelection(receive_data[6] - 1);
            spinner_output_mode.setSelection(receive_data[7] - 1);
            spinner_ele_save.setSelection(receive_data[8] - 1);
            spinner_zero_follow_range.setSelection(receive_data[9] - 1);
            spinner_zero_set_range.setSelection(receive_data[10] - 1);
            spinner_zero_start_range.setSelection(receive_data[11] - 1);
            spinner_filter_time_speed.setSelection(receive_data[12] - 1);
            spinner_time_stable.setSelection(receive_data[13] - 1);
            spinner_amplitude_stable.setSelection(receive_data[14] - 1);
            return 1;
        } else if (receive_data[2] == 0x02) {
            spinner_resolution_ratio.setSelection((int)receive_data[3] - 1);
            spinner_dec_point.setSelection(receive_data[4] - 1);
            return 2;
        }
        return 0;
    }

    public byte xor_calculate(byte[] xor_data) {
        byte xor_result = 0;
        byte[] xor_data_all = new byte[2 + xor_data.length];
        xor_data_all[0] = xor_key[0];
        xor_data_all[1] = xor_key[1];
        for(int i=0; i<xor_data.length; i++) {
            xor_data_all[2 + i] = xor_data[i];
        }
        for (byte xor_data_tmp:xor_data_all) {
            xor_result ^= xor_data_tmp;
        }
        return xor_result;
    }
}
