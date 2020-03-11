package com.awen.codebase.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 分辨率适配生成value-800x480文件
 *
 * Created by Administrator on 2017/12/12.
 */

public class ResolutionUtil {
    private final static String rootPath = "/Users/Downloads/value_root/values-{0}x{1}";


    //标准比例dw与dh
    private final static float dw = 320f;
    private final static float dh = 480f;

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    public static void makeString(int w, int h) {

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");
        float cellw =w / dw;
        for (int i = 1; i < 320; i++) {
            sb.append(WTemplate.replace("{0}",i + "").replace("{1}",
                    change(cellw * i) + ""));
        }
        sb.append(WTemplate.replace("{0}","320").replace("{1}", w + ""));
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");
        float cellh =h / dh;
        for (int i = 1; i < 480; i++) {
            sb2.append(HTemplate.replace("{0}",i + "").replace("{1}",
                    change(cellh * i) + ""));
        }
        sb2.append(HTemplate.replace("{0}","480").replace("{1}", h + ""));
        sb2.append("</resources>");

        String path = rootPath.replace("{0}",h + "").replace("{1}",w + "");
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File layxFile = new File(path,"lay_x.xml");
        File layyFile = new File(path,"lay_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sb.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sb2.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }
}
