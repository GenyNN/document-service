package bss.ServiceDocument.utils;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class DBKDouble extends DBKVariable<Double>
{
    private double m_dblVal = 0;

    public DBKDouble(DBKDouble obj)
    {
        super(DBKVariableType.DOUBLE, obj.getName());
        m_dblVal = obj.getValue();
    }
    public DBKDouble(double dblVal)
    {
        super(DBKVariableType.DOUBLE);
        m_dblVal = dblVal;    
    }
    public DBKDouble(String strName)
    {
        super(DBKVariableType.DOUBLE, strName);
    }
    public DBKDouble(double dblVal, String strName)
    {
        super(DBKVariableType.DOUBLE, strName);
        m_dblVal = dblVal;
    }
    public DBKDouble()
    {
        super(DBKVariableType.DOUBLE);
    }

    public Double getValue()
    {
        return m_dblVal;
    }
    public void setValue(Double dblVal)
    {
        m_dblVal = dblVal;
    }

    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if((obj instanceof DBKDouble) == false)
    		return false;
    	
    	DBKDouble _double = (DBKDouble)obj;
    	if(obj.hashCode() != hashCode())
    		return false;
    	if(_double.getValue().equals(getValue()) == false)
    		return false;
    	else return true;
    }
    
    public DBKDouble clone(){
    	return (DBKDouble)super.clone();
    }
    
    public String toString()
    {
        return "Double Name = " + getName() + "; Value = " + m_dblVal;
    }
}
