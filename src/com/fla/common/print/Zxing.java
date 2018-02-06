package com.fla.common.print;

import java.io.File;
import java.util.Hashtable;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
 
public class Zxing {
    public static void main(String[] args) throws Exception {
        String text = "http://www.csyor.com";
        int width = 250;
        int height = 250;
        // 二维码的图片格式
        String format = "gif";
        Hashtable<EncodeHintType,String> hints = 
                                      new Hashtable<EncodeHintType,String>();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter()
                  .encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        File outputFile = new File("d:" + File.separator + "csyor.com.gif");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    }
}