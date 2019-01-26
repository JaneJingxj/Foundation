package com.quansu.ui.activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.TAB;
import com.quansu.common.ui.BaseActivity;
import com.quansu.common.ui.FragmentAdapter;
import com.quansu.viewpagerindicator.CirclePageIndicator;
import com.quansu.ui.adapter.ViewPagerAdapter;
import com.quansu.ui.fragment.AnimInterface;
import com.quansu.utils.BUN;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseGuideActivity extends BaseActivity implements OnPageChangeListener {

    @Nullable
    private ViewPager vp;

    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private CirclePageIndicator indicator;


    @Override
    protected void initThings(Bundle savedInstanceState) {
        vp = getViewPager();
        indicator = getIndicator();

        ArrayList<Integer> layoutIds = getLayoutIds();
        if (layoutIds != null && layoutIds.size() > 0) {
            LayoutInflater inflater = LayoutInflater.from(this);
            views = new ArrayList<>();
            for (Integer layoutId : layoutIds) {
                views.add(inflater.inflate(layoutId, null));
            }
            vpAdapter = new ViewPagerAdapter(views, this, getClassName());
            vp.setAdapter(vpAdapter);
            vp.setOnPageChangeListener(this);
        } else {
            vp.setOffscreenPageLimit(4);
            ArrayList<Fragment> fragments = getFragments();
            if (fragments != null && fragments.size() > 0) {

                ArrayList tabs = new ArrayList();

                for (Fragment fragment : fragments) {
                    tabs.add(new TAB("", fragment, new BUN().ok()));
                }


                FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), tabs);
                vp.setAdapter(fragmentAdapter);
                indicator.setViewPager(vp);
                vp.addOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                    }

                    @Override
                    public void onPageSelected(int position) {

                        Fragment fragment = fragments.get(position);
                        AnimInterface animInterface = (AnimInterface) fragment;
                        animInterface.anim();

                        if (position - 1 >= 0) {
                            Fragment fragmentsub = fragments.get(position - 1);
                            if (fragmentsub != null) {
                                AnimInterface animInterfacesub = (AnimInterface) fragmentsub;
                                animInterfacesub.animed();
                            }
                        }

                        if (position + 1 < fragments.size()) {
                            Fragment fragmentplus = fragments.get(position + 1);
                            if (fragmentplus != null) {
                                AnimInterface animInterfacesub = (AnimInterface) fragmentplus;
                                animInterfacesub.animed();
                            }
                        }

                        if (position == fragments.size() - 1) {


                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {


                    }
                });

            }
        }
    }

    /**
     * 获取 ViewPager
     *
     * @return
     */
    protected abstract ViewPager getViewPager();

    protected abstract CirclePageIndicator getIndicator();

    /**
     * MainActivity ClassName
     *
     * @return
     */
    protected abstract Class getClassName();

    @Override
    public BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected FrameLayout getBody() {
        return null;
    }

    /**
     * layoutids
     *
     * @return
     */
    protected abstract ArrayList<Integer> getLayoutIds();

    protected abstract ArrayList getFragments();

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {


    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public void goToLoginActivity() {

    }



    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }
}