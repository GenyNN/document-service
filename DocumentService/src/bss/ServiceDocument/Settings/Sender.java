package bss.ServiceDocument.Settings;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sender extends SettingBase {
	private final Transport m_Transport; 
	
	public Sender(Transport transport)
	{
		m_Transport = transport;
	}
	public Transport getTransport() { return m_Transport; }
	
	public static Sender CreateFromNode(Node node)
	{
		if(node == null)
			return null;
		
		Sender objOut = null;
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; objOut == null && i < nodeList.getLength(); i++)
		{
			Node nodeItem = nodeList.item(i);
			if(nodeItem.getNodeName().compareTo("Transport") == 0)
			{
				objOut = new Sender(Transport.CreateFromNode(nodeItem));
			}
		}
		objOut.FillFromNode(node);		
		
		return objOut;
	}
}
