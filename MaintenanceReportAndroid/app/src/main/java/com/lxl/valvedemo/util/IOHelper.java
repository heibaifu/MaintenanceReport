package com.lxl.valvedemo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IOHelper {

    /**
     * @return
     */
    public static void fromIsToOsByCode(InputStream is, OutputStream os,
                                        String incode, String outcode) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, incode));
            writer = new BufferedWriter(new OutputStreamWriter(os, outcode));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String readStrByCode(InputStream is, String code)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;

        reader = new BufferedReader(new InputStreamReader(is, code));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        try {
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String fromIputStreamToString(InputStream is) {
        return fromIputStreamToString(is, "utf-8");
    }

    public static String fromIputStreamToString(InputStream is, String code) {
        if (is == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        try {
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] lens = baos.toByteArray();
        String result = null;
        try {
            result = new String(lens, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> readListStrByCode(InputStream is, String code)
            throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader reader = null;

        reader = new BufferedReader(new InputStreamReader(is, code));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        try {
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public static String listToStr(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (String line : list) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static boolean fromIputStreamToFile(InputStream is,
                                               String outfilepath) {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;

        try {
            inBuff = new BufferedInputStream(is);

            outBuff = new BufferedOutputStream(
                    new FileOutputStream(outfilepath));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (inBuff != null)

                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static InputStream fromFileToIputStream(String infilepath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(infilepath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fis;
    }

    public static InputStream fromFileToIputStream(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fis;
    }

    public static InputStream fromStringToIputStream(String s) {
        if (s != null && !s.equals("")) {
            try {

                ByteArrayInputStream stringInputStream = new ByteArrayInputStream(
                        s.getBytes());
                return stringInputStream;
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    public static InputStream getInputStreamFromUrl(String urlstr) {
        try {
            InputStream is = null;
            HttpURLConnection conn = null;
            System.out.println("urlstr:" + urlstr);
            URL url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return is;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static void writerStrByCode(OutputStream os, String outcode,
                                       String str) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(os, outcode));
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writerStrByCodeToFile(File file, String str) {
        writerStrByCodeToFile(file, "UTF-8", false, str);
    }


    public static void writerStrByCodeToFile(File file, String outcode,
                                             boolean type, String str) {
        BufferedWriter writer = null;
        try {
            FileOutputStream out = new FileOutputStream(file, type);
            writer = new BufferedWriter(new OutputStreamWriter(out, outcode));
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkParent(File outFile) throws IOException {
        File parentFile = outFile.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
        if (outFile.exists()) {
            outFile.delete();
        }
        return true;
    }


    public static boolean coyyFile(InputStream in, File outFile) throws IOException {
        checkParent(outFile);
        int length = 2048;
        FileOutputStream out = new FileOutputStream(outFile);
        byte[] buffer = new byte[length];
        while (true) {
            int ins = in.read(buffer);
            if (ins == -1) {
                in.close();
                out.flush();
                out.close();
                return true;
            } else {
                out.write(buffer, 0, ins);
            }
        }
    }
}
