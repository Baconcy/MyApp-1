package project.wy.com.myappdemo.untils;


import android.graphics.Bitmap;

import java.io.File;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import project.wy.com.myappdemo.R;
import project.wy.com.myappdemo.http.HttpCallback;
import project.wy.com.myappdemo.http.OkHttpRequest;
import project.wy.com.myappdemo.http.Platform;
import project.wy.com.myappdemo.http.ResultDesc;

public class OkhttpUtils {

    private static OkhttpUtils mInstance;
    private static OkHttpClient mOkHttpClient;
    private static Platform mPlatform;

    private OkhttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }

    public static OkhttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkhttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkhttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkhttpUtils getInstance() {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static void sendFailResultCallback(final int code, final String message, final HttpCallback callback) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(code, message);
            }
        });
    }

    public static void sendSuccessResultCallback(final ResultDesc result, final HttpCallback callback) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(result);
            }
        });
    }

    public static void sendProgressResultCallback(final long currentTotalLen, final long totalLen, final HttpCallback callback) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onProgress(currentTotalLen, totalLen);
            }
        });
    }

    public static void sendBitmapSuccessResultCallback(final Bitmap bitmap, final HttpCallback callback) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onBitmapSuccess(bitmap);
            }
        });
    }

    /**--------------------    同步数据请求    --------------------**/

    /**
     * @param url      请求地址
     * @param callback 请求回调
     * @Description GET请求
     */
    public static void getSync(String url, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.GET, url, null, null);
        OkHttpRequest.doExecute(request, callback);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description GET请求
     */
    public static void getSync(String url, Map<String, String> params, HttpCallback callback) {
        if (params != null && !params.isEmpty()) {
            url = OkHttpRequest.appendGetParams(url, params);
        }
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.GET, url, null, null);
        OkHttpRequest.doExecute(request, callback);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description POST请求
     */
    public static void postSync(String url, Map<String, String> params, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, params, null);
        OkHttpRequest.doExecute(request, callback);
    }

    /**--------------------    异步数据请求    --------------------**/

    /**
     * @param url      请求地址
     * @param callback 请求回调
     * @Description GET请求
     */
    public static void getAsyn(String url, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.GET, url, null, null);
        OkHttpRequest.doEnqueue(request, callback);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description GET请求
     */
    public static void getAsyn(String url, Map<String, String> params, HttpCallback callback) {
        if (params != null && !params.isEmpty()) {
            url = OkHttpRequest.appendGetParams(url, params);
        }
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.GET, url, null, null);
        OkHttpRequest.doEnqueue(request, callback);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description POST请求
     */
    public static void postAsyn(String url, Map<String, String> params, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, params, null);
        OkHttpRequest.doEnqueue(request, callback);
    }

    /**
     * @param url      请求地址
     * @param json     json数据格式
     * @param callback 请求回调
     * @Description POST提交JSON数据
     */
    public static void postAync(String url, String json, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, null, json);;
        OkHttpRequest.doEnqueue(request, callback);
    }

    /**--------------------    文件上传    --------------------**/

    /**
     * @param url      请求地址
     * @param file     上传文件
     * @param callback 请求回调
     * @Description 单文件上传
     */
    public static void postAsynFile(String url, File file, HttpCallback callback) {
        if (!file.exists()) {
            ToastUtil.showText(UIUtils.getString(R.string.file_does_not_exist));
            return;
        }
        Request request = OkHttpRequest.builderFileRequest(url, file, null, null, null, callback);
        OkHttpRequest.doEnqueue(request, callback);
    }

    /**
     * @param url      请求地址
     * @param pic_key  上传图片关键字（约定pic_key如“upload”作为后台接受多张图片的key）
     * @param files    上传文件集合
     * @param params   请求参数
     * @param callback 请求回调
     * @Description 多文件上传
     */
    public static void postAsynFiles(String url, String pic_key, List<File> files, Map<String, String> params, HttpCallback callback) {
        Request request = OkHttpRequest.builderFileRequest(url, null, pic_key, files, params, callback);
        OkHttpRequest.doEnqueue(request, callback);
    }

    /**--------------------    文件下载    --------------------**/

    /**
     * @param url          请求地址
     * @param destFileDir  目标文件存储的文件夹路径，如：Environment.getExternalStorageDirectory().getAbsolutePath()
     * @param destFileName 目标文件存储的文件名，如：gson-2.7.jar
     * @param callback     请求回调
     * @Description 文件下载
     */
    public void downloadAsynFile(String url, String destFileDir, String destFileName, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.GET, url, null, null);
        OkHttpRequest.doDownloadEnqueue(request, destFileDir, destFileName, callback);
    }

    /**
     * @param url          请求地址
     * @param destFileDir  目标文件存储的文件夹路径
     * @param destFileName 目标文件存储的文件名
     * @param params       请求参数
     * @param callback     请求回调
     * @Description 文件下载
     */
    public void downloadAsynFile(String url, String destFileDir, String destFileName, Map<String, String> params, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, params, null);
        OkHttpRequest.doDownloadEnqueue(request, destFileDir, destFileName, callback);
    }

    /**--------------------    图片显示    --------------------**/

    /**
     * @param url      请求地址
     * @param callback 请求回调
     * @Description 图片显示
     */
    public static void displayAsynImage(String url, HttpCallback callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.GET, url, null, null);
        OkHttpRequest.doDisplayEnqueue(request, callback);
    }




}

