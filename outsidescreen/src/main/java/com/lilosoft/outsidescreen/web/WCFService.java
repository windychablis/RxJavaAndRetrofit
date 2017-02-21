package com.lilosoft.outsidescreen.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.util.Xml;

import com.alibaba.fastjson.JSON;
import com.lilosoft.outsidescreen.bean.DeptDuty;
import com.lilosoft.outsidescreen.bean.NetWorkInfo;
import com.lilosoft.outsidescreen.bean.NewContext;
import com.lilosoft.outsidescreen.bean.Project;
import com.lilosoft.outsidescreen.bean.Userinfo;
import com.lilosoft.outsidescreen.common.CommonUtil;
import com.lilosoft.outsidescreen.common.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chablis on 2016/11/7.
 */

public class WCFService {
//    public static String url = "http://10.29.163.150:9003/SPService.svc";
//    public static String http = "http://10.29.163.150";
        public static String http = "http://192.168.1.107";
    public static String sgoUrl = http + ":8898/wisdomgov/ws";
        public static String url = "http://192.168.1.107:9004/SPService.svc";
    private Context context;

    private ArrayList<HeaderProperty> headerPropertyArrayList;

    public WCFService(Context context) {
        headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        this.context = context;
    }

    /**
     * 登陆
     *
     * @param useid
     * @return
     * @throws JSONException
     */
    public static Userinfo gettabpage5and6(String useid) throws JSONException {
        Userinfo data = null;
        SoapObject request = new SoapObject("http://tempuri.org/",
                "Gettabpage5and6ByAndroid");
        request.addProperty("work_id", useid);
//        addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url);
        trans.debug = true;
        try {
            trans.call(
                    "http://tempuri.org/ISPService/Gettabpage5and6ByAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                data = new Userinfo();
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
//                JSONArray array=jsonO.getJSONArray("UserInfo");
//                JSONObject jobj = (JSONObject) array.get(0);
                JSONObject jobj = (JSONObject) jsonO.getJSONObject("UserInfo");
                data.setPoliticaltype(jobj.getString("politicaltype"));
                data.setId(jobj.getString("id"));
                data.setTruename(jobj.getString("truename"));
                data.setWorkno(jobj.getString("workno"));
                PrefUtils.saveUserInfo(jobj.toString());
                PrefUtils.putCacheLoginState(true);

/*                CacheContent.setUserInfo(data);
                db.deleteUserinfo(useid);
                ContentValues vlas = new ContentValues();
                vlas.put("id", jobj.getString("id"));
                vlas.put("truename", jobj.getString("truename"));
                vlas.put("workno", jobj.getString("workno"));
                vlas.put("politicaltype", jobj.getString("politicaltype"));
                db.insertUserinfo(vlas);*/
            }
        } catch (Exception e) {
            System.out.println(e);
//            data = db.findUserinfo(useid);
        }

        return data;
    }

