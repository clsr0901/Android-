package cn.humiao.myserialport.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chenliangsen on 2017/9/13.
 */

public class MD5Util {

    public static String enMd(String sourceString) {

        StringBuilder stringBuilder = new StringBuilder();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            for (int i = 0; i < 10000; i++)
            {
                byte[] source = sourceString.getBytes("utf-8");
                source = md5.digest(source);
                sourceString = StringUtils.bytesToHexString(source);
                //Console.WriteLine("{0}\t{1}", i, sourceString);
            }
        } catch (NoSuchAlgorithmException e) {
        }catch (UnsupportedEncodingException e) {
        }
        return sourceString;
    }

    public static String verifyInstallPackage(String filePath) {
        try {
            MessageDigest sig = MessageDigest.getInstance("MD5");
            File packageFile = new File(filePath);
            InputStream signedData = new FileInputStream(packageFile);
            byte[] buffer = new byte[4096];//每次检验的文件区大小
            long toRead = packageFile.length();
            long soFar = 0;
            boolean interrupted = false;
            while (soFar < toRead) {
                interrupted = Thread.interrupted();
                if (interrupted) break;
                int read = signedData.read(buffer);
                soFar += read;
                sig.update(buffer, 0, read);
            }
            byte[] digest = sig.digest();
            String digestStr = bytesToHexString(digest);//将得到的MD5值进行移位转换
           return digestStr.toLowerCase();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    //bytesToHexString MD5值移位转换
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        int i = 0;
        while (i < src.length) {
            int v;
            String hv;
            v = (src[i] >> 4) & 0x0F;
            hv = Integer.toHexString(v);
            stringBuilder.append(hv);
            v = src[i] & 0x0F;
            hv = Integer.toHexString(v);
            stringBuilder.append(hv);
            i++;
        }
        return stringBuilder.toString();
    }

    public static String enBytes(Bitmap bmp){
        int bytes = bmp.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);

        byte[] src = buf.array();
        return  enMd(bytesToHexString(src));
    }

    public static String enByte(byte[] buffers) {
        MessageDigest md5 = null;
        String digestStr = "";
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(buffers, 0, buffers.length);
            byte[] digest = md5.digest();
            digestStr = bytesToHexString(digest);//将得到的MD5值进行移位转换
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digestStr.toUpperCase();
    }

}
