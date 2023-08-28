package util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Distinguish{
    
public static void docmd(String py_path, String catpath1, String catpath2) {
    String[] command = { "cmd", };
    Process p = null;
    try {
        p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        /** 以下可以输入自己想输入的cmd命令 */
        stdin.println("cd "+py_path);
        stdin.println("python "+py_path+"/final_test.py "+catpath1+" "+catpath2);
        //此处自行填写，切记有空格，跟cmd的执行语句一致。
        //比如这里cmd里面是：python C:/Users/yanyixiao/Desktop/mycat/final-test.py C:/Users/yanyixiao/Desktop/mycat/cat-photo/09.jpg C:/Users/yanyixiao/Desktop/mycat/cat-photo/02.jpg 
        stdin.close();
        p.waitFor();
        
        } catch (Exception e) {
            throw new RuntimeException("编译出现错误：" + e.getMessage());
        }

        // String logpath="C:/Users/yanyixiao/Desktop/mycat/testjava/resualt.log";//log文件的路径

        // String resualt = getlog(logpath);
        
        // System.out.println(resualt);
    }

    public static String getlog(String filepath)
    {
        String line = "";
        //以下是读取log文件中的结果
        try {
            FileInputStream is = new FileInputStream(filepath);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            try {
                line = br.readLine();
                br.close();
            } catch (IOException e) {
             e.printStackTrace();
             System.out.println("读取数据时出错");
            }
           } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件读取路径错误FileNotFoundException");
           }
           return line;
    }

}

class SyncPipe implements Runnable {

    private final OutputStream ostrm_;
    private final InputStream istrm_;
    public SyncPipe(InputStream istrm, OutputStream ostrm) {
        istrm_ = istrm;
        ostrm_ = ostrm;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
            ostrm_.write(buffer, 0, length);
            }
        } catch (Exception e) {
            throw new RuntimeException("处理命令出现错误：" + e.getMessage());
            }
        }
}
