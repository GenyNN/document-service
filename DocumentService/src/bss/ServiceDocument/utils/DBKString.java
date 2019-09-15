package bss.ServiceDocument.utils;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class DBKString extends DBKVariable<String>
{
    private String m_strValue = "";
    private int m_iMaxLength = -1;
    
    public DBKString(DBKString obj)
    {
        super(DBKVariableType.STRING, obj.getName());
        m_strValue = obj.getValue();
        m_iMaxLength = obj.MaxLength();
    }
    public DBKString(String strVal)
    {
        super(DBKVariableType.STRING);
        if(strVal != null)
        	m_strValue = strVal;
    }
    public DBKString(String strVal, int iMaxLength)
    {
        super(DBKVariableType.STRING);
        if(strVal == null)
        	strVal = "";
        if(iMaxLength < 0)
            m_strValue = strVal;
        else
            m_strValue = strVal.substring(0, Math.min(iMaxLength, strVal.length() - 0));
        m_iMaxLength = iMaxLength;
    }
    public DBKString(String strVal, int iMaxLength, String strName)
    {
        super(DBKVariableType.STRING, strName);
        if(strVal == null)
        	strVal = "";
        if(iMaxLength < 0)
            m_strValue = strVal;
        else
            m_strValue = strVal.substring(0, Math.min(iMaxLength, strVal.length() - 0));
        m_iMaxLength = iMaxLength;
    }
    public DBKString()
    {
        super(DBKVariableType.STRING);
    }

    public DBKString(String string, String string2) {
    	super(DBKVariableType.STRING, string2);
    	if(string == null)
    		m_strValue = "";
    	else m_strValue = string;
    	m_iMaxLength = m_strValue.length();
	}
	public String getValue()
    {
        return m_strValue;
    }
    
    public void setValue(String strVal)
    {
    	if(strVal == null)
    	{
    		m_strValue = "";
    		return;
    	}
        if(m_iMaxLength <= 0)
            m_strValue = strVal;
        else
            m_strValue = strVal.substring(0, Math.min(m_iMaxLength, strVal.length() - 0));
    }
    
    public int MaxLength() { return m_iMaxLength; }

    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if((obj instanceof DBKString) == false)
    		return false;
    	
    	DBKString _string = (DBKString)obj;
    	if(obj.hashCode() != hashCode())
    		return false;
    	if(_string.getValue().equals(getValue()) == false)
    		return false;
    	else return true;
    }      
    
    public DBKString clone(){
    	return (DBKString)super.clone();
    }
    
    public String toString()
    {
        return "String Name = " + getName() + "; Value = " + m_strValue;
    }
	public void setMaxLength(int length) {
		m_iMaxLength = length;
	}
}
