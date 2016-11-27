package com.qrplus;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/10/30
 */
public class FileHelper {

    public String getAdministratorPath() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        return String.valueOf(fsv.getHomeDirectory());
    }

    public void createOther() throws Exception {
        String administratorPath = getAdministratorPath();
        String fileName = getCurrentTime();
        String code = new CodeHelper().getMacAddress();
        String encrypt = string2MD5(code);
        File file = new File(administratorPath + "/" + fileName + ".txt");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        file.mkdirs();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(encrypt);
        writer.flush();
        writer.close();
    }

    public void createMac() throws Exception {
        String code = new CodeHelper().getMacAddress();
        String encrypt = string2MD5(code);
        File f = new File(getAbsPath() + "/data/.k.tell");
        File fDir = new File(getAbsPath() + "/data");
        if (!fDir.exists()) {
            fDir.mkdirs();
        }
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
        BufferedWriter w = new BufferedWriter(new FileWriter(f));
        w.write(encrypt);
        w.flush();
        w.close();
    }

    public String getAbsPath() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String administratorPath = String.valueOf(fsv.getHomeDirectory());
        int i = administratorPath.lastIndexOf("\\");
        return administratorPath.substring(0, i);
    }

    public String string2MD5(String inStr) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }


}
