package com.lilosoft.outsidescreen.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.adapter.NewsAdapter;
import com.lilosoft.outsidescreen.adapter.RecycleViewDivider;
import com.lilosoft.outsidescreen.base.AppContext;
import com.lilosoft.outsidescreen.base.BaseFragment;
import com.lilosoft.outsidescreen.bean.NewContext;
import com.lilosoft.outsidescreen.web.WCFService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.list)
    RecyclerView list;
    private NewsAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        getNews();
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

    public void getNews() {
        new AsyncTask<String, Integer, ArrayList<NewContext>>() {
            @Override
            protected ArrayList<NewContext> doInBackground(String... strings) {
                ArrayList<NewContext> list = (ArrayList<NewContext>) WCFService.queryChannelInfoByChannelName("通知公告", "20");
                return list;
            }

            @Override
            protected void onPostExecute(final ArrayList<NewContext> data) {
                super.onPostExecute(data);
                list.setLayoutManager(new LinearLayoutManager(mContext));
                mAdapter = new NewsAdapter(mContext, data);
                mAdapter.setmOnItemClickLitener(new NewsAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mParam1=data.get(position).getTitle();
                        mParam2=data.get(position).getContents();
                        getFragmentManager().beginTransaction()
                                //使用replace，返回时会重新加载，所以用add
                                .add(R.id.fragment_container, WebViewFragment.newInstance(mParam1, mParam2))
                                .addToBackStack(null)
                                .commitAllowingStateLoss();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                list.setAdapter(mAdapter);
                list.addItemDecoration(new RecycleViewDivider(AppContext.get(), LinearLayoutManager.HORIZONTAL, R.drawable.divider2));
                //这句就是添加我们自定义的分隔线
            }
        }.execute();
    }

}
