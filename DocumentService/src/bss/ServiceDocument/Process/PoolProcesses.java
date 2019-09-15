package bss.ServiceDocument.Process;

import java.util.ArrayList;
import java.util.List;

import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Settings.Setting;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.utils.DBKParams;

public class PoolProcesses {
	private final List<IProcess> m_Items = new ArrayList<IProcess>();
	private Setting m_setting = null;
	
	public PoolProcesses(Setting setting) throws Exception
	{
		m_setting = setting;
		String strPoolMin = m_setting.getProcess().getParams().GetString("PoolMin");
		int iPoolMin = Integer.parseInt(strPoolMin);
		String strType = m_setting.getProcess().getParams().GetString("Type");
		
		DBKParams paramsTransport = m_setting.getProcess().getTransport().getParams();
		
		if(strType.compareTo(ProcessType.STANDART.getValue()) == 0)
		{
			for(int i = 0; i < iPoolMin; i++)
			{
				m_Items.add(ProcessCreator.Create(ProcessType.STANDART));
				m_Items.get(i).Initialize(paramsTransport);
			}
		}
		else
			throw new Exception();		
	}
	public void Start()
	{
		for(IProcess item : m_Items)
		{
			item.Start();
		}
	}
	public void Destroy()
	{
		for(IProcess item : m_Items)
		{
			item.Destroy();
		}
	}
	public void setQueueTrIn(QueueTr<ITransport> obj)
	{
		for(IProcess item : m_Items)
			item.setQueueTrIn(obj);
	}
	public void setQueueTrOut(QueueTr<DataExTransport> obj)
	{
		for(IProcess item : m_Items)
			item.setQueueTrOut(obj);
	}
	
	private static PoolProcesses m_Pool = null;
	public static PoolProcesses getObject() 
	{
		return m_Pool;
	}
	public static void Create(Setting setting) throws Exception
	{		
		m_Pool = new PoolProcesses(setting);
	}
	
}
