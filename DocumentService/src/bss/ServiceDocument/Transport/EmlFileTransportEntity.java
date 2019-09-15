package bss.ServiceDocument.Transport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bss.ServiceDocument.Net.SmtpServerConection;

public class EmlFileTransportEntity implements IDocumentFileTransport{
	
	private String fileName;
	private MessageTransport.Statuses status;
	private Integer interval;
	private Integer quantToSend;
	
	private byte[] fileContent;

	public EmlFileTransportEntity() {
		super();
	}
	
	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getQuantToSend() {
		return quantToSend;
	}
	public void setQuantToSend(Integer quantToSend) {
		this.quantToSend = quantToSend;
	}
	public MessageTransport.Statuses getStatus() {
		return status;
	}
	public void setStatus(MessageTransport.Statuses status) {
		this.status = status;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public MessageTransport convertDocumentToMessage(SmtpServerConection connection){
		MessageTransport emlmessage = new MessageTransport();   
		
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(getFileContent());
			Message message;
			message = new MimeMessage(connection.getPreparedSession(), bis);
			
			Address addres;
			try {
				message.setFrom(new InternetAddress(connection.getEmailFrom(), connection.getNameFrom()));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			emlmessage.setMessage(message);
			emlmessage.setFileName(fileName);
			emlmessage.setStatus(status);
			emlmessage.setInterval(interval);
			emlmessage.setQuantityToSend(quantToSend);
			emlmessage.setFileContent(getFileContent());
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		return emlmessage;
	}
	public boolean checkValid(){
		boolean bRes = false;
		ByteArrayInputStream bis = new ByteArrayInputStream(getFileContent());
			Session s = Session.getDefaultInstance(new Properties());
			try {
				
				MimeMessage message = new MimeMessage(s, bis);
				String subject = message.getSubject();
				Address adr = null;
				if (message.getFrom() != null){
					if(message.getFrom().length > 0){
						adr = message.getFrom()[0];
					}
				}
				Address rcpt = null;
				if(message.getRecipients(RecipientType.TO) != null){
					if(message.getRecipients(RecipientType.TO).length > 0 ){
						rcpt = message.getRecipients(RecipientType.TO)[0];
					}
				}
				Object cont = null;
		        try {
		        	cont = message.getContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        if (subject != null && adr != null && rcpt != null && cont != null ){
		        	bRes = true;
		        }
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return bRes;
	}
	
}
