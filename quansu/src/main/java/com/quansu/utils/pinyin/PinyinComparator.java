package com.quansu.utils.pinyin;

import java.util.Comparator;

/**
 * Created by Ysnows on 2017/3/2.
 */

public class PinyinComparator implements Comparator<PinyinModel> {
    @Override
    public int compare(PinyinModel model1, PinyinModel model2) {
        return model1.getPinyin().compareTo(model2.getPinyin());
    }
}
