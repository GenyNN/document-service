package bss.ServiceDocument.utils;

//import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class DBKInt extends DBKVariable<Integer>
{
    private int m_iVal = 0;

    public DBKInt(DBKInt obj)
    {
        super(DBKVariableType.INTEGER, obj.getName());
        m_iVal = obj.getValue();
    }
    public DBKInt(int iVal)
    {
        super(DBKVariableType.INTEGER);
        m_iVal = iVal;
    }
    public DBKInt(String strName)
    {
        super(DBKVariableType.INTEGER, strName);
    }
    public DBKInt(int iVal, String strName)
    {
        super(DBKVariableType.INTEGER, strName);
        m_iVal = iVal;
    }
    public DBKInt()
    {
        super(DBKVariableType.INTEGER);
    }

    public Integer getValue()
    {
        return m_iVal;
    }
    
    public void setValue(Integer iVal)
    {
        m_iVal = iVal;
    }

    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if((obj instanceof DBKInt) == false)
    		return false;
    	
    	DBKInt _int = (DBKInt)obj;
    	if(obj.hashCode() != hashCode())
    		return false;
    	if(_int.getValue().equals(getValue()) == false)
    		return false;
    	else return true;
    }    
    
    public DBKInt clone(){
    	return (DBKInt)super.clone();
    }
    
    public String toString()
    {
        return "Int Name = " + getName() + "; Value = " + m_iVal;
    }

}
