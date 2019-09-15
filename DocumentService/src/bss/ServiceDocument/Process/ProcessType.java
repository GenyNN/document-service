package bss.ServiceDocument.Process;

public enum ProcessType {
	STANDART("STANDART");
	
	private final String m_strValue;
	private ProcessType(String strValue)
	{
		m_strValue = strValue;
	}
	public String getValue() { return m_strValue; }
}
