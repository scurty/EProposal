package com.infosys.eproposal;

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

import java.util.List;

import uk.co.senab.photoview.PhotoView;

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
            topChannelMenu.add(propItem.getName());
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

                if (position >= 0 && position <= 2) {
                    sec = getResources().getString(R.string.menu1);
                } else if (position >= 3 && position <= 8) {
                    sec = getResources().getString(R.string.menu2);
                } else if (position >= 9 && position <= 25) {
                    sec = getResources().getString(R.string.menu3);
                } else if (position >= 26 && position <= 32) {
                    sec = getResources().getString(R.string.menu4);
                } else if (position >= 33 && position <= 34) {
                    sec = getResources().getString(R.string.menu5);
                } else if (position == 35) {
                    sec = "Video";
                } else if (position == 36) {
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
        /*
         int id = item.getItemId();
// Menu 1
        if (id == R.id.menu11) {
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.menu12) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.menu13) {
            mViewPager.setCurrentItem(2);

// Menu 2
        } else if (id == R.id.menu21) {
            mViewPager.setCurrentItem(3);
        } else if (id == R.id.menu22) {
            mViewPager.setCurrentItem(4);
        } else if (id == R.id.menu23) {
            mViewPager.setCurrentItem(5);
        } else if (id == R.id.menu24) {
            mViewPager.setCurrentItem(6);
        } else if (id == R.id.menu25) {
            mViewPager.setCurrentItem(7);
        } else if (id == R.id.menu26) {
            mViewPager.setCurrentItem(8);

// Menu 3
        } else if (id == R.id.menu301) {
            mViewPager.setCurrentItem(9);
        } else if (id == R.id.menu302) {
            mViewPager.setCurrentItem(10);
        } else if (id == R.id.menu303) {
            mViewPager.setCurrentItem(11);
        } else if (id == R.id.menu304) {
            mViewPager.setCurrentItem(12);
        } else if (id == R.id.menu305) {
            mViewPager.setCurrentItem(13);
        } else if (id == R.id.menu306) {
            mViewPager.setCurrentItem(14);
        } else if (id == R.id.menu307) {
            mViewPager.setCurrentItem(15);
        } else if (id == R.id.menu308) {
            mViewPager.setCurrentItem(16);
        } else if (id == R.id.menu309) {
            mViewPager.setCurrentItem(17);
        } else if (id == R.id.menu310) {
            mViewPager.setCurrentItem(18);
        } else if (id == R.id.menu311) {
            mViewPager.setCurrentItem(19);
        } else if (id == R.id.menu312) {
            mViewPager.setCurrentItem(20);
        } else if (id == R.id.menu313) {
            mViewPager.setCurrentItem(21);
        } else if (id == R.id.menu314) {
            mViewPager.setCurrentItem(22);
        } else if (id == R.id.menu315) {
            mViewPager.setCurrentItem(23);
        } else if (id == R.id.menu316) {
            mViewPager.setCurrentItem(24);
        } else if (id == R.id.menu317) {
            mViewPager.setCurrentItem(25);

// Menu 4
        } else if (id == R.id.menu41) {
            mViewPager.setCurrentItem(26);
        } else if (id == R.id.menu42) {
            mViewPager.setCurrentItem(27);
        } else if (id == R.id.menu43) {
            mViewPager.setCurrentItem(28);
        } else if (id == R.id.menu44) {
            mViewPager.setCurrentItem(29);
        } else if (id == R.id.menu45) {
            mViewPager.setCurrentItem(30);
        } else if (id == R.id.menu46) {
            mViewPager.setCurrentItem(31);
        } else if (id == R.id.menu47) {
            mViewPager.setCurrentItem(32);
// Menu 5
        } else if (id == R.id.menu51) {
            mViewPager.setCurrentItem(33);
        } else { // if (id == R.id.menu52) {
            mViewPager.setCurrentItem(34);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*static class SamplePagerAdapter extends PagerAdapter {

        private static final int[] sDrawables = {
                R.drawable.sumario,
                R.drawable.conteudo,
                R.drawable.sumario_executivo,

                R.drawable.produtos_e_servicos_capa,
                R.drawable.produtos_e_servicos,
                R.drawable.presenca_global,
                R.drawable.perfil_corporativo_global,
                R.drawable.perfil_corporativo_brasil,
                R.drawable.clientes,

                R.drawable.experiencia,
                R.drawable.alliage,
                R.drawable.hersheys,
                R.drawable.infosys,
                R.drawable.johnson_controls,
                R.drawable.skf,
                R.drawable.toyota,
                R.drawable.zurich,
                R.drawable.the_linde_group,
                R.drawable.bg_group,
                R.drawable.cerradinho,
                R.drawable.syngenta,
                R.drawable.globonet,
                R.drawable.arcelor,
                R.drawable.nindera,
                R.drawable.ref_ams_brasil_suporte,
                R.drawable.ref_ams_brasil_suporte_oracle,

                R.drawable.alguns_nossos_produtos,
                R.drawable.infosys_fiscal_ingine,
                R.drawable.infosys_fiscal_ingine2,
                R.drawable.information_plataform,
                R.drawable.panaya,
                R.drawable.rpa,
                R.drawable.skava,

                R.drawable.agradecimento,
                R.drawable.fim};

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(sDrawables[position]);
            //   photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            //       @Override
            //      public void onPhotoTap(View view, float x, float y) {
//
            //       }
            //   });
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }*/

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private final int[] sDrawables = {
                R.drawable.sumario,
                R.drawable.conteudo,
                R.drawable.sumario_executivo,

                R.drawable.produtos_e_servicos_capa,
                R.drawable.produtos_e_servicos,
                R.drawable.presenca_global,
                R.drawable.perfil_corporativo_global,
                R.drawable.perfil_corporativo_brasil,
                R.drawable.clientes,

                R.drawable.experiencia,
                R.drawable.alliage,
                R.drawable.hersheys,
                R.drawable.infosys,
                R.drawable.johnson_controls,
                R.drawable.skf,
                R.drawable.toyota,
                R.drawable.zurich,
                R.drawable.the_linde_group,
                R.drawable.bg_group,
                R.drawable.cerradinho,
                R.drawable.syngenta,
                R.drawable.globonet,
                R.drawable.arcelor,
                R.drawable.nindera,
                R.drawable.ref_ams_brasil_suporte,
                R.drawable.ref_ams_brasil_suporte_oracle,

                R.drawable.alguns_nossos_produtos,
                R.drawable.infosys_fiscal_ingine,
                R.drawable.infosys_fiscal_ingine2,
                R.drawable.information_plataform,
                R.drawable.panaya,
                R.drawable.rpa,
                R.drawable.skava,

                R.drawable.agradecimento,
                R.drawable.fim};

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
                        frag = PhotoFragment.newInstance(propItemList.get(pos).getPath());

                    case 2:
                        break;
                }
                ;
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
