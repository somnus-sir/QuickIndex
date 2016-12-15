package com.whn.whn.quickindex;

/**
 * Created by lxj on 2016/12/15.
 */

public class Friend implements Comparable<Friend>{
    public String pinyin;
    public String name;

    public Friend(String name) {
        this.name = name;
        this.pinyin = PinYinUtil.getPinYin(this.name);
    }

    @Override
    public int compareTo(Friend o) {
        //汉字能不能排序
        return this.pinyin.compareTo(o.pinyin);
    }
}
