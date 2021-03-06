package com.diplom.app.fitnessproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.diplom.app.fitnessproject.R;
import com.diplom.app.fitnessproject.model.DataBaseHelper;
import com.diplom.app.fitnessproject.presenter.UprazneniaAddComplexActivityPresenter;
import com.diplom.app.fitnessproject.presenter.interfaces.ComplexThreeSetUprazneniaGetter;
import com.diplom.app.fitnessproject.presenter.interfaces.ComplexThreeSetUprazneniaSetter;
import com.diplom.app.fitnessproject.presenter.interfaces.PagesViewInteface;
import com.diplom.app.fitnessproject.presenter.interfaces.ComplexSuperSetUprazneniaGetter;
import com.diplom.app.fitnessproject.presenter.interfaces.ComplexSuperSetUprazneniaSetter;
import com.diplom.app.fitnessproject.presenter.interfaces.UprazneniaAddComplexActivityInt;
import com.diplom.app.fitnessproject.view.adapter.TabPagerAdapter;
import com.diplom.app.fitnessproject.view.interfaces.UprazneniaAddComplexView;


public class UprazneniaAddComplex extends AppCompatActivity implements UprazneniaAddComplexView{

    private UprazneniaAddComplexActivityInt presenter;
    private ViewPager viewPager;

    public final static int TYPE_SUPERSET=2;
    public final static int TYPE_THREESET=3;

    public final static int UPR_FIRST=1;
    public final static int UPR_SECOND=2;
    public final static int UPR_THIRD=3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_add);
        /*
        INIT
         */
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_add_complex);
        toolbar.setTitle(getString(R.string.title_add_complex));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        PRESENTER
         */
        presenter=new UprazneniaAddComplexActivityPresenter(getApplicationContext(),this,getSupportFragmentManager()); //set Presenter

        viewPager=(ViewPager)findViewById(R.id.viewpager_add_complex);
        viewPager.setAdapter(((PagesViewInteface)presenter).getTabPagerAdapter());
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout_add_complex);
        tabLayout.setupWithViewPager(viewPager);
        //

           /*
        START ACTIVITY FOR CHANGE?
         */
        if(getIntent()!=null && getIntent().hasExtra("NAME")){
            presenter.onChangeComplex(getIntent());
        }
    }

    @Override
    public void setCurentPage(int id) {
        viewPager.setCurrentItem(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) { // IF NULL
            super.onActivityResult(requestCode, resultCode, data);
            Fragment fragment;
            switch (data.getIntExtra("cat", 0)) {
                case TYPE_SUPERSET:
                    switch (data.getIntExtra("upr", 0)) {
                        case UPR_FIRST:
                            fragment = ((PagesViewInteface) presenter).getTabListFragments().get(0);
                            ((ComplexSuperSetUprazneniaSetter) fragment).setFirstUpr(data.getStringExtra("name"));
                            break;
                        case UPR_SECOND:
                            fragment = ((PagesViewInteface) presenter).getTabListFragments().get(0);
                            ((ComplexSuperSetUprazneniaSetter) fragment).setSecondUpr(data.getStringExtra("name"));
                            break;
                    }
                    break;
                case TYPE_THREESET:
                    switch (data.getIntExtra("upr", 0)) {
                        case UPR_FIRST:
                            fragment = ((PagesViewInteface) presenter).getTabListFragments().get(1);
                            ((ComplexThreeSetUprazneniaSetter) fragment).setFirstUpr(data.getStringExtra("name"));
                            break;
                        case UPR_SECOND:
                            fragment = ((PagesViewInteface) presenter).getTabListFragments().get(1);
                            ((ComplexThreeSetUprazneniaSetter) fragment).setSecondUpr(data.getStringExtra("name"));
                            break;
                        case UPR_THIRD:
                            fragment = ((PagesViewInteface) presenter).getTabListFragments().get(1);
                            ((ComplexThreeSetUprazneniaSetter) fragment).setThirdUpr(data.getStringExtra("name"));
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_ckeck){
            ComplexSuperSetUprazneniaGetter fragment;
            switch (viewPager.getCurrentItem()) {
                case 0://SUPERSET
                    setResult(RESULT_OK, ((UprazneniaAddComplexActivityPresenter)presenter).getSuperSetIntent());
                    finish();
                    return true;
                case 1://THREESET
                    setResult(RESULT_OK, ((UprazneniaAddComplexActivityPresenter)presenter).getThreeSetIntent());
                    finish();
                    return true;
            }
        }
        return false;
    }
}
