package bss.ServiceDocument.utils;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 11.06.11
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class DBKShort extends DBKVariable<Short>
{
    private short m_sValue = 0;

    public DBKShort()
    {
        super(DBKVariableType.SHORT);
    }
    public DBKShort(short sVal)
    {
        super(DBKVariableType.SHORT);
        m_sValue = sVal;
    }
    
    public DBKShort(String strName)
    {
        super(DBKVariableType.SHORT, strName);
    }
    
    public DBKShort(short sVal, String strName)
    {
        super(DBKVariableType.SHORT, strName);
        m_sValue = sVal;
    }
    
    public DBKShort(DBKShort obj)
    {
        super(DBKVariableType.SHORT, obj.getName());
        m_sValue = obj.getValue();
    }
    
    public Short getValue() { return m_sValue; }
    public void setValue(Short sVal) { m_sValue = sVal; }

    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if((obj instanceof DBKShort) == false)
    		return false;
    	
    	DBKShort _short = (DBKShort)obj;
    	if(obj.hashCode() != hashCode())
    		return false;
    	if(_short.getValue().equals(getValue()) == false)
    		return false;
    	else return true;
    }      
    
    public DBKShort clone(){
    	return (DBKShort)super.clone();
    }
    
    public String toString()
    {
        return "Short Name = " + getName() + "; Value = " + m_sValue;
    }
}
