package com.diplom.app.fitnessproject.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.diplom.app.fitnessproject.R;
import com.diplom.app.fitnessproject.model.DataBaseModel;
import com.diplom.app.fitnessproject.model.DataBaseModelUpraznenia;
import com.diplom.app.fitnessproject.presenter.UprazneniaFragmentComplexPresenter;
import com.diplom.app.fitnessproject.presenter.interfaces.UprazneniaComplexInterface;
import com.diplom.app.fitnessproject.view.UprazneniaActivity;
import com.diplom.app.fitnessproject.view.interfaces.ComplexView;
import com.diplom.app.fitnessproject.view.interfaces.FragmentPagesUseDb;


public class UprazneniaComplexListFragment extends Fragment implements FragmentPagesUseDb,ComplexView{
    private String title;
    private ExpandableListView list;
    private UprazneniaComplexInterface presenter;
    private DataBaseModelUpraznenia db;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_complexall,null);

        list=(ExpandableListView)v.findViewById(R.id.expandableListView_complexlist_all);
        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return  list.isGroupExpanded(groupPosition) ? list.collapseGroup(groupPosition) : list.expandGroup(groupPosition);
            }
        });
        list.setOnChildClickListener((ExpandableListView.OnChildClickListener) presenter);
        presenter=new UprazneniaFragmentComplexPresenter(getContext(),this,db,getFragmentManager());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.updateList();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public void setAdapter(BaseExpandableListAdapter adapter) {
        list.setAdapter(adapter);

    }

    @Override
    public void setDataBase(DataBaseModel db) {
        this.db=(DataBaseModelUpraznenia)db;
    }

    @Override
    public void startChangeActivity(Intent data) {
       getActivity().startActivityForResult(data, UprazneniaActivity.CHANGE_COMPL);
    }
}
