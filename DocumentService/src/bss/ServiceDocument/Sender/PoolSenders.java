package bss.ServiceDocument.Sender;

import java.util.ArrayList;
import java.util.List;

import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Settings.Setting;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.Transport.TransportCreator;
import bss.ServiceDocument.Transport.TransportType;
import bss.ServiceDocument.utils.DBKParams;

public class PoolSenders {
	private final List<ISender> m_Items = new ArrayList<ISender>();
	private Setting m_setting = null;
	private QueueTr<DataExTransport> m_QueueProcessToSender = new QueueTr<DataExTransport>();
	
	public PoolSenders(Setting setting) throws Exception
	{
		m_setting = setting;
		String strPoolMin = m_setting.getSender().getParams().GetString("PoolMin");
		int iPoolMin = Integer.parseInt(strPoolMin);
		String strType = m_setting.getSender().getParams().GetString("Type");
		
		DBKParams paramsTransport = m_setting.getSender().getTransport().getParams();
		String strTransportType = paramsTransport.GetString("Type");
		TransportType transType;
		try{
			transType = TransportType.valueOf(strTransportType);
		}
		catch(Exception ex){
			throw new Exception("Invalid transport type for Sender", ex);
		}
		
		DBKParams paramsQueueListenerToProcess = m_setting.getQueueListenerToProcess().getParams();
		
		if(strType.compareTo(SenderType.STANDART_DATASEND.getValue()) == 0)
		{			
			for(int i = 0; i < iPoolMin; i++)
			{				
				m_Items.add(SenderCreator.Create(SenderType.STANDART_DATASEND));
				m_Items.get(i).Initialize(m_setting.getSender().getParams());
				
				ITransport transport = TransportCreator.Create(transType);
				transport.Initialize(paramsTransport);
				
				m_Items.get(i).setTransport(transport);
				
				//QueueTr.getQueueProcessToSender().Initialize(paramsQueueListenerToProcess);
			}
		}
		else
			throw new Exception();
		
		m_QueueProcessToSender.Initialize(paramsQueueListenerToProcess);
		setQueue(m_QueueProcessToSender);
	}
	
	public void setQueue(QueueTr<DataExTransport> obj)
	{
		for(ISender item : m_Items)
		{
			item.setQueueTr(obj);
		}
	}
	
	public QueueTr<DataExTransport> getQueue(){
		return m_QueueProcessToSender;
	}
	
	public void Start()
	{
		for(ISender item : m_Items)
		{
			item.Start();
		}
	}
	public void Destroy()
	{
		for(ISender item : m_Items)
		{
			item.Destroy();
		}
	}
	
	private static PoolSenders m_Pool = null;
	public static PoolSenders getObject() 
	{
		return m_Pool;
	}
	public static void Create(Setting setting) throws Exception
	{		
		m_Pool = new PoolSenders(setting);
	}
}
