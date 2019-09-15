package bss.ServiceDocument.Listener;

public enum ListenerType {
	STANDART("STANDART");
	
	private final String m_strType;
	private ListenerType(String strType)
	{
		m_strType = strType;
	}
	
	public String getValue() { return m_strType; }
}
