package com.infosys.eproposal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

import static android.media.CamcorderProfile.get;

/**
 * Created by scurt on 09/11/2016.
 */
public class MainActivityProp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String nameProp;
    long idProp;
    List<ProposalItem> propItemList;
    private DrawerLayout drawer;
    private ViewPager mViewPager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        nameProp = b.getString("name");
        idProp = b.getLong("id_prop");
        setTitle(nameProp);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = navigationView.getMenu();
        SubMenu topChannelMenu = null;
        BD bd = new BD(getApplication());
        String chave = "asdfg";
        propItemList = bd.buscarProposalItens(idProp);
        for (ProposalItem propItem : propItemList) {
            if (!chave.equals(propItem.getMenu())) {
                chave = propItem.getMenu();
                topChannelMenu = m.addSubMenu(propItem.getMenu());
            }
            topChannelMenu.add(propItem.getName()).setIcon(R.drawable.ic_menu_gallery);
        }

        // Set up the ViewPager with the sections adapter.
        // SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (HackyViewPager) findViewById(R.id.container_prop);

        // mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // mViewPager.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //     public void onClick(View v) {
        //         drawer.openDrawer(Gravity.LEFT);
        //     }
        // });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                String pri = nameProp; //getResources().getString(R.string.infosys);
                String sec = null;


                if (position <= propItemList.size() - 1) {
                    sec = propItemList.get(position).getMenu();
                } else if (position == propItemList.size()) {
                    sec = "Video";
                } else if (position == propItemList.size() + 1) {
                    sec = "Chart";
                }
                setTitle(pri + " - " + sec);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // mViewPager = (ViewPager) findViewById(R.id.container);
        // mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //     return true;
        // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        for (int i = 0; i < propItemList.size(); i++) {
            if (propItemList.get(i).getName().equals(item.getTitle())) {
                mViewPager.setCurrentItem(i);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

// http://www.compiletimeerror.com/2013/10/playing-video-in-android-application.html#.WCE6WvorJEa
// http://stackoverflow.com/questions/18413309/how-to-implement-a-viewpager-with-different-fragments-layouts

            Fragment frag = null;
            if (pos <= (propItemList.size() - 1)) {
                switch (propItemList.get(pos).getType()) {
                    case 1: // Image
                        frag = PhotoFragment.newInstance(propItemList.get(pos).getImagepath());

                        /*      ProposalItem propitem = propItemList.get(pos);
                        if (propitem.getImagebitmap() == null) {
                            File imgFile = new File(propitem.getImagepath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                propitem.setImagebitmap(myBitmap);
                                //    Bitmap myBitmap = BD.decodeFile(imgFile, 300);
                            }
                        }
                        frag = PhotoFragment.newInstance(propitem.getImagebitmap());*/
                    case 2:
                        break;
                }
            } else if (pos == propItemList.size()) {
                frag = VideoFragment.newInstance("SecondFragment, Instance 1");
            } else if (pos == (propItemList.size() + 1)) {
                frag = ChartFragment.newInstance("Chart");
            }
            return frag;
        }

        @Override
        public int getCount() {
            return propItemList.size() + 2;
        }


    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


}
