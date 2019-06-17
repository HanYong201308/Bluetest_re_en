package com.example.gongchen.bluetest_re;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.example.gongchen.bluetest_re.base.BaseActivity;
import com.example.gongchen.bluetest_re.operation.OperationActivity;
import com.example.gongchen.bluetest_re.util.Content;
import com.example.gongchen.bluetest_re.util.SpUtil;
import com.example.gongchen.bluetest_re.util.TitleBar;
import com.example.gongchen.bluetest_re.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: Administrator
 * Time: 2019/6/3
 * Description:标定页面
 */
public class DemarcateDebugActivity extends BaseActivity {
    private Unbinder unbinder;
    @BindView(R.id.title)
    TitleBar titleBar;
    public static final String KEY_DATA = "key_data";
    public static final String SERVER_ID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String CHAR_ID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    private int is_null;
    @BindView(R.id.text_btn_noload)
    TextView text_btn_noload;//空载荷按钮

    @BindView(R.id.text_btn_fifth)
    TextView text_btn_fifth;//标定确认按钮

    @BindView(R.id.edit_demarcate_max_num)
    EditText edit_demarcate_max_num;//最大量程

    @BindView(R.id.edit_demarcate__num)
    EditText edit_demarcate__num;//标准砝码或替代物重量

    @BindView(R.id.text_add)
    TextView text_add;//多级标定

    @BindView(R.id.text_wending)
    ImageView text_wending;

    @BindView(R.id.text_wending2)
    ImageView text_wending2;

    @BindView(R.id.layout_demarcate1)
    LinearLayout layout_demarcate1;

    @BindView(R.id.layout_demarcate2)
    LinearLayout layout_demarcate2;

    boolean isMore = false;
    String TextUnit;

    public BleDevice bleDevice;
    private byte[] ad_data;
    public static final byte[] send_dema_pack = {(byte) 0xFE, (byte) 0x30, (byte) 0x50, (byte) 0x01,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x40, (byte) 0x19};
    public static final byte[] send_demaok_pack = {(byte) 0xFE, (byte) 0x30, (byte) 0x50, (byte) 0x02,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x43, (byte) 0x19};
    //单次一段校准保存
    public static final byte[] send_confirm_pack = {(byte) 0xFE, (byte) 0x20, (byte) 0x02, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x46, (byte) 0x19};

    //多次一段校准
    public static final byte[] send_confirm_packOne = {(byte) 0xFE, (byte) 0x20, (byte) 0x04, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x46, (byte) 0x19};

    //多次二段校准
    public static final byte[] send_confirm_packTwo = {(byte) 0xFE, (byte) 0x20, (byte) 0x05, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x46, (byte) 0x19};

    //多次三段校准
    public static final byte[] send_confirm_packThree = {(byte) 0xFE, (byte) 0x20, (byte) 0x06, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x46, (byte) 0x19};

    //多次三段校准保存
    public static final byte[] send_confirm_packThreeSave = {(byte) 0xFE, (byte) 0x20, (byte) 0x06, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x46, (byte) 0x19};
    public static final byte[] xor_key = {(byte) 0xF3, (byte) 0xE2};

    @Override
    protected void findViews(Bundle savedInstanceState) {
        super.findViews(savedInstanceState);
        setContentView(R.layout.activity_demarcate_debug);
        unbinder = ButterKnife.bind(this);
        titleBar.setTitle(getResources().getString(R.string.calibration));
        TextUnit = (String) SpUtil.getParam(DemarcateDebugActivity.this, SpUtil.unit, "");
        initView();
        initTwo();
        initThree();
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


    ImageView ThreeOneText_wending;
    ImageView ThreeOneText_wending2;
    TextView ThreeText_btn_noload;
    TextView ThreeText_btn_fifth;
    TextView uniFive;
    TextView unitSix;

    //初始化三段标定
    private void initThree() {
        final EditText edit_demarcate_max_numThree = layout_demarcate2.findViewById(R.id.edit_demarcate_max_num);
        final EditText edit_demarcate__numThree = layout_demarcate2.findViewById(R.id.edit_demarcate__num);
        uniFive = layout_demarcate2.findViewById(R.id.unitChildTop);
        unitSix = layout_demarcate2.findViewById(R.id.unitChildButton);
        uniFive.setText(TextUnit);
        unitSix.setText(TextUnit);

        ThreeOneText_wending = layout_demarcate2.findViewById(R.id.text_wending);
        ThreeOneText_wending2 = layout_demarcate2.findViewById(R.id.text_wending2);

        ThreeText_btn_noload = layout_demarcate2.findViewById(R.id.text_btn_noload);
        ThreeText_btn_fifth = layout_demarcate2.findViewById(R.id.text_btn_fifth);
        ThreeText_btn_noload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int int_range = 0;
                int int_dema = 0;
                String str_range = edit_demarcate_max_numThree.getText().toString();
                try {
                    int_range = Integer.parseInt(str_range);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_format),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                String str_dema = edit_demarcate__numThree.getText().toString();
                try {
                    int_dema = Integer.parseInt(str_dema);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_format),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                if (int_dema > int_range) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_greater_range),
                            Toast.LENGTH_LONG).show();
                }
