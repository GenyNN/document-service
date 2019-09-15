package bss.ServiceDocument.Transport;

import bss.ServiceDocument.Net.SmtpServerConection;
import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.utils.DBKParams;

public interface ITransport {
	public void Initialize(DBKParams params);
	
	public DataExTransport Read(SmtpServerConection connnection) throws Exception;
	
	public ITransport Accept() throws Exception, InterruptedException;
	
	public int Write(DataExTransport data) throws InterruptedException;
	
}
