package bss.ServiceDocument.Sender;

import org.apache.log4j.Logger;

import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.utils.DBKParams;

public class SenderStandartDataSend extends Thread implements ISender {
	
	@SuppressWarnings("unused")
	private DBKParams m_Params = null;
	private ITransport m_Transport = null;
	private QueueTr<DataExTransport> m_Queue = null;
	private static Logger logger = Logger.getLogger(SenderStandartDataSend.class);
	
	private enum State
	{
		NOTHING, PROCESS, END;
	}
	
	private State m_State = State.NOTHING;
	
	public SenderStandartDataSend(){
		setName("bss.ServiceDocument.Sender.SenderStandartDataSend");
		setDaemon(true);
	}
	
	public void Initialize(DBKParams params)
	{
		m_Params = params;
	}
	public void setTransport(ITransport transport)
	{
		m_Transport = transport;
	}
	public void setQueueTr(QueueTr<DataExTransport> queue)
	{
		m_Queue = queue;
	}
	public void Start()
	{
		start();
	}
	
	private volatile boolean isDestroy = false;
	
	public void Destroy()
	{
		interrupt();
	}
	
	public void interrupt(){
		super.interrupt();
		isDestroy = true;
	}

	public void run()
	{
		if(isDestroy){
			return;					
		}
		logger.info(Thread.currentThread().getName() + " поток запущен");
		while(m_State != State.END && isDestroy == false)
		{
			switch(m_State)
			{
			case NOTHING:
				m_State = State.PROCESS;
				break;
			case PROCESS:
				
				DataExTransport dataExTransport = null;
				try{
					dataExTransport = m_Queue.Pop();
					if(dataExTransport != null)
					{
						
						try {
							int writeCode = m_Transport.Write(dataExTransport);
						}
						catch(InterruptedException ex){
							m_State = State.END;
							break;
						}
					}					
				}
				catch(InterruptedException ex){
					Thread.currentThread().interrupt();
					m_State = State.END;
					continue;
				}
				break;
			case END:
				break;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				m_State = State.END;
			}
		}
		logger.info(Thread.currentThread().getName() + " поток остановлен");
	}
}
