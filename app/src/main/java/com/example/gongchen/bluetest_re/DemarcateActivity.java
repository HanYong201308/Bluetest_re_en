package com.example.gongchen.bluetest_re;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.example.gongchen.bluetest_re.base.BaseActivity;

public class DemarcateActivity extends BaseActivity implements View.OnClickListener{



    public static final String KEY_DATA = "key_data";
    public static final String SERVER_ID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String CHAR_ID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final byte[] send_dema_pack = {(byte)0xFE, (byte)0x30, (byte)0x50, (byte)0x01,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x40, (byte)0x19};
    public static final byte[] send_demaok_pack = {(byte)0xFE, (byte)0x30, (byte)0x50, (byte)0x02,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x43, (byte)0x19};
    public static final byte[] send_confirm_pack = {(byte)0xFE, (byte)0x20, (byte)0x02, (byte)0x55,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x46, (byte)0x19};
    public static final byte[] xor_key = {(byte)0xF3, (byte)0xE2};
    public BleDevice bleDevice;
    private int is_null;
    private Button btn_null_submit;
    private Button btn_dema_submit;
    private TextView text_is_stable;
    private byte[] ad_data;
    private EditText edit_range;
    private EditText edit_demarcate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demarcate);
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
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        if (data.length > 0) {
                            receive_dema_data_process(data);
                        }
                        // 打开通知后，设备发过来的数据将在这里出现
                    }
                });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BleManager.getInstance().write(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                send_dema_pack,
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
    @Override
    protected void onResume() {
        super.onResume();
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
                            receive_dema_data_process(data);
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
                send_demaok_pack,
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
            case R.id.btn_null_submit:
                int int_range;
                int int_dema;
                String str_range = edit_range.getText().toString();
                try {
                    int_range = Integer.parseInt(str_range);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateActivity.this, "请输入正确格式",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    break;
                }
                String str_dema = edit_demarcate.getText().toString();

                try {
                    int_dema = Integer.parseInt(str_dema);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateActivity.this, "请输入正确格式",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    break;
                }
                if (int_dema > int_range) {
                    Toast.makeText(DemarcateActivity.this, "标定重量大于量程",
                            Toast.LENGTH_LONG).show();
                    break;
                }
                if (int_dema < int_range/5.) {
                    Toast.makeText(DemarcateActivity.this, "标定重量过小",
                            Toast.LENGTH_LONG).show();
                    break;
                }
                send_null(int_dema, int_range);
                is_null = 1;
                break;
            case R.id.btn_dema_submit:
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BleManager.getInstance().write(
                        bleDevice,
                        SERVER_ID,
                        CHAR_ID,
                        send_confirm_pack,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                //Log.d("OperationActivity", "successful");
                            }
                            @Override
                            public void onWriteFailure(BleException exception) {
                            }
                        });
                Toast.makeText(DemarcateActivity.this, "设置成功",
                        Toast.LENGTH_LONG).show();
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

        btn_null_submit = (Button) findViewById(R.id.btn_null_submit);
        btn_null_submit.setOnClickListener(this);
        btn_dema_submit = (Button) findViewById(R.id.btn_dema_submit);
        btn_dema_submit.setOnClickListener(this);
        btn_null_submit.setEnabled(false);
        btn_dema_submit.setEnabled(false);
        text_is_stable = (TextView) findViewById(R.id.text_is_stable);
        edit_range = (EditText) findViewById(R.id.edit_range);
        edit_demarcate = (EditText) findViewById(R.id.edit_demarcate);

        is_null = 0;

        ad_data = new byte[4];

    }


    public int receive_dema_data_process(byte[] receive_data) {
        if (receive_data.length != 16) {
            Log.d("DemarcateActivity", "Length error");
            Log.d("DemarcateActivity", Integer.toString(receive_data.length));
            return 0;
        }
        if (receive_data[0] != (byte)0xFC) {
            Log.d("DemarcateActivity", "Header error");
            return 0;
        }
        if (receive_data[15] != (byte)0x19) {
            Log.d("DemarcateActivity", "End error");
            return 0;
        }
        if (receive_data[1] != 0x04) {
            return 0;
        }
        if(((receive_data[2] >> 3) & 0x01) == 0x01) {
            text_is_stable.setVisibility(View.VISIBLE);
            if (is_null == 0) {
                btn_null_submit.setEnabled(true);
                btn_dema_submit.setEnabled(false);
            } else {
                btn_null_submit.setEnabled(false);
                btn_dema_submit.setEnabled(true);
            }
            ad_data[0] = receive_data[4];
            ad_data[1] = receive_data[5];
            ad_data[2] = receive_data[6];
            ad_data[3] = receive_data[7];
            return 1;
        } else {
            text_is_stable.setVisibility(View.INVISIBLE);
            btn_dema_submit.setEnabled(false);
            btn_null_submit.setEnabled(false);
            return 0;
        }
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

    public void send_null(int int_dema, int int_range) {
        byte[] send_null_pack = new byte[18];
        send_null_pack[0] = (byte)0xFE;
        send_null_pack[1] = (byte)0x20;
        send_null_pack[2] = (byte)0x01;
        send_null_pack[3] = ad_data[0];
        send_null_pack[4] = ad_data[1];
        send_null_pack[5] = ad_data[2];
        send_null_pack[6] = ad_data[3];
        byte[] data_tmp;
        data_tmp = toBytes(int_range);
        send_null_pack[7] = data_tmp[0];
        send_null_pack[8] = data_tmp[1];
        send_null_pack[9] = data_tmp[2];
        send_null_pack[10] = data_tmp[3];
        data_tmp = toBytes(int_dema);
        send_null_pack[11] = data_tmp[0];
        send_null_pack[12] = data_tmp[1];
        send_null_pack[13] = data_tmp[2];
        send_null_pack[14] = data_tmp[3];
        send_null_pack[15] = (byte)0x55;
        byte[] xor_data = new byte[14];
        //for (int i=0; i<13; i++) {
        //    xor_data[i] = send_null_pack[i + 3];
        //
        for (int i=0; i<14; i++) {
            xor_data[i] = send_null_pack[i + 2];
        }
        send_null_pack[16] = xor_calculate(xor_data);
        send_null_pack[17] = (byte)0x19;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BleManager.getInstance().write(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                send_null_pack,
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

    public byte[] toBytes(int i)
    {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }
}