    /**
     * 获取登陆照片
     *
     * @param useid
     * @return
     * @throws JSONException
     */
    @SuppressLint("NewApi")
    public static byte[] getUserPic(String useid) throws JSONException {
        byte[] img = null;
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetPictureByAndroid");
        request.addProperty("id", useid);
//        addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetPictureByAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
//				ContentValues vlas = new ContentValues();
//				vlas.put("img", jsonVal);
//				db.updateUserinfoImg(vlas, useid);
//				if (vlas != null && "".equals(vlas) == false) {
                img = Base64.decode(jsonVal, Base64.DEFAULT);
//				}
                // imgBitMap = BitmapFactory.decodeByteArray(img, 0,
                // img.length);

            }
        } catch (Exception e) {
        }
        return img;
    }

    /**
     * 区划版添加areacode
     *
     * @param request
     */
    public static void addAreaCode(SoapObject request) {
//        SharedPreferences sp = AppContext.get().getSharedPreferences(CacheContent.SPName, Context.MODE_PRIVATE);
//        String areacode = sp.getString("areacode", "");
//        if(TextUtils.isEmpty(areacode)){
//            return;
//        }
//        request.addProperty("areacode", areacode);
    }

    /**
     * 获取本地ip
     *
     * @param context
     * @return
     */
    public static String getIPhost(Context context) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        String a = inetAddress.getHostAddress().toString();
                        return a;
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取窗口信息
     *
     * @param ip 本地ip
     * @return
     */
    public static NetWorkInfo getWorkIninfo(String ip) {
        NetWorkInfo data = new NetWorkInfo();
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetWorkIninfoByAndroid");
        request.addProperty("WorkOutIp", ip);
        request.addProperty("areacode", "360105000000");
//        addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetWorkIninfoByAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                JSONObject jobj = jsonO.getJSONObject("WorkIninfo");
                CommonUtil.saveLog("success", jobj.toString());
                data = JSON.parseObject(jobj.toString(), NetWorkInfo.class);
//                Gson gson = new Gson();
//                data = gson.fromJson(jobj.toString(), NetWorkInfo.class);
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
//			getWorkIninfo(ip);
//			e.printStackTrace();
        }

        return data;
    }

    /**
     * 办事指南
     *
     * @param workWinId
     * @return
     */
    public static ArrayList<DeptDuty> getDeptDuty(String workWinId) {
        ArrayList<DeptDuty> dutys = new ArrayList<DeptDuty>();
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetDeptDutyByAndroid");
        request.addProperty("WorkWinId", workWinId);
        request.addProperty("areacode", "360105000000");
//        addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetDeptDutyByAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                if (jsonVal.equals("0")) {
                    PrefUtils.saveDeptDuty("");
                    return dutys;
                }
                JSONObject jsonO = new JSONObject(jsonVal);
                JSONArray deptDutys = jsonO.getJSONArray("DeptDuty");
                dutys = (ArrayList<DeptDuty>) JSON.parseArray(deptDutys.toString(), DeptDuty.class);
                PrefUtils.saveDeptDuty(deptDutys.toString());
                return dutys;
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dutys;
    }


    /** 查询新闻 **/
    /**
     * 最新要闻  常见问题 通知公告
     *
     * @param channelName 最新要闻
     * @param amount      数量
     * @return
     */
    public static List<NewContext> queryChannelInfoByChannelName(String channelName,
                                                                 String amount) {
        ArrayList<NewContext> cotexts = new ArrayList<NewContext>();
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListChannelInfoByChannelName");
        request.addProperty("channelName", channelName);

        request.addProperty("iCount", amount);
        request.addProperty("areacode", "360105000000");
//        addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url);
        trans.debug = true;
        try {
            trans.call(
                    "http://tempuri.org/ISPService/ListChannelInfoByChannelName",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int pcount = result.getPropertyCount();
            if (pcount > 0) {
                String jsonVal = result.getProperty(0).toString();
                if (jsonVal.equals("anyType{}")) {
                    return cotexts;
                }
                JSONObject jsonObject = new JSONObject(jsonVal);
                JSONArray jsonArray = jsonObject.getJSONArray("ChannelInfo");
                cotexts = (ArrayList<NewContext>) JSON.parseArray(jsonArray.toString(), NewContext.class);
                System.out.println(cotexts);
                PrefUtils.saveNewContext(cotexts.toString());
                return cotexts;
            }
        } catch (Exception e) {
//            cotexts = db.listNewContext(channelName);
        }
        return cotexts;
    }

    /**
     * 查询办事指南
     *
     * @param zhinan 事项id
     * @return
     * @throws Exception
     */
    public static Project queryZhinan(String zhinan) throws Exception {
        Project p = new Project();
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListProjectSummaryByProjectIDToAndroid");
        request.addProperty("projectid", zhinan);
        addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url);
        trans.debug = true;
        trans.call(
                "http://tempuri.org/ISPService/ListProjectSummaryByProjectIDToAndroid",
                envelope);
        SoapObject result = (SoapObject) envelope.bodyIn;
        String jsonVal = result.getProperty(0).toString();
        JSONObject jsonO = new JSONObject(jsonVal);
        JSONObject jobj = jsonO.getJSONObject("ProjectSummary");
        p = JSON.parseObject(jobj.toString(), Project.class);
        return p;
    }

    public static boolean comment(String userid, int feedback, String remark) throws Exception {
        boolean flag;
        SoapObject request = new SoapObject("http://webservice.egs.lilosoft.com/",
                "saveOrUpdateForAGM");
        request.addProperty("userId", userid);
        request.addProperty("feedback", feedback);
        request.addProperty("remark", remark);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        HttpTransportSE trans = new HttpTransportSE(sgoUrl + "/feedBackNewService?wsdl");
        trans.debug = true;
        trans.call(null,
                envelope);
        SoapObject result = (SoapObject) envelope.bodyIn;
        String jsonVal = result.getProperty(0).toString();
        int code = JSON.parseObject(jsonVal).getInteger("code");
        return flag = code == 0 ? true : false;
    }

    public static Map<String, String> getVersion(String urlStr) {
        Map<String, String> result = null;
        try {
            URL urlxml = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) urlxml
                    .openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            if (code == 200) {
                XmlPullParser parser = Xml.newPullParser();
                InputStream in = conn.getInputStream();
                parser.setInput(in, "UTF-8");
                int type = parser.getEventType();
                result = new HashMap<String, String>();
                while (type != XmlPullParser.END_DOCUMENT) {
                    switch (type) {
                        case XmlPullParser.START_TAG:
                            if ("version".equals(parser.getName())) {
                                result.put("version", parser.nextText());
                            } else if ("url".equals(parser.getName())) {
                                result.put("url", parser.nextText());
                            } else if ("name".equals(parser.getName())) {
                                result.put("name", parser.nextText());
                            }
                            break;
                        default:
                            break;
                    }
                    type = parser.next();
                }
            }
        } catch (Exception e) {
            // String strErr = e.getMessage();
            // e.printStackTrace();
        }
        return result;
    }

}