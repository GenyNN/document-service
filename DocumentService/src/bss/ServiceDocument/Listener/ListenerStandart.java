package bss.ServiceDocument.Listener;

import org.apache.log4j.Logger;

import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.Transport.TransportQueueInputFilesBoundleGet;
import bss.ServiceDocument.utils.DBKParams;

public class ListenerStandart extends Thread implements IListener {

	
	public void Destroy() {
		this.interrupt();
	}
	
	public void interrupt(){
		super.interrupt();
		isDestroy = true;
	}

	public void Start() {
		start();
	}
	
	public ListenerStandart(){
		setName("bss.ServiceDocument.Listener.ListenerStandart");
		setDaemon(true);
	}
	
	@SuppressWarnings("unused")
	private DBKParams m_Params = null;
	private ITransport m_Transport = null;
	private QueueTr<ITransport> m_Queue = null;
	private volatile boolean isDestroy = false;
	private static Logger logger = Logger.getLogger(ListenerStandart.class);
	
	public void Initialize(DBKParams params)
	{
		m_Params = params;
	}
	public void setTransport(ITransport transport)
	{
		m_Transport = transport;
	}
	public void setQueueTr(QueueTr<ITransport> queue)
	{
		m_Queue = queue;
	}

	private enum State
	{
		NOTHING, PROCESS, END;
	}
	
	private State m_State = State.NOTHING;
	
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
			
				ITransport transportNew = null;
				
				try {
					transportNew = m_Transport.Accept();
				}
				catch(InterruptedException ex){
					Thread.currentThread().interrupt();
					m_State = State.END;
					break;
				}
				catch (Exception e1) {
					e1.printStackTrace();
					try{
						Thread.sleep(300);
					}
					catch(InterruptedException ex){
						Thread.currentThread().interrupt();						
						m_State = State.END;
						break;
					}
					break;
				}
				
				if(transportNew != null){
					try{
						if ( ((TransportQueueInputFilesBoundleGet)transportNew).getListDocumentFiles() != null){
							if( ((TransportQueueInputFilesBoundleGet)transportNew).getListDocumentFiles().size() != 0){
								m_Queue.Push(transportNew);
							}
						}
					}
					catch(InterruptedException ex){
						Thread.currentThread().interrupt();
						m_State = State.END;
					}
				}
				break;
			case END:
				break;
			}
			
			try {
				Thread.sleep(300);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				m_State = State.END;
			}
		}
		logger.info(Thread.currentThread().getName() + "поток остановлен");
	}
}
