package bss.ServiceDocument.Listener;

import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.utils.DBKParams;

import bss.ServiceDocument.Queue.QueueTr;

public interface IListener {
	public void Initialize(DBKParams params);
	public void setTransport(ITransport transport);
	public void setQueueTr(QueueTr<ITransport> queue);
	public void Start();
	public void Destroy();
}
