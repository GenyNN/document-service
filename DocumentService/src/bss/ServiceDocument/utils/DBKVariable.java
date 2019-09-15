package bss.ServiceDocument.utils;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public abstract class DBKVariable<T> implements Cloneable
{

    private DBKVariableType type;
    private String m_strName = "";

    protected DBKVariable(DBKVariableType type)
    {
    	this.type = type;
    }
    protected DBKVariable(DBKVariableType type, String strName)
    {
    	this.type = type;
        m_strName = strName;
    }

    public DBKVariableType getType()
    {
        return type;
    }

    public String getName() {    	
    	return m_strName; 
    }
    
    public void setName(String strName) 
    {
    	m_strName = strName; 
    }

    public abstract T getValue();
    public abstract void setValue(T value);
    
    @Override
    public int hashCode(){
		return m_strName.hashCode();    	
    }
    
    public DBKVariable<?> clone()
    {
        try{
        	DBKVariable<?> var = (DBKVariable<?>)super.clone();
        	var.m_strName = new String(this.m_strName);
			return var;
		}
        catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public String toString()
    {
        return "";
    }   
}
