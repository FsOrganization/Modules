package com.fla.common.barcode;

import java.io.File;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

public class PrintingExample
{

	public static void main(String[] args)
	{
		try
		{
			Barcode b = BarcodeFactory.createEAN128("3323 234234 234234 ");
//			b = BarcodeFactory.createEAN13("12564855125");
//			b= BarcodeFactory.createEAN128("23423423");
//			BarcodeFactory.createUSD3(arg0, arg1)
//			BarcodeFactory.createMonarch(arg0)
//			PrinterJob job = PrinterJob.getPrinterJob();
//			job.setPrintable(b);
			File file = File.createTempFile("tmp", ".png");
			BarcodeImageHandler.savePNG(b, file);
//			if (job.printDialog())
//			{
//				job.print();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
