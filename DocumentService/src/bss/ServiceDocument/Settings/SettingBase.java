package bss.ServiceDocument.Settings;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bss.ServiceDocument.utils.DBKArray;
import bss.ServiceDocument.utils.DBKParams;

public class SettingBase {
	
	private final DBKParams m_Params = new DBKParams();
	
	private void getArrayFromNode(Node node, DBKArray arrNew)
	{
		if(node == null)
			return;
		
		if(arrNew == null)
			arrNew = new DBKArray(node.getNodeName());
		else
		{
			arrNew.AddArray(node.getNodeName());
			arrNew = arrNew.GetAt_Array(arrNew.Count() - 1);
		}
		
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node nodeItem = nodeList.item(i);
			if(nodeItem.getNodeName().length() > 0 && nodeItem.getFirstChild() != null && 
					nodeItem.getFirstChild().getNodeValue() != null)
			{
				if(nodeItem.getChildNodes().getLength() > 1)
					getArrayFromNode(nodeItem, arrNew);
				else
					arrNew.AddString(nodeItem.getFirstChild().getNodeValue(), nodeItem.getNodeName());
			}
		}
	}
	
	public DBKParams getParams() { return m_Params; }
	
	public void FillFromNode(Node node)
	{
		m_Params.Clear();
		
		
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node nodeItem = nodeList.item(i);
			if(nodeItem.getNodeName().length() > 0 && nodeItem.getFirstChild() != null && 
					nodeItem.getFirstChild().getNodeValue() != null)
			{
				if(nodeItem.getChildNodes().getLength() > 1)
				{
					DBKArray arr = new DBKArray(nodeItem.getNodeName());
					getArrayFromNode(nodeItem, arr);
					if(arr != null)
						m_Params.SetArray(arr.getName(), arr);
				}
				else
				{
					m_Params.SetString(nodeItem.getNodeName(), nodeItem.getFirstChild().getNodeValue());
				}
			}
		}
	}
}
