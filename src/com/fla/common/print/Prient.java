package com.fla.common.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
  
public class Prient implements Printable {  
	private static final String PRINT_MCH_NAME = "2120TF";

	public String content;
	
	public Prient(String content) {
		this.content = content;
	}

	@Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {  
            return NO_SUCH_PAGE;  
        } 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		BufferedImage bi = getBufferedImageByContent(this.getContent());
        Graphics2D graphics = (Graphics2D) g;
        graphics.setFont(getFont("Default",Font.BOLD, 10));
        graphics.drawString("源信幸福快递", 55, 16);
        graphics.setFont(getFont("Default",Font.PLAIN, 7));
        graphics.drawString("-----------------------------------------------", 25, 30);  
        graphics.drawImage(bi, 25,30,null);
        graphics.setFont(getFont("Default",Font.PLAIN, 7));  
        graphics.drawString("-----------------------------------------------", 25, 145);  
        graphics.drawString("*打印时间:" +dateStr+"*", 50, 155);
        graphics.setFont(getFont("Default",Font.ITALIC, 7));  
        graphics.drawString("注意二维码仅当天有效", 50, 165);
        return PAGE_EXISTS;
    }
	
	public BufferedImage getBufferedImageByContent(String content) {
		int qrcodeWidth = 118;
        int qrcodeHeight = 118;
        HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		MultiFormatWriter mfw = new MultiFormatWriter();
		BitMatrix bitMatrix = null;
		BufferedImage bufferedImage = null;
		try 
		{
			bitMatrix = mfw.encode(content, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);
			bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bufferedImage;
	}
	
	public Font getFont(String font,int style,int fontSize) {
		return new Font(font, style, fontSize);
	}
	
	public static Book getBook(String content){
		int height = 175 + 3 * 15+20;  
        // 通俗理解就是书、文档  
        Book book = new Book();  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT); // LANDSCAPE表示竖打;PORTRAIT表示横打;REVERSE_LANDSCAPE表示打印空白
//        pf.setOrientation(PageFormat.LANDSCAPE);
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(230, height);  
        p.setImageableArea(5, -20, 230, height+10);
        pf.setPaper(p);  
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        book.append(new Prient(content), pf);
        return book;
	}
  
    public static void main(String[] args) {  
    	String content ="nihao";
    	Book book = getBook(content);
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(PRINT_MCH_NAME);
        job.setPageable(book);  
        try 
        {
            job.print();  
        } catch (PrinterException e) {  
            System.out.println("================打印出现异常");  
        }  
  
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}  
  
} 