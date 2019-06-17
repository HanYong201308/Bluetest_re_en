package com.example.gongchen.bluetest_re;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;
import com.example.gongchen.bluetest_re.base.BaseActivity;
import com.example.gongchen.bluetest_re.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

public class ParameterSettingActivity extends BaseActivity {

    @BindView(R.id.rlBarBack)
    RelativeLayout rlBarBack;
    @BindView(R.id.tvBarTitle)
    TextView tvBarTitle;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.N)
    TextView N;
    @BindView(R.id.kg)
    TextView kg;
    @BindView(R.id.automatic_shut_tv)
    TextView automaticShutTv;
    @BindView(R.id.Baud_rateTV)
    TextView BaudRateTVs;
    @BindView(R.id.roughWeight)
    TextView roughWeight;
    @BindView(R.id.suttle)
    TextView suttle;
    @BindView(R.id.no_send)
    TextView noSends;
    @BindView(R.id.Power_saving_function)
    TextView PowerSavingFunctions;
    @BindView(R.id.Zero_tracking)
    TextView ZeroTrackings;
    @BindView(R.id.The_zero_key_tv)
    TextView TheZeroKeyTvs;
    @BindView(R.id.Boot_the_zero_tv)
    TextView BootTheZeroTvs;
    @BindView(R.id.digital_filtering_tv)
    TextView digitalFilteringTvs;
    @BindView(R.id.settling_timeTV)
    TextView settlingTimeTVs;
    @BindView(R.id.Stable_amplitudeTV)
    TextView StableAmplitudeTVs;
    @BindView(R.id.distingTv)
    TextView distingTvs;
    @BindView(R.id.decimalPlaces)
    TextView decimalPoint;
    @BindView(R.id.RestoreSettingsTV)
    TextView RestoreSettingsTV;
    @BindView(R.id.recovery_settingTv)
    TextView recoverySettingTv;
    OptionPicker mOptionPicker;
    int arrows1Rel;
    public BleDevice bleDevice;

    public static final String KEY_DATA = "key_data";
    public static final String SERVER_ID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String CHAR_ID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final String send_request_pack = "FE3040015555555555555555555555555019";
    public static final byte[] send_setok_pack = {(byte) 0xFE, (byte) 0x30, (byte) 0x40, (byte) 0x02,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x53, (byte) 0x19};
    public static final byte[] send_reset_pack = {(byte) 0xFE, (byte) 0x10, (byte) 0x01, (byte) 0x01,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x11, (byte) 0x19};
    public static final byte[] xor_key = {(byte) 0xF3, (byte) 0xE2};
    @BindView(R.id.NCheck)
    ImageView NChecks;
    @BindView(R.id.KGCheck)
    ImageView KGChecks;
    @BindView(R.id.suttleCheck)
    ImageView suttleChecks;
    @BindView(R.id.roughWeightCheck)
    ImageView roughWeightChecks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("onCreate:=============");
        initData();
        setContentView(R.layout.activity_parameter_setting);
        ButterKnife.bind(this);
        init();
    }


    BluetoothGattCharacteristic characteristic;
    List<BluetoothGattService> mServiceData = new ArrayList<>();
    List<BluetoothGattCharacteristic> mCharacteristicData = new ArrayList<>();
    BluetoothGattService bluetoothGattService;

    List<String> mDatas = new ArrayList<>();

    @SuppressLint("NewApi")
    private void init() {
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        if (bleDevice == null) {
            Toast.makeText(this, "bleDevice null", Toast.LENGTH_SHORT).show();
            return;
        }
        BluetoothGatt gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);
        for (BluetoothGattService service : gatt.getServices()) {
            mServiceData.add(service);
        }
        if (mServiceData.size() == 3) {
            bluetoothGattService = mServiceData.get(2);
        }

        for (BluetoothGattCharacteristic characteristic : bluetoothGattService.getCharacteristics()) {
            mCharacteristicData.add(characteristic);
        }
        if (mCharacteristicData.size() != 0) {
            characteristic = mCharacteristicData.get(0);
        }

