package bss.ServiceDocument.Net;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;

import bss.ServiceDocument.Transport.MessageTransport.Statuses;

public class SmtpServerConection {
	
	private Properties props = null;
	private Session session = null;
	private Transport transport = null;
	private String host;
	private Integer port;
	private final String userName; 
	private final String password;
	
	private String emailFrom;
	private String nameFrom;
	
	
	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getNameFrom() {
		return nameFrom;
	}

	public void setNameFrom(String nameFrom) {
		this.nameFrom = nameFrom;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Properties getProps() {
		return props;
	 
	}
	
	 
	public void setProps(Properties props) {
		this.props = props;
	 
	}
	 
	 
	 
	 public Session getSession() {
		return session;
	 }
	 
	 
	 public void setSession(Session session) {
		this.session = session;
	 }
	
	 public Transport getTransport() {
		return transport;
	 }
	 
	 public void setTransport(Transport transport) {
		this.transport = transport;
	 }
	
	 public SmtpServerConection(String host, int port, final String userName, final String password, String emailFrom, String nameFrom){	 
		 this.host = host;
		 this.port = port;
		 this.userName = userName;
		 this.password = password;
		 this.emailFrom = emailFrom;
		 this.nameFrom = nameFrom;
		 
		 props = new Properties();
		 // required for gmail   
		 //props.put("mail.smtp.starttls.enable","true");
		 props.put("mail.smtp.auth", "true");
		 props.put("mail.smtp.host", host);
		 props.put("mail.smtp.port", port);
		 
		 props.put("mail.smtp.submitter", userName);
		 session = Session.getInstance(props,  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}	
		 });
		 
		 try {
			transport = session.getTransport("smtp");
		 } catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 public boolean connect(){
		 boolean isConnected = false;
		 try {
			transport.connect();
			isConnected = true;
		 } 
		 catch (MessagingException e) {
			e.printStackTrace();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return isConnected;
	 }
	 public boolean disConnect(){
		 boolean isDisConnected = false;
		 try {
			transport.close();
			isDisConnected = true;
		 } 
		 catch (MessagingException e) {
			e.printStackTrace();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return isDisConnected;
	 }
	 public boolean isConnected(){
		 return transport.isConnected();
	 }
	 public void Close(){
		try {
			transport.close();
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	 
	 }
	 public Statuses Send(Message mes) throws Exception{
		
		 
		boolean isSent = false;
		Statuses resCode = Statuses.OK;
		try {
			Transport.send(mes);
			
			isSent = true;
		}
		catch(SendFailedException e){
			resCode = Statuses.SENDING_ERROR_BAD_MAIL;
			e.printStackTrace();
		}
		catch (MessagingException e) {
			resCode = Statuses.SENDING_ERROR;
			e.printStackTrace();
		}
		catch (Exception e) {
			resCode = Statuses.SENDING_ERROR;
			e.printStackTrace();
		}
		
		return resCode;
	 }
	 
	 public Session getPreparedSession() {
			Authenticator authenticator = new Authenticator(userName, password);
			
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
			properties.setProperty("mail.smtp.auth", "true");

			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.port", port.toString());
			
			return Session.getInstance(properties, authenticator);	
	 }
	 
	 private class Authenticator extends javax.mail.Authenticator {
			private PasswordAuthentication authentication;

			public Authenticator(String userName, String passwd) {
				authentication = new PasswordAuthentication(userName, passwd);
			}

			protected PasswordAuthentication getPasswordAuthentication() {
				return authentication;
			}	
	 }
}
