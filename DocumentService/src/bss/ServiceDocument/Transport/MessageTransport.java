package bss.ServiceDocument.Transport;

import javax.mail.Message;

public class MessageTransport {
	public enum Statuses{
		OK("Успешно"), SENDING_ERROR_BAD_MAIL("Не корректный адресс получателя"), SENDING_ERROR("Ошибка отсылки"), /* READING_SRC_ERROR("Ошибка чтения исходного файла"),*/ PARSING_SRC_ERROR("Ошибка парсига исходного файла")/*, DELETING_SRC_ERROR("Ошибка удаления исходного файла")*/;
		
	    private Statuses(final String text) {
	        this.text = text;
	    }
	    
	    private final String text;
	    
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	private Message message = null;
	
	private byte[] fileContent = null;
	
	private String fileName = null;
	
	private Statuses status = Statuses.OK;
	
	private Integer interval = null;
	
	private Integer quantityToSend = null;
	
	private Integer quantitySent = null;
	
	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	public Integer getQuantitySent() {
		return quantitySent;
	}

	public void setQuantitySent(Integer quantitySent) {
		this.quantitySent = quantitySent;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getQuantityToSend() {
		return quantityToSend;
	}

	public void setQuantityToSend(Integer quantToSend) {
		this.quantityToSend = quantToSend;
	}

	public Statuses getStatus() {
		return status;
	}

	public void setStatus(Statuses status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setMessage(Message message){
		 this.message = message;
	}

	public Message getMessage() {
		return message;
	}
	
}