//        spinner_unit = 0;
//        KGChecks.setBackgroundResource(R.mipmap.check);
//        NChecks.setBackgroundResource(R.mipmap.uncheck);
//        spinner_output_type = 0;
//        suttleChecks.setBackgroundResource(R.mipmap.check);
//        roughWeightChecks.setBackgroundResource(R.mipmap.uncheck);

        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        if (bleDevice == null) {
            finish();
        }
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
                        try {


                            int startIndex = 0;
                            int endIndex = 0;
                            String str = HexUtil.formatHexString(characteristic.getValue()).trim();
                            LogUtil.e("HexUtil:" + str);
                            if (str.length() <= 32) {
                                String one = str.substring(26, 27);
                                String two = str.substring(27, 28);
                                if (!one.equals("7") && !two.equals("8")) {
                                    mDatas.clear();
                                    for (int i = 0; i < str.length(); i++) {
                                        if (endIndex < str.length()) {
                                            startIndex = startIndex + 2;
                                            String temp = str.substring(endIndex, startIndex);
                                            endIndex = endIndex + 2;
                                            mDatas.add(temp);
                                        }
                                    }
                                    if (mDatas.size() > 0 && isOk) {
                                        receive_set_data_process(mDatas);
                                    }
                                }
                            }
                            // 打开通知后，设备发过来的数据将在这里出现
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        initDevice();
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
//        BleManager.getInstance().stopNotify(bleDevice, SERVER_ID, CHAR_ID);
        Log.d("SetActivity", "stop");
        super.onStop();
    }

    List<String> spinner_auto_shutdownData = new ArrayList<>();
    List<String> spinner_baudrateData = new ArrayList<>();
    List<String> spinner_output_modeData = new ArrayList<>();
    List<String> spinner_ele_saveData = new ArrayList<>();
    List<String> spinner_zero_follow_rangeData = new ArrayList<>();
    List<String> spinner_zero_set_rangeData = new ArrayList<>();
    List<String> spinner_zero_start_rangeData = new ArrayList<>();
    List<String> spinner_filter_time_speedData = new ArrayList<>();
    List<String> spinner_time_stableData = new ArrayList<>();
    List<String> spinner_amplitude_stableData = new ArrayList<>();
    List<String> spinner_resolution_ratioData = new ArrayList<>();
    List<String> spinner_dec_pointData = new ArrayList<>();

    boolean isOk = true;


    public void receive_set_data_process(List<String> receive_data) {
        try {
            isOk = false;

            spinner_resolution_ratio = Integer.valueOf(receive_data.get(1));
            distingTvs.setText(spinner_resolution_ratioData.get(spinner_resolution_ratio - 1));

            spinner_dec_point = Integer.valueOf(receive_data.get(2));
            decimalPoint.setText(spinner_dec_pointData.get(spinner_dec_point - 1));

            spinner_unit = Integer.valueOf(receive_data.get(3));
            if (spinner_unit == 1) {
                KGChecks.setBackgroundResource(R.mipmap.check);
                NChecks.setBackgroundResource(R.mipmap.uncheck);
            } else {
                NChecks.setBackgroundResource(R.mipmap.check);
                KGChecks.setBackgroundResource(R.mipmap.uncheck);
            }

            spinner_auto_shutdown = Integer.valueOf(receive_data.get(4));
            automaticShutTv.setText(spinner_auto_shutdownData.get(spinner_auto_shutdown - 1));

            spinner_baudrate = Integer.valueOf(receive_data.get(5));
            BaudRateTVs.setText(spinner_baudrateData.get(spinner_baudrate - 1));


            spinner_output_type = Integer.valueOf(receive_data.get(6));
            if (spinner_output_type == 1) {
                suttleChecks.setBackgroundResource(R.mipmap.check);
                roughWeightChecks.setBackgroundResource(R.mipmap.uncheck);
            } else {
                roughWeightChecks.setBackgroundResource(R.mipmap.check);
                suttleChecks.setBackgroundResource(R.mipmap.uncheck);
            }

            spinner_output_mode = Integer.valueOf(receive_data.get(7));
            noSends.setText(spinner_output_modeData.get(spinner_output_mode - 1));


            spinner_ele_save = Integer.valueOf(receive_data.get(8));
            PowerSavingFunctions.setText(spinner_ele_saveData.get(spinner_ele_save - 1));


            spinner_zero_follow_range = Integer.valueOf(receive_data.get(9));
            ZeroTrackings.setText(spinner_zero_follow_rangeData.get(spinner_zero_follow_range - 1));


            spinner_zero_set_range = Integer.valueOf(receive_data.get(10));
            TheZeroKeyTvs.setText(spinner_zero_set_rangeData.get(spinner_zero_set_range - 1));


            spinner_zero_start_range = Integer.valueOf(receive_data.get(11));
            BootTheZeroTvs.setText(spinner_zero_start_rangeData.get(spinner_zero_start_range - 1));

            spinner_filter_time_speed = Integer.valueOf(receive_data.get(12));
            digitalFilteringTvs.setText(spinner_filter_time_speedData.get(spinner_filter_time_speed - 1));

            spinner_time_stable = Integer.valueOf(receive_data.get(13));
            settlingTimeTVs.setText(spinner_time_stableData.get(spinner_time_stable - 1));

            spinner_amplitude_stable = Integer.valueOf(receive_data.get(14));
            StableAmplitudeTVs.setText(spinner_amplitude_stableData.get(spinner_amplitude_stable - 1));

            isOk = true;
        } catch (Exception e) {

        }
    }

    private void initDevice() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BleManager.getInstance().write(
                bleDevice,
                SERVER_ID,
                CHAR_ID,
                HexUtil.hexStringToBytes(send_request_pack),
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(int current, int total, byte[] data) {
                        String string = HexUtil.formatHexString(data);
                        LogUtil.e("onWriteSuccess：" + string);
                    }

                    @Override
                    public void onWriteFailure(BleException exception) {

                    }
                });
    }

    private void initData() {
        spinner_auto_shutdownData.add("屏幕亮度1");
        spinner_auto_shutdownData.add("屏幕亮度2");
        spinner_auto_shutdownData.add("屏幕亮度3");
        spinner_auto_shutdownData.add("屏幕亮度4");
        spinner_auto_shutdownData.add("屏幕亮度5");
        spinner_auto_shutdownData.add("屏幕亮度6");
        spinner_auto_shutdownData.add("最亮无法进入省电模式");

        spinner_baudrateData.add("9600");
        spinner_baudrateData.add("4800");
        spinner_baudrateData.add("2400");
        spinner_baudrateData.add("1200");

        spinner_output_modeData.add("不发送");
        spinner_output_modeData.add("连续发送");
        spinner_output_modeData.add("稳定时连续发送");
        spinner_output_modeData.add("命令方式");
        spinner_output_modeData.add("大屏幕显示");
        spinner_output_modeData.add("大屏幕和R232同时使用");

        spinner_ele_saveData.add("无省电功能");
        spinner_ele_saveData.add("有省电功能");

        spinner_zero_follow_rangeData.add("0.5e");
        spinner_zero_follow_rangeData.add("1.0e");
        spinner_zero_follow_rangeData.add("1.5e");
        spinner_zero_follow_rangeData.add("2.0e");
        spinner_zero_follow_rangeData.add("2.5e");
        spinner_zero_follow_rangeData.add("3.0e");
        spinner_zero_follow_rangeData.add("5.0e");
        spinner_zero_follow_rangeData.add("禁止跟踪");

        spinner_zero_set_rangeData.add("2%FS");
        spinner_zero_set_rangeData.add("4%FS");
        spinner_zero_set_rangeData.add("10%FS");
        spinner_zero_set_rangeData.add("20%FS");
        spinner_zero_set_rangeData.add("100%FS");

        spinner_zero_start_rangeData.add("2%FS");
        spinner_zero_start_rangeData.add("4%FS");
        spinner_zero_start_rangeData.add("10%FS");
        spinner_zero_start_rangeData.add("20%FS");
        spinner_zero_start_rangeData.add("100%FS");
        spinner_zero_start_rangeData.add("禁止开机置零");

        spinner_filter_time_speedData.add("快");
        spinner_filter_time_speedData.add("中");
        spinner_filter_time_speedData.add("慢");

        spinner_time_stableData.add("快");
        spinner_time_stableData.add("中");
        spinner_time_stableData.add("慢");

        spinner_amplitude_stableData.add("低");
        spinner_amplitude_stableData.add("中");
        spinner_amplitude_stableData.add("高");

        spinner_resolution_ratioData.add("1");
        spinner_resolution_ratioData.add("2");
        spinner_resolution_ratioData.add("5");
        spinner_resolution_ratioData.add("10");
        spinner_resolution_ratioData.add("20");
        spinner_resolution_ratioData.add("50");

        spinner_dec_pointData.add("00.0000");
        spinner_dec_pointData.add("000.000");
        spinner_dec_pointData.add("0000.00");
        spinner_dec_pointData.add("00000.0");
        spinner_dec_pointData.add("000000.");
    }

    StringBuffer stringBuffer = new StringBuffer();

    @OnClick({R.id.rlBarBack, R.id.arrows1Rel,
            R.id.Baud_rateImRel, R.id.KGCheck,
            R.id.NCheck, R.id.suttleCheck,
            R.id.roughWeightCheck, R.id.outputImRel,
            R.id.PowerImRel, R.id.ZeroTrackingImRel,
            R.id.The_zeroImRel, R.id.Boot_the_zeroImRel,
            R.id.digital_filtering_ImRel, R.id.settling_time_ImRel,
            R.id.Stable_amplitude_ImRel, R.id.distinguishability_ImRel,
            R.id.decimal_places_ImRel, R.id.apply_setting_Rel,
            R.id.RestoreSettingsTV, R.id.recovery_settingTv})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlBarBack:
                finish();
                break;
            case R.id.KGCheck:
                spinner_unit = 1;
                KGChecks.setBackgroundResource(R.mipmap.check);
                NChecks.setBackgroundResource(R.mipmap.uncheck);
                break;
            case R.id.NCheck:
                spinner_unit = 2;
                NChecks.setBackgroundResource(R.mipmap.check);
                KGChecks.setBackgroundResource(R.mipmap.uncheck);
                break;
            case R.id.suttleCheck:
                spinner_output_type = 1;
                suttleChecks.setBackgroundResource(R.mipmap.check);
                roughWeightChecks.setBackgroundResource(R.mipmap.uncheck);
                break;
            case R.id.roughWeightCheck:
                spinner_output_type = 2;
                roughWeightChecks.setBackgroundResource(R.mipmap.check);
                suttleChecks.setBackgroundResource(R.mipmap.uncheck);
                break;
            case R.id.arrows1Rel:

                setPackData(spinner_auto_shutdownData, automaticShutTv, 1);
                break;
            case R.id.Baud_rateImRel:
                setPackData(spinner_baudrateData, BaudRateTVs, 2);
                break;
            case R.id.outputImRel:

                setPackData(spinner_output_modeData, noSends, 3);
                break;
            case R.id.PowerImRel:

                setPackData(spinner_ele_saveData, PowerSavingFunctions, 4);
                break;
            case R.id.ZeroTrackingImRel:

                setPackData(spinner_zero_follow_rangeData, ZeroTrackings, 5);
                break;

            case R.id.The_zeroImRel:

                setPackData(spinner_zero_set_rangeData, TheZeroKeyTvs, 6);
                break;
            case R.id.Boot_the_zeroImRel:

                setPackData(spinner_zero_start_rangeData, BootTheZeroTvs, 7);
                break;
            case R.id.digital_filtering_ImRel:

                setPackData(spinner_filter_time_speedData, digitalFilteringTvs, 8);
                break;
            case R.id.settling_time_ImRel:

                setPackData(spinner_time_stableData, settlingTimeTVs, 9);
                break;
            case R.id.Stable_amplitude_ImRel:

                setPackData(spinner_amplitude_stableData, StableAmplitudeTVs, 10);
                break;
            case R.id.distinguishability_ImRel:

                setPackData(spinner_resolution_ratioData, distingTvs, 11);
                break;
            case R.id.decimal_places_ImRel:

                setPackData(spinner_dec_pointData, decimalPoint, 12);
                break;
            case R.id.apply_setting_Rel:
                stringBuffer.setLength(0);
                byte[] send_set_pack = new byte[18];
                send_set_pack[0] = (byte) 0xFE;
                send_set_pack[1] = (byte) 0x10;
                send_set_pack[2] = (byte) (spinner_unit);
                send_set_pack[3] = (byte) (spinner_auto_shutdown);
                send_set_pack[4] = (byte) (spinner_baudrate);
                send_set_pack[5] = (byte) (spinner_output_type);
                send_set_pack[6] = (byte) (spinner_output_mode);
                send_set_pack[7] = (byte) (spinner_ele_save);
                send_set_pack[8] = (byte) (spinner_zero_follow_range);
                send_set_pack[9] = (byte) (spinner_zero_set_range);
                send_set_pack[10] = (byte) (spinner_zero_start_range);
                send_set_pack[11] = (byte) (spinner_filter_time_speed);
                send_set_pack[12] = (byte) (spinner_time_stable);
                send_set_pack[13] = (byte) (spinner_amplitude_stable);
                send_set_pack[14] = (byte) (spinner_resolution_ratio);
                send_set_pack[15] = (byte) (spinner_dec_point);

                byte[] validdata = new byte[14];
                for (int i = 0; i < 14; i++) {
                    validdata[i] = send_set_pack[2 + i];
                }
                send_set_pack[16] = xor_calculate(validdata);
                send_set_pack[17] = (byte) 0x19;

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
                                LogUtil.e("应用设置:" + HexUtil.formatHexString(justWrite));
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {
                            }
                        });
                break;
            case R.id.RestoreSettingsTV:
                initDevice();
                break;
            case R.id.recovery_settingTv:
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
        }
    }

    private void setPackData(List<String> data, final TextView stextView, final int indexType) {
        mOptionPicker = new OptionPicker(this, data);
        mOptionPicker.setTextSize(18);
        mOptionPicker.show();
        mOptionPicker.setSelectedIndex(0);
        mOptionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                indexType(indexType, index);
                mOptionPicker.dismiss();
                stextView.setText(item);
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

    int spinner_unit = 0;
    int spinner_output_type = 0;

    int spinner_auto_shutdown = 0;
    int spinner_baudrate = 0;
    int spinner_output_mode = 0;
    int spinner_ele_save = 0;
    int spinner_zero_follow_range = 0;
    int spinner_zero_set_range = 0;
    int spinner_zero_start_range = 0;
    int spinner_filter_time_speed = 0;
    int spinner_time_stable = 0;
    int spinner_amplitude_stable = 0;
    int spinner_resolution_ratio = 0;
    int spinner_dec_point = 0;

    private void indexType(int indexType, int selectIndex) {
        if (indexType == 1) {
            spinner_auto_shutdown = selectIndex + 1;
        }
        if (indexType == 2) {
            spinner_baudrate = selectIndex + 1;
        }
        if (indexType == 3) {
            spinner_output_mode = selectIndex + 1;
        }
        if (indexType == 4) {
            spinner_ele_save = selectIndex + 1;
        }
        if (indexType == 5) {
            spinner_zero_follow_range = selectIndex + 1;
        }
        if (indexType == 6) {
            spinner_zero_set_range = selectIndex + 1;
        }
        if (indexType == 7) {
            spinner_zero_start_range = selectIndex + 1;
        }
        if (indexType == 8) {
            spinner_filter_time_speed = selectIndex + 1;
        }
        if (indexType == 9) {
            spinner_time_stable = selectIndex + 1;
        }
        if (indexType == 10) {
            spinner_amplitude_stable = selectIndex + 1;
        }
        if (indexType == 11) {
            spinner_resolution_ratio = selectIndex + 1;
        }
        if (indexType == 12) {
            spinner_dec_point = selectIndex + 1;
        }
    }
}
