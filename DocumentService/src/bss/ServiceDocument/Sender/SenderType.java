package bss.ServiceDocument.Sender;

public enum SenderType {
	STANDART_DATASEND("STANDART_DATASEND");
	
	private final String m_strType;
	private SenderType(String strType)
	{
		m_strType = strType;
	}
	
	public String getValue() { return m_strType; }
}
