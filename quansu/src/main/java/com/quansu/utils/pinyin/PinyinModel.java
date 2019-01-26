package com.quansu.utils.pinyin;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Collections;
import java.util.List;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Ysnows on 2017/3/2.
 */

public class PinyinModel {

    private String name;
    private String pinyin;
    private String firstPinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        if (isEmpty(pinyin)) {
            pinyin = transformPinYin(getName());
        }
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstPinyin() {
        firstPinyin = getPinyin().substring(0, 1);
        return firstPinyin;
    }


    /**
     * 将汉字转化为拼音
     *
     * @param character
     *
     * @return
     */
    public static String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }


    /**
     * 按拼音进行排序
     *
     * @param list
     */
    public static void sortWithPinyin(List list) {
        Collections.sort(list, new PinyinComparator());
        //再进行一次排序--特殊符号放在后面


    }


}
