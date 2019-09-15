package bss.ServiceDocument.Main;

import bss.ServiceDocument.Listener.PoolListeners;
import bss.ServiceDocument.Process.PoolProcesses;
import bss.ServiceDocument.Sender.PoolSenders;
import bss.ServiceDocument.Settings.Setting;

public class EmlHandler {
	private PoolListeners poolListeners = null;
	private PoolProcesses poolProcesses = null;
	private PoolSenders poolSenders = null;
	public EmlHandler(Setting setting)  throws Exception{
		poolListeners = new PoolListeners(setting);
		poolSenders = new PoolSenders(setting);
		
		poolProcesses = new PoolProcesses(setting);		
		poolProcesses.setQueueTrIn(poolListeners.getQueue());
		poolProcesses.setQueueTrOut(poolSenders.getQueue());		
	}
	public void start(){
		new Thread(new Runnable(){
			public void run(){
				poolListeners.Start();
				poolSenders.Start();						
				poolProcesses.Start();
			}
		}).start();
	}
	
	public void stop(){
		poolListeners.Destroy();
		poolSenders.Destroy();
		poolProcesses.Destroy();		
	}
}
