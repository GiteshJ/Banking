package com.banking.reportGenerators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class BankStatementGenerator {

	@SuppressWarnings("unused")
	public static ByteArrayInputStream bankStatementPdfReport(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc,out);
			Paragraph header = new Paragraph();
			header.add("Bank Statement");
			doc.open();
			doc.add(header);
			doc.close();
		}
		catch(Exception e) {
			
		}
		return new ByteArrayInputStream(out.toByteArray());
		
	}
	
}
