package com.example.gongchen.bluetest_re;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.example.gongchen.bluetest_re.base.BaseActivity;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextaddmacActivity extends BaseActivity implements View.OnClickListener{

    private Button mac_sub;
    private EditText mac_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textaddmac);
        mac_sub = (Button) findViewById(R.id.mac_sumbit);
        mac_sub.setOnClickListener(this);
        mac_text = (EditText) findViewById(R.id.text_mac);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mac_sumbit:
                String inputmac = mac_text.getText().toString();
                save(inputmac);
                Toast.makeText(TextaddmacActivity.this, "添加成功。", Toast.LENGTH_LONG).show();
                break;

        }
    }

    public void save(String data) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data+",");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
