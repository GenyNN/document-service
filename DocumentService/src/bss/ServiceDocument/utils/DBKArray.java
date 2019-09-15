package bss.ServiceDocument.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */

public class DBKArray extends DBKVariable<Object>
{
	private List<DBKVariable<?>> m_Arrays = new ArrayList<DBKVariable<?>>();

    public DBKArray()
    {
        super(DBKVariableType.ARRAY);
    }
	public DBKArray(DBKArray objArray)
    {
        super(DBKVariableType.ARRAY, objArray.getName());

        for(int i = 0; i < objArray.Count(); i++)
        {
            DBKVariable<?> objVar = (DBKVariable<?>)objArray.GetAt(i).clone();
            m_Arrays.add(objVar);
        }
    }
    public DBKArray(String strName)
    {
        super(DBKVariableType.ARRAY, strName);
    }

    public String toString()
    {
    	String strOut = "Array = " + getName() + "";
    	strOut += System.getProperty("line.separator");
    	for(int i = 0; i < m_Arrays.size(); i++)
    	{
    		strOut += m_Arrays.get(i).toString();
    		strOut += System.getProperty("line.separator");
    	}
    	return strOut;
    }

    public DBKVariable<?> getFirstByName(String strName)
    {
    	for(int i = 0; i < Count(); i++)
    	{
    		if(strName.compareTo(m_Arrays.get(i).getName()) == 0)
    			return m_Arrays.get(i);
    	}
    	return null;
    }

    public DBKVariable<?> getLastByName(String name){
    	DBKVariable<?> var = null;
    	for(int i = 0; i < Count(); i++)
    	{
    		if(name.compareTo(m_Arrays.get(i).getName()) == 0){
    			var = m_Arrays.get(i);
    		}
    	}
    	return var;
    }

    public DBKArray getLastArrayByName(String name){
    	DBKVariable<?> var = getLastByName(name);
    	if(var instanceof DBKArray)
    		return (DBKArray)var;
    	else return null;
    }

    public DBKArray getDBKArrayByName(String name){
    	DBKVariable<?> var = getFirstByName(name);
    	if(var instanceof DBKArray)
    		return (DBKArray)var;
    	else return null;
    }

    public DBKShort getDBKShortByName(String name){
    	DBKVariable<?> var = getFirstByName(name);
		if(var instanceof DBKShort)
			return (DBKShort)var;
		else return null;
    }

    public DBKInt getDBKIntByName(String name){
    	DBKVariable<?> var = getFirstByName(name);
    	if(var instanceof DBKInt)
    		return (DBKInt)var;
    	else return null;
    }

    public DBKDouble getDBKDoubleByName(String name){
    	DBKVariable<?> var = getFirstByName(name);
    	if(var instanceof DBKDouble)
    		return (DBKDouble)var;
    	else return null;
    }

    public DBKString getDBKStringByName(String name){
    	DBKVariable<?> var = getFirstByName(name);
    	if(var instanceof DBKString)
    		return (DBKString)var;
    	else return null;
    }
    
    public DBKVariable<?> getDBKVariable(String name){
    	return getFirstByName(name);
    }

    public String getStringByName(String name){
    	DBKString str = getDBKStringByName(name);
    	if(str != null)
    		return str.getValue();
    	else return null;
    }
    
    
    
    public String GetAt_ToString(int iIndex)
    {
        if(iIndex < 0 || iIndex >= (int)m_Arrays.size())
            return "";
        return (String)((DBKString)m_Arrays.get(iIndex)).getValue();
    }

    public int Count()
    {
        return m_Arrays.size();
    }

	public void Add(DBKVariable<?> obj)
    {
		if(obj != this)
			m_Arrays.add(obj);
    }

    public DBKVariable<?> GetAt(int iIndex)
    {
        if(iIndex < 0 || iIndex >= (int)m_Arrays.size())
            return null;
        return (DBKVariable<?>)m_Arrays.get(iIndex);
    }

	public void SetAt(int iIndex, DBKVariable<?> objNew)
    {
        if(iIndex < 0 || iIndex >= m_Arrays.size())
            return;
        m_Arrays.set(iIndex, objNew);
    }

    public void RemoveAt(int iIndex)
    {
        if(iIndex < 0 || iIndex >= m_Arrays.size())
            return;
        m_Arrays.remove(iIndex);
    }

    public void RemoveAll()
    {
        m_Arrays.clear();
    }

