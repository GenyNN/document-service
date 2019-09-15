package bss.ServiceDocument.Sender;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SenderResponse {
	private String fileName;
	private int code;
	private String description;
	private Date timeStamp;
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getFileName() {
		return fileName;
	}
	@XmlElement
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getCode() {
		return code;
	}
	@XmlElement
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
