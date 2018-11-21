package com.ybkj.demo.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Rongkui.xiao on 2017/3/16.
 * 此类提供I/O流操作的一般方法
 */

public class IOUtlis {
    private static IOUtlis instance;

    IOUtlis() {
    }

    public static IOUtlis getInstance() {
        if (instance == null) {
            synchronized (IOUtlis.class) {
                if (instance == null) instance = new IOUtlis();
            }
        }
        return instance;
    }


    /**
     * 读取指定文件内容
     */
    public static String readFile(String filePath) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filePath);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            String s = new String(buffer, "UTF-8");
//            String res = EncodingUtils.getString(buffer, "UTF-8");
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeStream(fin);
        }
    }

    /**
     * 将html文件保存到sd卡
     */
    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeStream(fos);
            closeStream(bw);
        }
    }

    /**
     * 根据输入流，保存文件
     *
     * @param file
     * @param is
     * @return
     */
    static boolean writeFile(File file, InputStream is) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
            return true;
        } catch (Exception e) {
            if (file != null && file.exists()) {
                file.deleteOnExit();
            }
            e.printStackTrace();
        } finally {
            closeStream(os);
            closeStream(is);
        }
        return false;
    }

    /**
     * 追加内容
     *
     * @param filePath   filename
     *                   文件名
     * @param contentStr 文件内容
     */
    public static void appendFile(String filePath, String contentStr) {

        if (TextUtils.isEmpty(contentStr)) {
            contentStr = "\n\n\n";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            String nowTimer = dateFormat.format(curDate);
            contentStr = nowTimer + " " + contentStr + "\n";
        }
        FileOutputStream out = null;
        try {
            File file = new File(filePath);
            out = new FileOutputStream(file, true);
            out.write((contentStr).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(out);
        }
    }

    /**
     * 读取配置文件的json串,有中文符不会出现乱码
     */
    public static String readFileContent(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }

        InputStream in = null;
        byte[] buffer = null;
        try {
            in = new FileInputStream(fileName);
            int totalLen = in.available();
            buffer = new byte[totalLen];
            int readLen = in.read(buffer);
            in.close();
            in = null;
            if (readLen != totalLen) {
                Log.e("1027", "readFileContent(" + fileName + ") failed, read(" + totalLen + ") but return " +
                        readLen); // parasoft-suppress BD.PB.STRNULL "null object can output to log"
                return null;
            }
            String charSet = getCharset(fileName, buffer);
            String ret = new String(buffer, charSet);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeStream(in);
        }
    }

    private static String getCharset(String fileName, byte[] buffer) {
        String charset = "GBK";

        if (buffer.length <= 3) {
            return charset; // 文件编码为 ANSI
        } else if (buffer[0] == (byte) 0xFF && buffer[1] == (byte) 0xFE) {
            return "UTF-16LE"; // 文件编码为 Unicode
        } else if (buffer[0] == (byte) 0xFE && buffer[1] == (byte) 0xFF) {
            return "UTF-16BE"; // 文件编码为 Unicode big endian
        } else if (buffer[0] == (byte) 0xEF && buffer[1] == (byte) 0xBB && buffer[2] == (byte) 0xBF) {
            return "UTF-8"; // 文件编码为 UTF-8
        }
        int indx;
        for (indx = 0; indx + 1 < buffer.length; ) {
            int curChar = (buffer[indx++] & 0xff);
            if (curChar >= 0xF0) {
                break;
            } else if (0x80 <= curChar && curChar <= 0xBF) {
                // 单独出现BF以下的，也算是GBK
                break;
            } else if (0xC0 <= curChar && curChar <= 0xDF) {
                curChar = (buffer[indx++] & 0xff);
                if (0x80 <= curChar && curChar <= 0xBF) {
                    // 双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
                    continue;
                } else {
                    break;
                }
            } else if (0xE0 <= curChar && curChar <= 0xEF) {
                // 也有可能出错，但是几率较小
                curChar = (buffer[indx++] & 0xff);
                if (0x80 <= curChar && curChar <= 0xBF) {
                    curChar = (buffer[indx++] & 0xff);
                    if (0x80 <= curChar && curChar <= 0xBF) {
                        charset = "UTF-8";
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return charset;
    }

    public static void copyAssetsToSD(String assetFileName, Context ctx) {
        File file = new File(Environment.getExternalStorageDirectory(), assetFileName);
        copyAssetsToSD(assetFileName, file.getAbsolutePath(), ctx);
    }

    /**
     * 从资产目录拷贝一个文件到SDCARD
     *
     * @param assetFileName 文件名
     * @param url           拷贝到指定路径,完整路径
     * @param ctx
     */
    public static void copyAssetsToSD(String assetFileName, String url, Context ctx) {
        LogUtil.i("1027", "asset start!" + ";assetFileName=" + assetFileName + ";url=" + url);
        // 获取资产管理器
        InputStream in = null;
        OutputStream out = null;
        try {
            in = ctx.getResources().getAssets().open(assetFileName);
            out = new FileOutputStream(new File(url));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 创建文件目录
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 删除文件或文件夹
     *
     * @param file
     */
    static void deleteFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                deleteFile(f);
                            } else {
                                f.delete();
                            }
                        }
                    }
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * 写入文件
//     *
//     * @param file
//     * @param info
//     * @throws IOException
//     */
//    public static void writeCache(ResponseBody responseBody, File file, DownEvent info) {
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        long allLength;
//        if (info.getCountLength() == 0) {
//            allLength = responseBody.contentLength();
//        } else {
//            allLength = info.getCountLength();
//        }
//        FileChannel channelOut = null;
//        RandomAccessFile randomAccessFile = null;
//        try {
//            randomAccessFile = new RandomAccessFile(file, "rwd");
//            channelOut = randomAccessFile.getChannel();
//            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, info.getReadLength(),
//                    allLength - info.getReadLength());
//            byte[] buffer = new byte[1024 * 8];
//            int len;
//            int record = 0;
//            while ((len = responseBody.byteStream().read(buffer)) != -1) {
//                mappedBuffer.put(buffer, 0, len);
//                record += len;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            closeStream(responseBody.byteStream());
//            closeStream(channelOut);
//            closeStream(randomAccessFile);
//        }
//    }

    /**
     * 关闭流
     *
     * @param closeable
     */
    static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从资产目录解压一个文件到sd卡
     */
    public void unZip(Context context, String assetName, String savefilename) {
        // 创建解压目标目录
        File file = new File(Environment.getExternalStorageDirectory(), savefilename);
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            inputStream = context.getAssets().open(assetName);
//      inputStream =getClass().getResourceAsStream(assetName;//也可以进行流解读
            zipInputStream = new ZipInputStream(inputStream);
            // 读取一个进入点
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            byte[] buffer = new byte[1024 * 1024];
            int count = 0;
            // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (nextEntry != null) {
                // 如果是一个文件夹
                if (nextEntry.isDirectory()) {
                    file = new File(savefilename + File.separator + nextEntry.getName());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                } else {
                    // 如果是文件那就保存
                    file = new File(savefilename + File.separator + nextEntry.getName());
                    // 则解压文件
                    if (!file.exists()) {
                        file.createNewFile();
                        FileOutputStream fos = new FileOutputStream(file);
                        while ((count = zipInputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, count);
                        }
                        fos.close();
                    }
                }

                //这里很关键循环解读下一个文件
                nextEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(zipInputStream);
        }

    }
}