    public void AddShort(short sVal, String strName)
    {
    	DBKShort obj = new DBKShort(sVal, strName);
    	m_Arrays.add(obj);
    }
    public void AddShort()
    {
        AddShort((short) 0);
    }
	public void AddShort(short sVal)
    {
        DBKShort obj = new DBKShort(sVal);
        m_Arrays.add(obj);
    }
    public void AddInteger(int iVal, String strName)
    {
    	DBKInt obj = new DBKInt(iVal, strName);
    	m_Arrays.add(obj);
    }
	public void AddInteger(int iVal)
    {
        DBKInt obj = new DBKInt(iVal);
        m_Arrays.add(obj);
    }
    public void AddInteger()
    {
        AddInteger(0);
    }
    public void AddDouble(double dblVal, String strName)
    {
    	DBKDouble temp = new DBKDouble(dblVal, strName);
    	m_Arrays.add(temp);
    }
    public void AddDouble(double dblVal)
    {
        DBKDouble obj = new DBKDouble(dblVal);
        m_Arrays.add(obj);
    }
    public void AddDouble()
    {
        AddDouble(0);
    }
    
    
    public void AddObject(Object objVal, String strName)
    {
    	DBKObject temp = new DBKObject(objVal, strName);
    	m_Arrays.add(temp);
    }
    public void AddObject(Object objVal)
    {
    	DBKObject obj = new DBKObject(objVal);
        m_Arrays.add(obj);
    }
    public void AddObject()
    {
    	AddObject(0);
    }
    
    public void AddString(String strVal, String strName)
    {
    	DBKString obj = new DBKString(strVal, -1, strName);
    	m_Arrays.add(obj);
    }
    public void AddString(String strVal)
    {
        DBKString obj = new DBKString(strVal);
        m_Arrays.add(obj);
    }
    public void AddString(String strVal, int iMaxLength)
    {
        DBKString obj = new DBKString(strVal, iMaxLength);
        m_Arrays.add(obj);
    }
    public void AddString(int iMaxLength)
    {
        DBKString obj = new DBKString("", iMaxLength);
        m_Arrays.add(obj);
    }
    public void AddString()
    {
        AddString("");
    }
    public void AddArray(String strName)
    {
    	DBKArray obj = new DBKArray(strName);
    	m_Arrays.add(obj);
    }
    public void AddArray()
    {
        DBKArray obj = new DBKArray();
        m_Arrays.add(obj);
    }

    public short GetAt_Short(int iIndex)
    {
        DBKShort obj = (DBKShort)GetAt(iIndex);
        if(obj == null)
            return 0;
        return obj.getValue();
    }
    public void SetAt_Short(int iIndex, short sVal)
    {
        if(iIndex < 0 || iIndex >= Count())
            return;
        if((DBKShort)m_Arrays.get(iIndex) != null)
            ((DBKShort)m_Arrays.get(iIndex)).setValue(sVal);
    }
    public int GetAt_Integer(int iIndex)
    {
        DBKInt obj = (DBKInt)GetAt(iIndex);
        if(obj == null)
            return 0;
        return obj.getValue();
    }
    public void SetAt_Integer(int iIndex, int iVal)
    {
        if(iIndex < 0 || iIndex >= Count())
            return;
        if((DBKInt)m_Arrays.get(iIndex) != null)
            ((DBKInt)m_Arrays.get(iIndex)).setValue(iVal);
    }
    public double GetAt_Double(int iIndex)
    {
        DBKDouble obj = (DBKDouble)GetAt(iIndex);
        if(obj == null)
            return 0;
        return obj.getValue();
    }
    public void SetAt_Double(int iIndex, double dblVal)
    {
        if(iIndex < 0 || iIndex >= Count())
            return;
        if((DBKDouble)m_Arrays.get(iIndex) != null)
            ((DBKDouble)m_Arrays.get(iIndex)).setValue(dblVal);
    }
    public String GetAt_String(int iIndex)
    {
        DBKString obj = (DBKString)GetAt(iIndex);
        if(obj == null)
            return "";
        return obj.getValue();
    }
    
    public void SetAt_Object(int iIndex, Object objVal)
    {
        if(iIndex < 0 || iIndex >= Count())
            return;
        if((DBKObject)m_Arrays.get(iIndex) != null)
            ((DBKObject)m_Arrays.get(iIndex)).setValue(objVal);
    }
    public Object GetAt_Object(int iIndex)
    {
        DBKObject obj = (DBKObject)GetAt(iIndex);
        if(obj == null)
            return "";
        return obj.getValue();
    }
    
    public void SetAt_String(int iIndex, String strVal)
    {
        if(iIndex < 0 || iIndex >= Count())
            return;
        if((DBKString)m_Arrays.get(iIndex) != null)
            ((DBKString)m_Arrays.get(iIndex)).setValue(strVal);
    }
    public DBKArray GetAt_Array(int iIndex)
    {
        return (DBKArray)GetAt(iIndex);
    }
    public void SetAt_Array(int iIndex, DBKArray objArray)
    {
        SetAt(iIndex, (DBKVariable<?>)objArray);
    }

    public boolean equals(Object obj){
    	if(obj == null)
    		return false;
    	if((obj instanceof DBKArray) == false)
    		return false;
    	DBKArray _array = (DBKArray)obj;
    	if(_array.hashCode() != obj.hashCode())
    		return false;
    	if(_array.m_Arrays.equals(m_Arrays) == false)
    		return false;
    	return true;
    }

    public DBKArray clone(){
    	DBKArray arrayClone = new DBKArray(this.getName());
    	if(m_Arrays != null){
    		for(DBKVariable<?> var: m_Arrays){
    			arrayClone.Add(var.clone());
    		}
    	}
    	return arrayClone;
    }

	@Override
	public Object getValue() {
		return null;
	}
	@Override
	public void setValue(Object value) {
	}
}
