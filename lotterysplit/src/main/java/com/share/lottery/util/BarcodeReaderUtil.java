package com.share.lottery.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public final class BarcodeReaderUtil {
	   
	public static String read(byte[] image) throws IOException, NotFoundException, ChecksumException, FormatException{
		InputStream barCodeInputStream = new ByteArrayInputStream(image);
		BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
		
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		Result result = reader.decode(bitmap);
		System.out.println("Barcode text is " + result.getText());
		
		return result.getText();
	}
	
}
