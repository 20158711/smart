package com.example.yanbi.smart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yanbi.smart.R;
import com.example.yanbi.smart.adapter.ChatListAdapter;
import com.example.yanbi.smart.entity.ChatListData;
import com.example.yanbi.smart.utils.L;
import com.example.yanbi.smart.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanbi on 2017/9/10.
 */

public class ButleFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private Button btn_send;
    private EditText et_text;
    private List<ChatListData> mList=new ArrayList<>();
    ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mChatListView= (ListView) view.findViewById(R.id.mChatListView);

        et_text= (EditText) view.findViewById(R.id.et_text);
        btn_send= (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        //设置适配器
        adapter=new ChatListAdapter(getActivity(),mList);
        mChatListView.setAdapter(adapter);

        addLieftItem("你好，我是小优");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                /**
                 * 逻辑
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.清空当前的输入框
                 * 5.添加你输入的内容到right item
                 * 6.发送给机器人请求返回内容
                 * 7.拿到机器人的返回值之后添加在left item
                 */
                String text=et_text.getText().toString().trim();
                if(!TextUtils.isEmpty(text)){
                    if(text.length()<=30){
                        et_text.setText("");
                        addRightItem(text);
                        String url="http://op.juhe.cn/robot/index?info="+text+"&key="+ StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
//                                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                                L.i(t);
                                parsingJson(t);
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "字符长度不能超过30", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //添加左边文本
    private void addLieftItem(String text){
        ChatListData data=new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text){
        ChatListData data=new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    private void parsingJson(String t){
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            String text = result.getString("text");
            addLieftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
