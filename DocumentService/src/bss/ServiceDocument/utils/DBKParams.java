package bss.ServiceDocument.utils;


import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class DBKParams 
{
    private final HashMap<String, DBKVariable<?>> m_Map = new HashMap<String, DBKVariable<?>>();
    protected final static byte[] m_VerPack = {100, 106};
    
    private final HashMap<String, Object> m_MapObject = new HashMap<String, Object>();

    public DBKParams()
    {
    }

    public DBKParams Clone()
    {
        DBKParams outParams = new DBKParams();
        Object a[] = m_Map.keySet().toArray();
        for(int i = 0; i < a.length; i++)
        {
            String strName = (String)a[i];
            if(strName != null)
            {
                DBKVariable<?> dbkVar = m_Map.get(strName);
                outParams.Set(strName, dbkVar);
            }
        }
        return outParams;
    }
    public DBKParams CloneWithVars()
    {
        DBKParams outParams = new DBKParams();
        Object a[] = m_Map.keySet().toArray();
        for(int i = 0; i < a.length; i++)
        {
            String strName = (String)a[i];
            if(strName != null)
            {
                DBKVariable<?> dbkVar = m_Map.get(strName);
                outParams.Set(strName, dbkVar.clone());
            }
        }
        return outParams;
    }
    
    public void SetObject(String strName, Object obj)
    {
    	m_MapObject.put(strName, obj);
    }
    
    public java.util.Set<String> GetObjectNames(){
    	return m_MapObject.keySet();
    }
    
    public Object GetObject(String strName)
    {
    	return m_MapObject.get(strName);
    }

    public int Count()
    {
        return m_Map.size();
    }

    public void Set(String strName, DBKVariable<?> objValue)
    {
        m_Map.put(strName, objValue);
    }
    public void SetInteger(String strName, int iVal)
    {
        DBKInt objInt = new DBKInt(iVal);
        m_Map.put(strName, objInt);
    }
    public void SetDouble(String strName, double dblVal)
    {
        DBKDouble objDouble = new DBKDouble(dblVal);
        m_Map.put(strName, objDouble);
    }
    public void SetString(String strName, String strVal)
    {
        DBKString objString = new DBKString(strVal);
        m_Map.put(strName, objString);
    }
    public void SetArray(String strName, DBKArray arr)
    {
        m_Map.put(strName, arr);
    }

    public DBKVariable<?> Get(String strName)
    {
        return m_Map.get(strName);
    }
    public int GetInteger(String strName)
    {
        DBKInt objInt = (DBKInt)Get(strName);
        if(objInt == null)
            return 0;
        return objInt.getValue();
    }
    public double GetDouble(String strName)
    {
        DBKDouble objDouble = (DBKDouble)Get(strName);
        if(objDouble == null)
            return 0;
        return objDouble.getValue();
    }
    public String GetString(String strName)
    {
        DBKString objString = (DBKString)Get(strName);
        if(objString == null)
            return "";
        return objString.getValue();
    }

    public String ConvertToString(String strName)
    {
        DBKVariable<?> obj = Get(strName);
        if(obj == null)
            return "";
        return obj.toString();
    }

    public void Clear()
    {
        m_Map.clear();
    }
}
