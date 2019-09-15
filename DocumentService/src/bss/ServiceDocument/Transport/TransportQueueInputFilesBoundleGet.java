package bss.ServiceDocument.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bss.ServiceDocument.Net.SmtpServerConection;
import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.utils.DBKArray;
import bss.ServiceDocument.utils.DBKObject;
import bss.ServiceDocument.utils.DBKParams;
//import bss.ServiceDocument.Settings.SettingAccess;

public class TransportQueueInputFilesBoundleGet implements ITransport {
	
	private HashMap<String, Object> params = new HashMap<String, Object>();
	
	private List<IDocumentFileTransport> listDocumentFiles = new ArrayList<IDocumentFileTransport>();
	
	public HashMap<String, Object> getParams() {
		return params;
	}
	
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	
	public List<IDocumentFileTransport> getListDocumentFiles() {
		return listDocumentFiles;
	}

	public void setListDocumentFiles(List<IDocumentFileTransport> listDocumentFiles) {
		this.listDocumentFiles = listDocumentFiles;
	}
	
	public ITransport Accept() throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public void Initialize(DBKParams params) {
		
		Set<String> names = params.GetObjectNames();
		Iterator<String> it = names.iterator();
		String name = null;
		while(it.hasNext()){
			name = it.next();
			this.params.put(name, params.GetObject(name));
		}
		
		DBKArray arrayFiles = (DBKArray)params.Get("Files");
		if(arrayFiles == null)
			return;
		
		for(int k = 0; k < arrayFiles.Count(); k++)
		{
			DBKObject obj = (DBKObject)arrayFiles.GetAt(k);
			if(obj == null)
				continue;
			IDocumentFileTransport docFile = (IDocumentFileTransport)obj.getValue();
			listDocumentFiles.add(docFile);
		}
	}
	public synchronized DataExTransport Read(SmtpServerConection connnection) throws Exception {
		DataExTransport transport = new DataExTransport();
		for (IDocumentFileTransport document : listDocumentFiles){
			MessageTransport message = document.convertDocumentToMessage(connnection);
			transport.getListMessageFiles().add(message);
		}
		
		transport.setTransport(this);
		return transport;
	}
	public int Write(DataExTransport data) {
		throw new UnsupportedOperationException();
	}
}
