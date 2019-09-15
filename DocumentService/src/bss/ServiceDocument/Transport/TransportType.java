package bss.ServiceDocument.Transport;

public enum TransportType {
	QUEUE_INPUT_FILES_BOUNDLE_GET("QueueInputFilesBoundleGet"), INPUT("INPUT"), OUTPUT("OUTPUT");
	
	private final String m_strValue;
	private TransportType(String strType)
	{
		m_strValue = strType;
	}
	public String getValue() { return m_strValue; }
}
