package bss.ServiceDocument.Process;
import java.io.File;
import java.io.FileOutputStream;

import javax.mail.Message;

import org.apache.log4j.Logger;

import bss.ServiceDocument.Net.SmtpServerConection;
import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Queue.QueueTr;
import bss.ServiceDocument.Transport.ITransport;
import bss.ServiceDocument.Transport.MessageTransport;
import bss.ServiceDocument.Transport.MessageTransport.Statuses;
import bss.ServiceDocument.utils.DBKParams;
public class ProcessStandart extends Thread implements IProcess {
	private static int count = 0;
	private QueueTr<ITransport> m_QueueIn = null;
	private QueueTr<DataExTransport> m_QueueOut = null;
	private DBKParams m_Params = null;
	private static Logger logger = Logger.getLogger(ProcessStandart.class);
	private SmtpServerConection smtpConnection = null;
	
	private String errDir = null;
	
	public ProcessStandart()
	{
		this.setName("bss.ServiceDocument.Process.ProcessStandart[" + count + "]");
		count++;
		this.setDaemon(true);
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
	
	public void Initialize(DBKParams params) 
	{
		m_Params = params;
		
		String host = params.GetString("Host");
		String userName = params.GetString("UserName");
		String password = params.GetString("Password");
		String portStr = params.GetString("Port");
		this.errDir = params.GetString("ERROR_DIR");
		String emailFrom = params.GetString("emailFrom");
		String nameFrom = params.GetString("nameFrom");
		
		if(errDir.isEmpty()){
			logger.info("LOGGER:"+"ошибка задания конфигурации каталога ошибок Process: не задан каталог ERR_DIR " + errDir);
		}
		else{
			File srcDir = new File(errDir);
			
			File[] listFiles = srcDir.listFiles();
			if (listFiles == null){
				logger.info("LOGGER:"+"ошибка задания конфигурации каталога ошибок Process: нет доступа к файлам исходного каталога или такой каталог не существует ERR_DIR "+ errDir);
			}
		}
		
		if(emailFrom.isEmpty()){
			logger.info("LOGGER: неверно указан адрес с которого отправлять");
		}
		if(nameFrom.isEmpty()){
			logger.info("LOGGER: неверно указано название отправителя");
		}
		
		Integer port = null;
		try{
			port = Integer.parseInt(portStr);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		smtpConnection = new SmtpServerConection(host, port, userName, password, emailFrom, nameFrom);
		boolean isConnected = smtpConnection.connect();
		if(!isConnected){
			logger.info("LOGGER:"+"ошибка соединения SMTP сервером при иницализации");
		}
		else{
			logger.info("LOGGER:"+"соединение SMTP сервером при иницализации работает корректно");
		}
		smtpConnection.disConnect();
	}
	
	public void Start() 
	{
		boolean isConnected = false;
		start();
		
	}

	public void setQueueTrIn(QueueTr<ITransport> queue) {
		m_QueueIn = queue;
	}

	public void setQueueTrOut(QueueTr<DataExTransport> queue) {
		m_QueueOut = queue;
	}
	
	private State ThreadState = State.PROCESS;
	
	private enum State
	{
		PROCESS, END;		
	}
	
	public void run()
	{
		logger.info(Thread.currentThread().getName() + " поток запущен");
        while(Thread.currentThread().isInterrupted() == false && ThreadState != State.END && isDestroy == false)
		{
        	try{
    			
    			
	    		ITransport transportIn = null;
	    		try
	    		{
	    			transportIn = m_QueueIn.Pop();
	    		}
	    		catch(InterruptedException ex)
	    		{
	    			Thread.currentThread().interrupt();
	    			ThreadState = State.END;
	    			continue;
	    		}
	    		
	    		
	    		if(transportIn != null)
	    		{	
	    			 logger.info(getName() + " Запускаем процесс обработки");
 	    			 DataExTransport dataExTransport = null;
	    			 try 	
	    			 {	
	    				 dataExTransport = transportIn.Read(smtpConnection);
	    			 }	
	    			 catch (Exception e1) 	
	    			 {
	    				 e1.printStackTrace();
	    				 continue;	 
	    			 }
	    			 for(MessageTransport messageTransport : dataExTransport.getListMessageFiles() ){
	    				 Message message = messageTransport.getMessage();
	    				 long interval = 100;
    					 /*
	    				 if (messageTransport.getInterval() != null){
    						 interval = messageTransport.getInterval();
    					 }
    					 */
    					 int quantTosend = 3;
    					 if (messageTransport.getQuantityToSend() != null){
    						 quantTosend = messageTransport.getQuantityToSend();
    					 }
    					 Statuses sentCode = Statuses.OK;
    					 if (messageTransport.getStatus().ordinal() == 0){ 
	    					 for(int i=0; i<quantTosend; i++){
    	    					 Thread.sleep(interval);
    	    					 try { 
    	    						sentCode = smtpConnection.Send(message);
    	 	    					if (sentCode == Statuses.OK){
     	 	    						messageTransport.setStatus(Statuses.OK);
    	 	    					}
    	    					 } catch (Exception e) {
    	 	    					// TODO Auto-generated catch block
    	 	    					e.printStackTrace();
    	    					 }
    	    					 if (sentCode == Statuses.OK){
    	    						 break;
    	    					 }
	    					 }
    					 }
    					 if (sentCode.ordinal() > Statuses.OK.ordinal() )
    					 {
    						 if(sentCode == Statuses.SENDING_ERROR_BAD_MAIL){
    							 messageTransport.setStatus(Statuses.SENDING_ERROR_BAD_MAIL);
    							 logger.info("LOGGER:"+" ошибка отправки файла с именем " + messageTransport.getFileName() + " не корректный email");
    						 }
    						 else{
    							 if(sentCode == Statuses.SENDING_ERROR){
    								 logger.info("LOGGER:"+" ошибка отправки файла с именем " + messageTransport.getFileName() + " возможно не доступен указанный в настройках SMTP сервер ");
    								 messageTransport.setStatus(Statuses.SENDING_ERROR);
    							 }
    						 }
    						 if(this.errDir !=null && messageTransport.getFileName()!= null){
    							 
    							 String newErrFile = this.errDir + "//" + messageTransport.getFileName();
    							 try{
										FileOutputStream fosErr = new FileOutputStream(newErrFile);
										fosErr.write(messageTransport.getFileContent());
										fosErr.close();
    							 }
    							 catch(Exception e){
    								 logger.info("LOGGER:"+" ошибка записи в каталог ошибок обработчика Process ERR_DIR " + this.errDir);
    								 e.printStackTrace();
    							 }
    						 }
    					 } 
	    			 }
	    			 
	    			 
	    			 try {
	    				m_QueueOut.Push(dataExTransport);
	    			 } catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			 }
	    			 
	    		}
    		}
            catch(Exception e){
            	e.printStackTrace();
            }

		}
        logger.info(Thread.currentThread().getName() + " поток остановлен");
        
	}
	
	public Object getParam(String strName) {
		return null;
	}	
}