//                if (int_dema < int_range / 5.) {
//                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_small_range),
//                            Toast.LENGTH_LONG).show();
//                }
                send_null(int_dema, int_range, 0x05);
                is_null = 1;
            }
        });
        ThreeText_btn_fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BleManager.getInstance().write(
                        bleDevice,
                        SERVER_ID,
                        CHAR_ID,
                        send_confirm_packThree,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                //Log.d("OperationActivity", "successful");
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {

                            }
                        });
                Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_setting_success),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    ImageView TwoOneText_wending;
    ImageView TwoOneText_wending2;
    TextView TwoText_btn_noload;
    TextView TwoText_btn_fifth;
    TextView uniThree;
    TextView unitFour;



    //初始化二段标定
    private void initTwo() {
        final EditText edit_demarcate_max_numTwo = layout_demarcate1.findViewById(R.id.edit_demarcate_max_num);
        final EditText edit_demarcate__numTwo = layout_demarcate1.findViewById(R.id.edit_demarcate__num);
        uniThree = layout_demarcate1.findViewById(R.id.unitChildTop);
        unitFour = layout_demarcate1.findViewById(R.id.unitChildButton);
        uniThree.setText(TextUnit);
        unitFour.setText(TextUnit);
        TwoOneText_wending = layout_demarcate1.findViewById(R.id.text_wending);
        TwoOneText_wending2 = layout_demarcate1.findViewById(R.id.text_wending2);


        TwoText_btn_noload = layout_demarcate1.findViewById(R.id.text_btn_noload);
        TwoText_btn_fifth = layout_demarcate1.findViewById(R.id.text_btn_fifth);
        TwoText_btn_noload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int int_range = 0;
                int int_dema = 0;
                String str_range = edit_demarcate_max_numTwo.getText().toString();
                try {
                    int_range = Integer.parseInt(str_range);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_format),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                String str_dema = edit_demarcate__numTwo.getText().toString();
                try {
                    int_dema = Integer.parseInt(str_dema);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_format),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                if (int_dema > int_range) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_greater_range),
                            Toast.LENGTH_LONG).show();
                }
//                if (int_dema < int_range / 5.) {
//                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_small_range),
//                            Toast.LENGTH_LONG).show();
//                }
                send_null(int_dema, int_range, 0x05);
                is_null = 1;
            }
        });
        TwoText_btn_fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BleManager.getInstance().write(
                        bleDevice,
                        SERVER_ID,
                        CHAR_ID,
                        send_confirm_packTwo,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                //Log.d("OperationActivity", "successful");
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {

                            }
                        });
                Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_setting_success),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    TextView unitone;
    TextView unittwo;

    /**
     * 初始化控件
     */
    private void initView() {
        unitone = findViewById(R.id.unit);
        unittwo = findViewById(R.id.units);
        unitone.setText(TextUnit);
        unittwo.setText(TextUnit);
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);

        if (bleDevice == null) {
            finish();
        }
        ad_data = new byte[4];
    }

    @OnClick({R.id.text_btn_noload, R.id.text_btn_fifth, R.id.text_add})
    public void onClick(View view) {
        switch (view.getId()) {
            //空载荷
            case R.id.text_btn_noload:
                int int_range;
                int int_dema;
                String str_range = edit_demarcate_max_num.getText().toString();
                try {
                    int_range = Integer.parseInt(str_range);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_format),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    break;
                }
                String str_dema = edit_demarcate__num.getText().toString();
                try {
                    int_dema = Integer.parseInt(str_dema);
                } catch (NumberFormatException e) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_format),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    break;
                }
                if (int_dema > int_range) {
                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_greater_range),
                            Toast.LENGTH_LONG).show();
                    break;
                }
