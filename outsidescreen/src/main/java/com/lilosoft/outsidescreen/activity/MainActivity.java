package com.lilosoft.outsidescreen.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.base.AppContext;
import com.lilosoft.outsidescreen.base.BaseActivity;
import com.lilosoft.outsidescreen.base.BaseFragment;
import com.lilosoft.outsidescreen.base.Constant;
import com.lilosoft.outsidescreen.bean.NetWorkInfo;
import com.lilosoft.outsidescreen.bean.Userinfo;
import com.lilosoft.outsidescreen.common.PrefUtils;
import com.lilosoft.outsidescreen.common.UpdateManager;
import com.lilosoft.outsidescreen.fragment.EvaluateFragment;
import com.lilosoft.outsidescreen.fragment.InformationFragment;
import com.lilosoft.outsidescreen.fragment.NewsFragment;
import com.lilosoft.outsidescreen.fragment.NotificationFragment;
import com.lilosoft.outsidescreen.fragment.OutOfServiceFragment;
import com.lilosoft.outsidescreen.fragment.ProblemFragment;
import com.lilosoft.outsidescreen.fragment.VideoFragment;
import com.lilosoft.outsidescreen.web.WCFService;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.vov.vitamio.Vitamio;

public class MainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {
    private static final int msgKey1 = 1;
    private FragmentTransaction transaction = null;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tabIndicators)
    RadioGroup tabIndicators;
    @BindView(R.id.evaluate)
    Button evaluate;
    @BindView(R.id.tv_no2)
    TextView tvNo2;
    @BindView(R.id.tv_person2)
    TextView tvPerson2;
    @BindView(R.id.tv_phone2)
    TextView tvPhone2;
    @BindView(R.id.tv_phone4)
    TextView tvPhone4;
    @BindView(R.id.tv_window2)
    TextView tvWindow2;
    @BindView(R.id.stop_service)
    Button stopService;
    //    @BindView(R.id.iv_person)
    ImageView ivPerson;

    private Userinfo userinfo = null;

    public InformationFragment informationFragment = InformationFragment.newInstance(null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("lost focus");
        registerReceiver(receiver,intentFilter);
        setContentView(R.layout.activity_main);
        //检查更新
        String uploadXml = WCFService.http+":9002/config.xml";
        UpdateManager manager = new UpdateManager(MainActivity.this);
        manager.checkUpdate(uploadXml);



        ButterKnife.bind(this);
        String str = PrefUtils.getUserInfo();
        userinfo = JSON.parseObject(str, Userinfo.class);
        appContext.user=userinfo;
        getUserPic(userinfo.getId());

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1000);
        oprationTabs();
//        InformationFragment fragment = new InformationFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, informationFragment).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);//注销广播接收者
    }

    public void getUserPic(final String id) {
        new AsyncTask<String, Integer, byte[]>() {
            @Override
            protected byte[] doInBackground(String... strings) {
                byte[] a = null;
                try {
                    a = WCFService.getUserPic(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return a;
            }

            @Override
            protected void onPostExecute(byte[] s) {
                if(s!=null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(s, 0, s.length);
                    ivPerson = (ImageView) findViewById(R.id.iv_person);
                    ivPerson.setImageBitmap(bitmap);
                    super.onPostExecute(s);
                }
            }
        }.execute();
    }

    public void fillData() {
        String str = PrefUtils.getUserInfo();
        Userinfo userinfo = JSON.parseObject(str, Userinfo.class);
        tvNo2.setText(userinfo.getWorkno());
        tvPerson2.setText(userinfo.getTruename());

//        Uri uri = Uri.parse("http://img2.imgtn.bdimg.com/it/u=2298378918,759412970&fm=21&gp=0.jpg");
//        ivPerson = (SimpleDraweeView) findViewById(R.id.iv_person);
//        ivPerson.setImageURI(uri);

    }

    public void oprationTabs() {
        tabIndicators.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.tabInformationButton:
                        mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, informationFragment)
                            .commitAllowingStateLoss();
                        break;
                    case R.id.tabNewsButton:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, NewsFragment.newInstance(null, null))
                                .commitAllowingStateLoss();
                        break;
                    case R.id.tabNotificationButton:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, NotificationFragment.newInstance(null, null))
                                .commitAllowingStateLoss();
                        break;
                    case R.id.tabVideo:
                        Log.d("MainActivity", "video");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, VideoFragment.newInstance(null, null))
                                .commitAllowingStateLoss();
                        break;
                    case R.id.tabProblemButton:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, ProblemFragment.newInstance(null, null))
                                .commitAllowingStateLoss();
                        break;
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
//        Toast.makeText(this, "from to " + uri, Toast.LENGTH_SHORT).show();
//        System.out.println("from to " + uri);
        String str = uri.toString();
        switch (str) {
            case Constant.STARTSERVICE:
                enableRadioGroup(tabIndicators);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case Constant.WORKINFO:
                NetWorkInfo netWorkInfo = informationFragment.netWorkInfo;
                tvPhone2.setText(netWorkInfo.getDepttel());
                tvWindow2.setText(netWorkInfo.getDeptname());
                tvPhone4.setText(netWorkInfo.getSupervisiontel());
                break;
        }
    }


    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            getTime();
            handler.postDelayed(this, 1000);
        }
    };

    public void getTime() {
//        long sysTime = System.currentTimeMillis();
//        CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        tvTime.setText(str);
    }

    @OnClick(R.id.stop_service)
    public void stopService() {
        disableRadioGroup(tabIndicators);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OutOfServiceFragment.newInstance(null, null))
                .commitAllowingStateLoss();
    }

    @OnLongClick(R.id.tv_time)
    public boolean logout() {
        PrefUtils.putCacheLoginState(false);
        PrefUtils.saveUserInfo("");
        MainActivity.this.finish();
        nextActivity(LoginActivity.class);
        return true;
    }

    @OnClick(R.id.evaluate)
    public void toEvaluate() {
        if (PrefUtils.getCacheLoginState()) {
            NetWorkInfo netWorkInfo = informationFragment.netWorkInfo;
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EvaluateFragment.newInstance(netWorkInfo.getWorkwin_id(), null))
                    .commitAllowingStateLoss();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void disableRadioGroup(RadioGroup rg) {
        evaluate.setEnabled(false);
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup(RadioGroup rg) {
        evaluate.setEnabled(true);
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(true);
        }
    }

    /**
     * 更改Fragment对象
     *
     * @param index
     */
    private void switchFragment(int index) {
        transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                transaction.show(fragments.get(index));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("lost focus")){
                evaluate.setEnabled(false);
            }
        }
    };
}
