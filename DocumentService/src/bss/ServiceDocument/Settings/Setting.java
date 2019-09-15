package bss.ServiceDocument.Settings;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Setting {
	
	
	private final Listener m_Listener;
	
	private final QueueFIFO m_QueueListenerToProcess;
	private final Process m_Process;
	private final QueueFIFO m_QueueProcessToSender;
	private final Sender m_Sender;
	
	
	public Setting(Listener listener, QueueFIFO queueListenerToProcess, Process process, QueueFIFO queueProcessToSender, Sender sender)
	{
		
		this.m_Listener = listener;
		m_QueueListenerToProcess = queueListenerToProcess;
		this.m_Process = process;
		this.m_QueueProcessToSender = queueProcessToSender;
		this.m_Sender = sender;
	}
	
	
	public Listener getListener() { return m_Listener; }
	
	public QueueFIFO getQueueListenerToProcess() { return m_QueueListenerToProcess; }
	
	public Process getProcess() { return m_Process; }
	
	public QueueFIFO getQueueProcessToSender() { return m_QueueProcessToSender; }
	
	public Sender getSender() { return m_Sender; }
	
	
	private static ArrayList<Setting> settings = new ArrayList<Setting>();
	
	public static Setting CreateFromXML(String strFullFileName)
	{
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		
		final Document doc;
		try {					
			InputStream stream = SettingAccess.getInputStream(strFullFileName);
			doc = docBuilder.parse(stream);
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
		Listener listener = null;
		QueueFIFO queueListenerToProcess = null;
		Process process = null;
		QueueFIFO queueProcessToSender = null;
		Sender sender = null;
		
		
 		final Element docElement = doc.getDocumentElement();
		final NodeList nodeList = docElement.getChildNodes();
		for(int j = 0; j < nodeList.getLength(); j++)
		{
			Node nItem = nodeList.item(j);
			if(nItem.getNodeName().compareTo("docserv") == 0)
			{
				boolean bUseAdapter = true;
				NamedNodeMap attr = nItem.getAttributes();
				for(int l = 0; l < attr.getLength(); l++){
					Node attrItem = attr.item(l);
					if(attrItem.getNodeName().compareTo("enable") == 0){
						bUseAdapter = Boolean.parseBoolean(attrItem.getNodeValue());
					}						
				}
				if(bUseAdapter == false)
					continue;
				NodeList nList = nItem.getChildNodes();
				for(int i = 0; i < nList.getLength(); i++)
				{
					Node nodeItem = nList.item(i);
					if(nodeItem.getNodeName().compareTo("Listener") == 0)
					{
						listener = Listener.CreateFromNode(nodeItem);
					}
					else if(nodeItem.getNodeName().compareTo("QueueListenerToProcess") == 0)
					{
						queueListenerToProcess = QueueFIFO.CreateFromNode(nodeItem);
					}
					else if(nodeItem.getNodeName().compareTo("Process") == 0)
					{
						process = Process.CreateFromNode(nodeItem);
					}
					
					else if(nodeItem.getNodeName().compareTo("QueueProcessToSender") == 0)
					{
						queueProcessToSender = QueueFIFO.CreateFromNode(nodeItem);
					}				
					else if(nodeItem.getNodeName().compareTo("Sender") == 0)
					{
						sender = Sender.CreateFromNode(nodeItem);
					}
				}
				settings.add(new Setting(listener, queueListenerToProcess, process, queueProcessToSender, sender));
			}
		}
		
		
		return null;
	}
	
	public static void Create(String strFulFileName)
	{
		CreateFromXML(strFulFileName);
	}
	
	public static Setting getSetting(int index){
		return settings.get(index);
	}
	
	public static int getCountEmlHandlers(){
		return settings.size();
	}
}
