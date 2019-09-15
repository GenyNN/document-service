package bss.ServiceDocument.Settings;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Process extends SettingBase {
	private final Transport m_Transport;
	public Transport getTransport() { return m_Transport; }
	
	public Process(Transport transport)
	{
		this.m_Transport = transport;
	}
	
	public static Process CreateFromNode(Node node)
	{
		if(node == null)
			return null;
		Process objOut = null; 
		Transport transport = null;
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; objOut == null && i < nodeList.getLength(); i++){
			Node nodeItem = nodeList.item(i);
			if(nodeItem.getNodeName().compareTo("Transport") == 0){
				transport = Transport.CreateFromNode(nodeItem);
			}
		}
		
		objOut = new Process(transport);
		objOut.FillFromNode(node);
		return objOut;
	}
	
}
