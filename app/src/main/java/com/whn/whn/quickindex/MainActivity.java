package com.whn.whn.quickindex;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 快速索引条目
 */
public class MainActivity extends AppCompatActivity {

    private QuickIndexBar quickindexbar;
    private ArrayList<Friend> friends;
    private ListView listview;
    private TextView tv_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quickindexbar = (QuickIndexBar) findViewById(R.id.quickindexbar);
        listview = (ListView) findViewById(R.id.listview);
        tv_word = (TextView) findViewById(R.id.tv_word);

        //初始化数据
        friends = new ArrayList<>();
        prepareData();
        Collections.sort(friends);
        listview.setAdapter(new FriendAdapter());

        //获取控件,设置监听
        quickindexbar.setonLetterChangeListener(new QuickIndexBar.onLetterChangeListener() {
            @Override
            public void onLetterChange(String word) {
                for (int i = 0; i < friends.size(); i++) {
                    String letter = friends.get(i).pinyin.charAt(0) + "";
                    if (letter.equals(word)) {
                        //说明找到了，那么就置顶
                        listview.setSelection(i);
                        break;//找到后立即中断
                    }
                }
                //显示当前的字母
                showCurrentWord(word);
            }
        });
    }

    /**
     * 显示当前字母
     */
    private Handler handler = new Handler();

    private void showCurrentWord(String word) {
        handler.removeCallbacksAndMessages(null);
        tv_word.setText(word);
        tv_word.setVisibility(View.VISIBLE);
        //延时消失
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_word.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private class FriendAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return friends.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                convertView = View.inflate(parent.getContext(), R.layout.adapter_friend, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            //letter显示名字的首个字母
            Friend friend = friends.get(position);
            String letter = friend.pinyin.charAt(0)+"";
            viewHolder.tvName.setText(friend.name);

            //如果当前条目的letter和上一个相同,就隐藏
            if(position>0){
                String lastLetter = friends.get(position-1).pinyin.charAt(0)+"";
                if(letter.equals(lastLetter)){
                    viewHolder.tvLetter.setVisibility(View.GONE);
                }else{
                    viewHolder.tvLetter.setVisibility(View.VISIBLE);
                    viewHolder.tvLetter.setText(letter);
                }
            }else{
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(letter);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_letter)
        TextView tvLetter;
        @Bind(R.id.tv_name)
        TextView tvName;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // 虚拟数据
    private void prepareData() {
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }
}
