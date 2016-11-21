package com.lilosoft.outsidescreen.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.base.BaseFragment;
import com.lilosoft.outsidescreen.bean.Project;
import com.lilosoft.outsidescreen.common.PrefUtils;
import com.lilosoft.outsidescreen.web.WCFService;

import org.apache.commons.lang.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AsyncTask client;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.project_name)
    TextView projectName;
    @BindView(R.id.tv_requirement2)
    TextView tvRequirement2;
    @BindView(R.id.tv_dept2)
    TextView tvDept2;
    @BindView(R.id.tv_material2)
    TextView tvMaterial2;
    @BindView(R.id.tv_subject2)
    TextView tvSubject2;
    @BindView(R.id.tv_process2)
    TextView tvProcess2;
    @BindView(R.id.tv_promise2)
    TextView tvPromise2;
    @BindView(R.id.tv_legal2)
    TextView tvLegal2;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Project project;

    private OnFragmentInteractionListener mListener;

    public WorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkFragment newInstance(String param1, String param2) {
        WorkFragment fragment = new WorkFragment();
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
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        ButterKnife.bind(this, view);
        getData();
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

    /**
     * 获取办事指南
     */
    public void getData() {
        client = new AsyncTask<String, Integer, Project>() {
            @Override
            protected Project doInBackground(String... strings) {
                Project p = null;
                try {
                    p = WCFService.queryZhinan(mParam1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return p;
            }

            @Override
            protected void onPostExecute(Project p) {
                super.onPostExecute(p);
                tvTitle.setText(PrefUtils.getWorkWindow() + "号窗口办理事项");
                projectName.setText(mParam2);
                tvRequirement2.setText(p.getPremit_cond());
                tvDept2.setText(p.getAccept_name());
                tvMaterial2.setText(p.getApply_material());
                tvSubject2.setText(p.getAccept_name());
                tvProcess2.setText(StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(p.getProcess())));
                tvPromise2.setText(p.getPromise_desc());
                tvLegal2.setText(p.getStatutory_desc());
                Log.d("InformationFragment", p.toString());
            }
        }.execute();
    }

    @OnClick(R.id.iv_back)
    public void goBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        client.cancel(true);
    }
}
