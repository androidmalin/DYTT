package com.bzh.dytt.main;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bzh.data.basic.MeiZiEntity;
import com.bzh.data.meizi.FastBlurUtil;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.widget.XViewPager;
import com.bzh.log.MyLog;

import java.util.ArrayList;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements MainIView {

    MainPresenter mainA;

    ActionBarDrawerToggle toggle;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.viewPager)
    XViewPager container;
    private CircleImageView iv_head;
    private ImageView iv_header_view_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv_head = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_head);
        iv_header_view_background = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_header_view_background);

        mainA = new MainPresenter(this, this);
        mainA.onCreate(savedInstanceState);
    }

    @Override
    public void setHeaderViewBackground(String url) {
        if (iv_header_view_background != null) {
            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(new BitmapImageViewTarget(iv_header_view_background) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            int scaleRatio = 10;
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource,
                                    resource.getWidth() / scaleRatio,
                                    resource.getHeight() / scaleRatio,
                                    false);
                            Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, 8, true);
                            iv_header_view_background.setImageBitmap(blurBitmap);
                        }
                    });
        }
    }

    @Override
    public void setHeadView(String url) {
        if (iv_head != null)
            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(new BitmapImageViewTarget(iv_head) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            iv_head.setImageBitmap(resource);
                        }
                    });
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initToolbar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void initDrawerToggle() {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void initContainer(PagerAdapter pagerAdapter, int limit) {
        container.setOffscreenPageLimit(limit);
        container.setAdapter(pagerAdapter);
    }

    @Override
    public void setCurrentItem(int item) {
        container.setCurrentItem(item, false);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setNavigationItemSelectedListener(MainPresenter mainA) {
        navigationView.setNavigationItemSelectedListener(mainA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainA.onDestroy();
    }
}
