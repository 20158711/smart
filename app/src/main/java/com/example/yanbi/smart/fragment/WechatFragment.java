package com.example.yanbi.smart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yanbi.smart.R;
import com.example.yanbi.smart.adapter.WeChatAdapter;
import com.example.yanbi.smart.entity.WeChatData;
import com.example.yanbi.smart.ui.WebViewActivity;
import com.example.yanbi.smart.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanbi on 2017/9/10.
 */

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList=new ArrayList<>();
    private List<String> mListTitle=new ArrayList<>();
    private List<String> mListUrl=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_wechat,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView= (ListView) view.findViewById(R.id.mListView);

        String url="http://v.juhe.cn/weixin/query?key="+ StaticClass.WETCH_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);

//                两种传值
//                Bundle bundle=new Bundle();
//                bundle.putString("key","value");
//                intent.putExtras(bundle);
                intent.putExtra("title",mListTitle.get(i));
                intent.putExtra("url",mListUrl.get(i));
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject json= (JSONObject) list.get(i);
                WeChatData data=new WeChatData();
                String title = json.getString("title");
                String source = json.getString("source");
                String firstImg = json.getString("firstImg");
                String url=json.getString("url");
                data.setTitle(title);
                data.setSource(source);
                data.setImgUrl(firstImg);
                mListTitle.add(title);
                mListUrl.add(url);
                mList.add(data);
            }
            WeChatAdapter adapter=new WeChatAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
