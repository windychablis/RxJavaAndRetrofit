package com.lilosoft.outsidescreen.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.activity.MainActivity;
import com.lilosoft.outsidescreen.base.AppContext;
import com.lilosoft.outsidescreen.base.BaseFragment;
import com.lilosoft.outsidescreen.web.WCFService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvaluateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluateFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_my)
    ImageView tvMy;
    @BindView(R.id.tv_bmy)
    ImageView tvBmy;
    @BindView(R.id.tv_fcmy)
    ImageView tvFcmy;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public EvaluateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluateFragment newInstance(String param1, String param2) {
        EvaluateFragment fragment = new EvaluateFragment();
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
        Log.d("EvaluateFragment", mParam1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluate, container, false);
        ButterKnife.bind(this, view);
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

    @OnClick({R.id.tv_my, R.id.tv_bmy, R.id.tv_fcmy})
    public void btnClick(ImageView iv) {
        if (AppContext.filter()){
            Toast.makeText(mContext,"三分钟之内不能重复提交!",Toast.LENGTH_SHORT).show();
            return;
        }
        //非常满意1  满意2   不满意4
        switch (iv.getId()) {
            case R.id.tv_my:
                comment(2);
                break;
            case R.id.tv_bmy:
                comment(4);
                onButtonPressed(Uri.parse(TAG));
                break;
            case R.id.tv_fcmy:
                comment(1);
                break;
        }
    }

    public void comment(final int feedback) {
        new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                boolean flag = false;
                try {
                    flag = WCFService.comment(mContext.appContext.user.getId(), feedback, mParam1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
//                    Toast.makeText(mContext, "谢谢您的评价！", Toast.LENGTH_SHORT).show();
                    tv1.setVisibility(View.INVISIBLE);
                    tv2.setText("谢谢您的评价！");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//此处相当于布局文件中的Android:layout_gravity属性
                    lp.gravity = Gravity.CENTER;
                    tv2.setLayoutParams(lp);
                    tvBmy.setVisibility(View.INVISIBLE);
                    tvMy.setVisibility(View.INVISIBLE);
                    tvFcmy.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent("lost focus");
                    mContext.sendBroadcast(intent);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
//                            mContext.finish();
//                            mContext.getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                            mContext.nextActivity(MainActivity.class);
//                            mContext.getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container, InformationFragment.newInstance(null, null))
//                                    .commitAllowingStateLoss();

                        }
                    }, 3 * 1000);
                }
            }
        }.execute();

    }

}
