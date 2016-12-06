package com.lilosoft.outsidescreen.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.adapter.RecycleViewDivider;
import com.lilosoft.outsidescreen.adapter.RecyclerAdapter;
import com.lilosoft.outsidescreen.base.AppContext;
import com.lilosoft.outsidescreen.base.BaseFragment;
import com.lilosoft.outsidescreen.base.Constant;
import com.lilosoft.outsidescreen.bean.DeptDuty;
import com.lilosoft.outsidescreen.bean.NetWorkInfo;
import com.lilosoft.outsidescreen.common.PrefUtils;
import com.lilosoft.outsidescreen.web.WCFService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String ip = null;
    public NetWorkInfo netWorkInfo;
    ArrayList<DeptDuty> data;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.list)
    RecyclerView list;

    private RecyclerAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        getIp();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getIp() {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return WCFService.getIPhost(mContext);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getData(s);
            }
        }.execute();
        return ip;
    }

    public void getData(final String ip) {
        new AsyncTask<String, Integer, NetWorkInfo>() {
            @Override
            protected NetWorkInfo doInBackground(String... strings) {
                return WCFService.getWorkIninfo(ip);
            }

            @Override
            protected void onPostExecute(NetWorkInfo s) {
                super.onPostExecute(s);
                Log.d("aaa", s.toString());
                netWorkInfo = s;
                PrefUtils.saveWorkWindow(netWorkInfo.getWorkwin_id());
                getDept();
                tvTitle.setText(s.getWorkwin_id() + "号窗口办理事项");
                Uri uri = Uri.parse(Constant.WORKINFO);
                onButtonPressed(uri);
            }
        }.execute();
    }

    public void getDept() {
        new AsyncTask<String, Integer, ArrayList<DeptDuty>>() {
            @Override
            protected ArrayList<DeptDuty> doInBackground(String... strings) {
                return data=WCFService.getDeptDuty(netWorkInfo.getWorkwin_id());  //3号窗口
            }

            @Override
            protected void onPostExecute(ArrayList<DeptDuty> list) {
                super.onPostExecute(list);
                Log.d("InformationFragment", list.toString());
                fillData(list);
            }
        }.execute();
    }

    public void fillData(final ArrayList<DeptDuty> data) {
        list.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RecyclerAdapter(mContext, data);
        mAdapter.setmOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mParam1=data.get(position).getId();
                mParam2=data.get(position).getProjectname();
                getFragmentManager().beginTransaction()
                        //使用replace，返回时会重新加载，所以用add
                        .add(R.id.fragment_container, WorkFragment.newInstance(mParam1, mParam2))
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        list.setAdapter(mAdapter);
        list.addItemDecoration(new RecycleViewDivider(AppContext.get(), LinearLayoutManager.HORIZONTAL, R.drawable.divider));
        //这句就是添加我们自定义的分隔线
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
