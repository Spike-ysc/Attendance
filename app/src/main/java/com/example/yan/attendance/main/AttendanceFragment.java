package com.example.yan.attendance.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.yan.attendance.Adapter.GroupExpandableListAdapter;
import com.example.yan.attendance.Db.GroupDb;
import com.example.yan.attendance.R;
import com.example.yan.attendance.Utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {

    private ExpandableListView mListView;
    private GroupExpandableListAdapter mAdapter;
    private List<String> groups;
    private List<List<String>> childs;
    private LinearLayout linToSearch;
    private GroupDb[] myGroup = new GroupDb[]{
            new GroupDb(0,"数据结构",89),
            new GroupDb(0,"计算机组成原理",56),
            new GroupDb(0,"计算机网络",78)
    };
    private GroupDb[] attendGroup = new GroupDb[]{
            new GroupDb(1,"C#程序设计",89),
            new GroupDb(1,"java面向对象",56),
            new GroupDb(1,"专业英语",78)
    };

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        initData();

        linToSearch = (LinearLayout) view.findViewById(R.id.linear_to_search);
        linToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: onclick");
                Intent intent = new Intent(getActivity(), ToSearchActivity.class); //SearchActivity不能用
                startActivity(intent);
            }
        });
        mListView = (ExpandableListView) view.findViewById(R.id.group_list);
        mAdapter = new GroupExpandableListAdapter(getContext(), groups, childs);
        mListView.setAdapter(mAdapter);
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle bundle = new Bundle();

                //我创建的群组
                if (groupPosition == 0){
                    bundle.putSerializable("groupInfo",myGroup[childPosition]);
                    Intent to_my_group = new Intent(getContext(),GroupsActivity.class);
                    to_my_group.putExtras(bundle);
                    startActivity(to_my_group);


                }else if(groupPosition == 1) {
                    bundle.putSerializable("groupInfo",attendGroup[childPosition]);
                    Intent to_attend_group = new Intent(getContext(),GroupsSecondActivity.class);
                    to_attend_group.putExtras(bundle);
                    startActivity(to_attend_group);

                }


                Log.d("home", "onChildClick: "+"v:"+v.toString()+"groupPosition:"+ groupPosition+"childposition："+childPosition);
                return true;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    private void initData(){
        groups = new ArrayList<>();
        groups.add("我创建的群组");
        groups.add("我加入的群组");

        childs = new ArrayList<>();
        List<String> child1 = new ArrayList<>();

        List<String> child2 = new ArrayList<>();
        for (int i=0; i<myGroup.length;i++){
            child1.add(myGroup[i].getName());
        }
        for (int i=0; i<attendGroup.length;i++){
            child2.add(attendGroup[i].getName());
        }



        childs.add(child1);
        childs.add(child2);
    }

}
