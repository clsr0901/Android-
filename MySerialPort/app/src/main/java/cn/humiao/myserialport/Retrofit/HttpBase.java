package cn.humiao.myserialport.Retrofit;


import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenliangsen on 2017/6/26.
 */

public class HttpBase {
private static String URL = "http://192.168.200.37:9999/";
    public static String TOKEN = "";
    public static String MD5 = "";
    public static String EXT = "";
    public static int TIMEOUT = 120;
    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static OkHttpClient client;

    public static String getTOKEN() {
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        HttpBase.TOKEN = TOKEN;
    }

    public static String getURL() {
        return URL;
    }

    public static OkHttpClient getClient(){
        if (client == null){
//            getSafeClient();
            getRetrofitNotSSL();
        }
        return client;

    }

    public static void setURL(String URL) {
        HttpBase.URL = URL;
    }

    public static String getMD5() {
        return MD5;
    }

    public static void setMD5(String MD5) {
        HttpBase.MD5 = MD5;
    }

    public static String getEXT() {
        return EXT;
    }

    public static void setEXT(String EXT) {
        HttpBase.EXT = EXT;
    }

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URL)
                .build();
        return retrofit;
    }




    //信任Https所有证书
    public static Retrofit getRetrofitNotSSL(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (TOKEN == "") {
//                    EventBus.getDefault().post(new ReLoginModel());
                    return chain.proceed(originalRequest);
                }
                Request authorised = originalRequest.newBuilder()
                        .header("token", TOKEN)
                        .addHeader("md5", MD5)
                        .addHeader("ext", EXT)
                        .build();
                return chain.proceed(authorised);
            }
        }).addInterceptor(logging)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier());
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URL)
                .build();
        return retrofit;
    }
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
