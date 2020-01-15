package com.java.ff;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String host = "192.168.1.125";
        int port = 14821;
        String username = "ftpuser";
        String password = "ftpuser";
        String basePath = "home/ftpusers/";
        String filePath = "";
        String filename = "mysql.txt";
        File file = new File("d:\\mysql.txt");
        InputStream input = null;
        try{
            input = new FileInputStream(file);
            boolean flag = uploadFile(host, port, username, password, basePath, filePath, filename, input);
            System.out.println("ftp上传返回："+flag);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input){
        boolean result = false;
        System.out.println("host:" + host);
        System.out.println("port:" + port);
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("filePath:" + filePath);
        System.out.println("filename:" + filename);
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            if (!ftp.isConnected()) {
                ftp.connect(host, port);// 连接FTP服务器
            }
            System.out.println("登录ftp");
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            boolean flag_login = ftp.login(username, password);// 登录
            System.out.println("登录状态："+flag_login);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("ftp链接失败！");
                return result;
            }
            System.out.println("ftp链接状态码："+reply);
            System.out.println("设置上传文件类型为二进制");
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            System.out.println("设置工作模式");
            ftp.enterLocalPassiveMode(); //设置工作模式被动模式
            System.out.println("切换上传路径");
            //切换到上传目录
            filePath = "wzs/" + filePath;
            ftp.changeWorkingDirectory(basePath);
//            if (!ftp.changeWo
//            System.out.println("ftp状态："+ftp.getStatus());

            //上传文件
            if (ftp.storeFile(filename, input)) {
                System.out.println("上传成功！");
                result = true;;
            }else{
                result = false;
            }
            input.close();
            ftp.logout();
        } catch (IOException e) {
            result = false;
            System.out.println("上传失败1");
            e.printStackTrace();
        } catch (Exception e){
            result = false;
            System.out.println("上传失败2");
            e.printStackTrace();
        }finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();//断开 fpt 连接
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            return result;
        }
    }


}
