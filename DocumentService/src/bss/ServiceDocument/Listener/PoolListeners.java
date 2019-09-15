package bss.ServiceDocument.Listener;

import java.util.ArrayList;
import java.util.List;

import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Settings.Setting;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.Transport.TransportCreator;
import bss.ServiceDocument.Transport.TransportType;
import bss.ServiceDocument.utils.DBKParams;


public class PoolListeners {
	private final List<IListener> m_Items = new ArrayList<IListener>();
	
	private Setting m_setting = null;
	QueueTr<ITransport> m_QueueListenerToProcess = new QueueTr<ITransport>();
	
	public PoolListeners(Setting setting) throws Exception
	{
		
		m_setting = setting;
		String strPoolMin = m_setting.getListener().getParams().GetString("PoolMin");
		int iPoolMin = Integer.parseInt(strPoolMin);
		String strType = m_setting.getListener().getParams().GetString("Type");
		
		DBKParams paramsTransport = m_setting.getListener().getTransport().getParams();
		
		String strTransportType = paramsTransport.GetString("Type");
		TransportType transType;
		try{
			transType = TransportType.valueOf(strTransportType);
		}
		catch(Exception ex){
			throw new Exception("Invalid transport type for Listener", ex);
		}
		
		DBKParams paramsQueueListenerToProcess = m_setting.getQueueListenerToProcess().getParams();
		
		if(strType.compareTo(ListenerType.STANDART.getValue()) == 0)
		{			
			for(int i = 0; i < iPoolMin; i++)
			{
				m_Items.add(ListenerCreator.Create(ListenerType.STANDART));
				m_Items.get(i).Initialize(m_setting.getListener().getParams());
				ITransport transport = TransportCreator.Create(transType);
				
				transport.Initialize(paramsTransport);
				m_Items.get(i).setTransport(transport);
			}
		}
		else
			throw new Exception();
		
		
		m_QueueListenerToProcess.Initialize(paramsQueueListenerToProcess);
		setQueue(m_QueueListenerToProcess);
	}
	
	public void setQueue(QueueTr<ITransport> obj)
	{
		for(IListener item : m_Items)
		{
			item.setQueueTr(obj);
		}
	}
	
	public QueueTr<ITransport> getQueue(){
		return m_QueueListenerToProcess;
	}
	
	public void Start()
	{
		for(IListener item : m_Items)
		{
			item.Start();
		}
	}
	public void Destroy()
	{
		for(IListener item : m_Items)
		{
			item.Destroy();
		}
	}
	
	private static PoolListeners m_Pool = null;
	public static PoolListeners getObject() 
	{
		return m_Pool;
	}
	public static void Create(Setting setting) throws Exception
	{		
		m_Pool = new PoolListeners(setting);
	}
}
