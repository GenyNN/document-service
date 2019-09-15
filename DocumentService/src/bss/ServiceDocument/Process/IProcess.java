package bss.ServiceDocument.Process;

import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.utils.DBKParams;

public interface IProcess {
	public void Initialize(DBKParams params);
	public void setQueueTrIn(QueueTr<ITransport> queue);
	public void setQueueTrOut(QueueTr<DataExTransport> queue);
	public void Start();
	public void Destroy();
}
