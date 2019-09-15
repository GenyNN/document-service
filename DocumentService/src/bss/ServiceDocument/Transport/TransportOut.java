package bss.ServiceDocument.Transport;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.Message;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import bss.ServiceDocument.Net.SmtpServerConection;
import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Sender.SenderResponse;
import bss.ServiceDocument.utils.DBKParams;
public class TransportOut implements ITransport{
	
	private String targetDir;
	private String archTargetDir;
	private String errDir;
	private static Logger logger = Logger.getLogger(TransportOut.class);
	
	public String getErrDir() {
		return errDir;
	}
	public void setErrDir(String errDir) {
		this.errDir = errDir;
	}
	public String getTargetDir() {
		return targetDir;
	}
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getArchTargetDir() {
		return archTargetDir;
	}

	public void setArchTargetDir(String archTargetDir) {
		this.archTargetDir = archTargetDir;
	}
	
	public ITransport Accept() throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public void Initialize(DBKParams params) {
		targetDir = params.GetString("TARGET_DIR");
		
		if(targetDir.isEmpty()){
			logger.info("LOGGER:"+"ошибка задания конфигурации объекта Sender: не задан каталог TARGET_DIR " + targetDir);
		}
		else{
			File targetDirObj = new File(targetDir);
			
			File[] listFiles = targetDirObj.listFiles();
			if (listFiles == null){
				logger.info("LOGGER:"+"ошибка задания конфигурации объекта Sender: ошибка доступа к каталогу TARGET_DIR или не существует " + targetDir);
			}
		}
		
		archTargetDir = params.GetString("ARCHIVE_TARGET_DIR");
		if(archTargetDir.isEmpty()){
			logger.info("LOGGER:"+"ошибка задания конфигурации объекта Sender: не задан каталог ARCHIVE_TARGET_DIR " + archTargetDir );
		}
		else{
			File archTargetDirObj = new File(archTargetDir);			
			File[] listFiles = archTargetDirObj.listFiles();
			if (listFiles == null){
				logger.info("LOGGER:"+"ошибка задания конфигурации объекта Sender: ошибка доступа к каталогу ARCHIVE_TARGET_DIR или не существует " + archTargetDir);
			}
		}
		
		errDir = params.GetString("ERROR_DIR");
		if(errDir.isEmpty()){
			logger.info("LOGGER:"+"ошибка задания конфигурации объекта Sender: не задан каталог ERROR_DIR " + errDir);
		}
		else{
			File errDirObj = new File(errDir);			
			File[] listFiles = errDirObj.listFiles();
			if (listFiles == null){
				logger.info("LOGGER:"+"ошибка задания конфигурации объекта Sender: ошибка доступа к каталогу ERROR_DIR или не существует " + errDir);
			}
		}
		
	}
	
	public DataExTransport Read(SmtpServerConection connnection) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public synchronized int Write(DataExTransport data) throws InterruptedException {
		for(MessageTransport mesTrans : data.getListMessageFiles()){
			Message message = mesTrans.getMessage();
			if (message != null){
				String fileName = mesTrans.getFileName();
				logger.info("Sender название файла = " + fileName);
				SenderResponse resp = new SenderResponse();
				resp.setFileName(fileName);
				resp.setCode(mesTrans.getStatus().ordinal());
				resp.setDescription(mesTrans.getStatus().toString());
				resp.setTimeStamp(new Date());
				
				boolean isInTarget = false;
				boolean isInTargetArch = false;
				//target
			    try{
			    	File resTargetFile = new File(targetDir+"\\"+fileName+".xml");
					
					if (!resTargetFile.exists()){
						FileOutputStream fop = new FileOutputStream(resTargetFile);
			    		JAXBContext jaxbContext = JAXBContext.newInstance(SenderResponse.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						jaxbMarshaller.marshal(resp, fop);
						fop.flush();
						fop.close();
					}
					else{
						logger.info("LOGGER: файл с именем " + resTargetFile.getPath() + " уже существует");
					}
					
					isInTarget = true;
			    }
			    catch(Exception e){
			    	logger.info("LOGGER:"+"возникла ошибка записи результирующего файла на диск в TARGET_DIR ");
			    	e.printStackTrace();
			    }
			    
			    //target arch
			    try{
			    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			    	File resArchTargetFile = new File(archTargetDir+"\\"+fileName+"-"+timeStamp+".xml");
			    	resArchTargetFile.createNewFile();
			    	FileOutputStream fopArch = new FileOutputStream(resArchTargetFile);
					JAXBContext jaxbContext = JAXBContext.newInstance(SenderResponse.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					
					jaxbMarshaller.marshal(resp, fopArch);
					fopArch.flush();
					fopArch.close();
					isInTargetArch = true;
			    }
				catch(Exception e){
					logger.info("LOGGER:"+"возникла ошибка записи результирующего файла на диск в архивный каталог в ARCHIVE_TARGET_DIR");
			    	e.printStackTrace();
				}
			    
			    if (!isInTargetArch || !isInTarget){
			    	//err
				    try{
				    	File resErrDirFile = new File(errDir+"\\"+fileName+".xml");
				    	FileOutputStream fopErr = new FileOutputStream(resErrDirFile);
						
						JAXBContext jaxbContext = JAXBContext.newInstance(SenderResponse.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				 
						jaxbMarshaller.marshal(resp, fopErr);
						
						fopErr.flush();
						fopErr.close();
				    }
				    catch(Exception e){
				    	logger.info("LOGGER:"+"возникла ошибка записи результирующего файла на диск в каталог ERROR_DIR");
				    	e.printStackTrace();
				    }
			    }
			}
		}
		return 0;
	}
	
}
