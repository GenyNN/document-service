package bss.ServiceDocument.Queue;

import java.util.ArrayList;
import java.util.List;

import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.Transport.MessageTransport;

public class DataExTransport{
	private ITransport m_Transport = null;
	private List<MessageTransport> listMessageFiles = new ArrayList<MessageTransport>();
	
	public ITransport getTransport() { return m_Transport; }
	public void setTransport(ITransport obj) { m_Transport = obj; }
	public List<MessageTransport> getListMessageFiles() {
		return listMessageFiles;
	}
	public void setListMessageFiles(List<MessageTransport> listMessageFiles) {
		this.listMessageFiles = listMessageFiles;
	}
	
}
