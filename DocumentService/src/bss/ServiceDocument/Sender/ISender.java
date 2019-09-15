package bss.ServiceDocument.Sender;

import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.utils.DBKParams;

public interface ISender {
	public void Initialize(DBKParams params);
	public void setTransport(ITransport transport);
	public void setQueueTr(QueueTr<DataExTransport> queue);
	public void Start();
	public void Destroy();
}