//                if (int_dema < int_range / 5.) {
//                    Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_small_range),
//                            Toast.LENGTH_LONG).show();
//                    break;
//                }
                if (isMore) {
                    send_null(int_dema, int_range, 0x03);
                } else {
                    send_null(int_dema, int_range, 0x01);
                }
                is_null = 1;
                break;
            //确认
            case R.id.text_btn_fifth:
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] tempSendPack;
                if (isMore) {
                    tempSendPack = send_confirm_packOne;
                } else {
                    tempSendPack = send_confirm_pack;
                }
                BleManager.getInstance().write(
                        bleDevice,
                        SERVER_ID,
                        CHAR_ID,
                        tempSendPack,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                //Log.d("OperationActivity", "successful");
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {

                            }
                        });
                Toast.makeText(DemarcateDebugActivity.this, getResources().getString(R.string.toast_setting_success),
                        Toast.LENGTH_LONG).show();
                break;
            //多段标定
            case R.id.text_add:
                isMore = true;
                edit_demarcate_max_num.getText().clear();
                edit_demarcate__num.getText().clear();
                text_add.setVisibility(View.GONE);
                layout_demarcate1.setVisibility(View.VISIBLE);
                layout_demarcate2.setVisibility(View.VISIBLE);
                break;
        }
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
        super.onPause();
    }

    //空载荷
    //空校准
    byte[] send_null_pack = new byte[18];

    public void send_null(int int_dema, int int_range, int applyThree) {
        send_null_pack[0] = (byte) 0xFE;
        send_null_pack[1] = (byte) 0x20;
        send_null_pack[2] = (byte) applyThree;
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
        send_null_pack[15] = (byte) 0x55;
        byte[] xor_data = new byte[14];
        //for (int i=0; i<13; i++) {
        //    xor_data[i] = send_null_pack[i + 3];
        //
        for (int i = 0; i < 14; i++) {
            xor_data[i] = send_null_pack[i + 2];
        }
        send_null_pack[16] = xor_calculate(xor_data);
        send_null_pack[17] = (byte) 0x19;

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

    public byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    public int receive_dema_data_process(byte[] receive_data) {
        if (receive_data.length != 16) {
            Log.d("DemarcateActivity", "Length error");
            Log.d("DemarcateActivity", Integer.toString(receive_data.length));
            return 0;
        }
        if (receive_data[0] != (byte) 0xFC) {
            Log.d("DemarcateActivity", "Header error");
            return 0;
        }
        if (receive_data[15] != (byte) 0x19) {
            Log.d("DemarcateActivity", "End error");
            return 0;
        }
        if (receive_data[1] != 0x04) {
            return 0;
        }
        if (((receive_data[2] >> 3) & 0x01) == 0x01) {
            //一段
            text_wending.setImageResource(R.mipmap.green);
            text_wending2.setImageResource(R.mipmap.green);
            //二段
            TwoOneText_wending.setImageResource(R.mipmap.green);
            TwoOneText_wending2.setImageResource(R.mipmap.green);
            //三段
            ThreeOneText_wending.setImageResource(R.mipmap.green);
            ThreeOneText_wending2.setImageResource(R.mipmap.green);

            if (is_null == 0) {
                ThreeText_btn_noload.setEnabled(true);
                ThreeText_btn_fifth.setEnabled(false);
                TwoText_btn_noload.setEnabled(true);
                TwoText_btn_fifth.setEnabled(false);
                text_btn_noload.setEnabled(true);
                text_btn_fifth.setEnabled(false);
                text_btn_fifth.setTextColor(getResources().getColor(R.color.black));
            } else {
                ThreeText_btn_noload.setEnabled(false);
                ThreeText_btn_fifth.setEnabled(true);
                TwoText_btn_noload.setEnabled(false);
                TwoText_btn_fifth.setEnabled(true);
                text_btn_noload.setEnabled(false);
                text_btn_fifth.setEnabled(true);
                text_btn_fifth.setTextColor(getResources().getColor(R.color.bg1));
            }
            ad_data[0] = receive_data[4];
            ad_data[1] = receive_data[5];
            ad_data[2] = receive_data[6];
            ad_data[3] = receive_data[7];
            return 1;
        } else {
            text_wending.setImageResource(R.mipmap.gray);
            text_wending2.setImageResource(R.mipmap.gray);

            TwoOneText_wending.setImageResource(R.mipmap.gray);
            TwoOneText_wending.setImageResource(R.mipmap.gray);

            ThreeOneText_wending.setImageResource(R.mipmap.gray);
            ThreeOneText_wending2.setImageResource(R.mipmap.gray);
            text_btn_fifth.setEnabled(false);
            text_btn_noload.setEnabled(false);
            return 0;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
