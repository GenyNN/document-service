package bss.ServiceDocument.utils;

/**
 * Created by eclipse.
 * User:  ushakov
 * Date: 26.08.2013
 */
public class DBKObject extends DBKVariable<Object>
{
    private Object m_dblVal = 0;

    public DBKObject(DBKObject obj)
    {
        super(DBKVariableType.OBJECT, obj.getName());
        m_dblVal = obj.getValue();
    }
    public DBKObject(Object dblVal)
    {
        super(DBKVariableType.OBJECT);
        m_dblVal = dblVal;
    }
    public DBKObject(String strName)
    {
        super(DBKVariableType.OBJECT, strName);
    }
    public DBKObject(Object dblVal, String strName)
    {
        super(DBKVariableType.OBJECT, strName);
        m_dblVal = dblVal;
    }
    public DBKObject()
    {
        super(DBKVariableType.OBJECT);
    }

    public Object getValue()
    {
        return m_dblVal;
    }
    public void setValue(Object dblVal)
    {
        m_dblVal = dblVal;
    }

    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if((obj instanceof DBKObject) == false)
    		return false;
    	
    	DBKObject _object = (DBKObject)obj;
    	if(obj.hashCode() != hashCode())
    		return false;
    	if(_object.getValue().equals(getValue()) == false)
    		return false;
    	else return true;
    }
    
    public DBKObject clone(){
    	return (DBKObject)super.clone();
    }
    
    public String toString()
    {
        return "Object Name = " + getName() + "; Value = " + m_dblVal;
    }
}
