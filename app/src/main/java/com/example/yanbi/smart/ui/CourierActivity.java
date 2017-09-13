package com.example.yanbi.smart.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yanbi.smart.R;
import com.example.yanbi.smart.adapter.CourierAdapter;
import com.example.yanbi.smart.entity.CourierData;
import com.example.yanbi.smart.utils.L;
import com.example.yanbi.smart.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yanbi on 2017/9/12.
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_getcourier;
    private ListView mListView;
    private List<CourierData> mList=new ArrayList<CourierData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_name= (EditText) findViewById(R.id.et_name);
        et_number= (EditText) findViewById(R.id.et_number);
        btn_getcourier= (Button) findViewById(R.id.btn_get_courier);
        btn_getcourier.setOnClickListener(this);
        mListView= (ListView) findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_courier:
                /*
                 *查询操作
                 */
                String name=et_name.getText().toString().trim();
                String number=et_number.getText().toString().trim();
                String url="http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY+"&com=" +
                        name+"&no="+number;
                if(!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(number)){

                    //请求数据
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
//                            Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
                            L.i("Json: \n"+t);

                            //解析json
                            parsingJson(t);
                        }
                    });
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData data=new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序处理
            Collections.reverse(mList);
            CourierAdapter adapter=new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
