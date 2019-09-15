package bss.ServiceDocument.Transport;

import bss.ServiceDocument.Net.SmtpServerConection;

public interface IDocumentFileTransport {
	public MessageTransport convertDocumentToMessage(SmtpServerConection connnection);
	public byte[] getFileContent();
	public void setFileContent(byte[] fileContent);
	public MessageTransport.Statuses getStatus();
	public void setStatus(MessageTransport.Statuses status);
	public boolean checkValid();
	public Integer getInterval();
	public void setInterval(Integer interval);
	public Integer getQuantToSend();
	public void setQuantToSend(Integer quantToSend);
	public void setFileName(String name);
	public String getFileName();
}
