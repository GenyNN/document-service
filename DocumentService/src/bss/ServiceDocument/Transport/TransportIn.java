package bss.ServiceDocument.Transport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import bss.ServiceDocument.Net.SmtpServerConection;
import bss.ServiceDocument.Queue.DataExTransport;
import bss.ServiceDocument.Transport.MessageTransport.Statuses;
import bss.ServiceDocument.utils.DBKArray;
import bss.ServiceDocument.utils.DBKObject;
import bss.ServiceDocument.utils.DBKParams;

public class TransportIn implements ITransport{
	
	private List<Source> listSources = new ArrayList<Source>();
	private static Logger logger = Logger.getLogger(TransportIn.class);
	private int quantityRead = 0;
	DBKParams params = new DBKParams();
	
	public synchronized ITransport Accept() throws Exception {
		ITransport transOut = TransportCreator.Create(TransportType.QUEUE_INPUT_FILES_BOUNDLE_GET);
		DBKParams paramsInit = new DBKParams();			
		
		DBKArray arr = new DBKArray("SourceFiles");

		int countRead = 0;
		for(Source source : listSources){
			String directoryPath = source.getSrcDir();
			String directoryArchPath = source.getSrcArchivDir();
			String errDir = source.getErrDir(); 
			File fileSourceDirectory = null;
			Integer readInterval = source.getSrcInterval();
			
			boolean isSourceExistsAndCanRead = false;
			try{
				fileSourceDirectory = new File(directoryPath);
				isSourceExistsAndCanRead = fileSourceDirectory.canRead();
				if(!isSourceExistsAndCanRead){
					logger.info("LOGGER:"+"нет прав на чтение исходной директории или она не задана");
				}
			}
			catch(NullPointerException e){
				logger.info("LOGGER:"+"исходная дректория не задана");
				isSourceExistsAndCanRead = false;
				e.printStackTrace();
			}
			
			if (isSourceExistsAndCanRead){
				File[] filesInFolder = fileSourceDirectory.listFiles();
				if(filesInFolder != null){
					Thread.sleep(readInterval);
					for(File fileObj : filesInFolder){
						IDocumentFileTransport entity = new EmlFileTransportEntity();
						entity.setStatus(Statuses.OK);
						
						if(fileObj.canRead()){
							
							byte [] fileDataBuf = new byte[(int)fileObj.length()];
							byte [] fileData = null;
							FileInputStream fileInputStream=null;
							try{
								 fileInputStream = new FileInputStream(fileObj);
								 fileInputStream.read(fileDataBuf);
								 fileInputStream.close();
								 fileData = fileDataBuf;
							}
							catch(Exception e){
								logger.info("LOGGER:"+"произошла ошибка чтения файла " + fileObj.getName() +" из каталога источника");
								e.printStackTrace();
							}
							
							//boolean isInArch = false;
							//boolean isInErr = false;
							
							if (fileData != null){
								entity.setFileContent(fileData);
								
								boolean isParseError = false;
								//проверка есть ли ошибки парсинга
								if ( entity.checkValid() == false){
									isParseError = true;
									logger.info("LOGGER:"+"произошла ошибка парсинга файла из каталога источника SRC_DIR");
									entity.setStatus(MessageTransport.Statuses.PARSING_SRC_ERROR);
								}
								if(isParseError){
									
									try{
										File newErrFile = new File(errDir + "\\" + fileObj.getName());
										File newErrDir = new File(errDir);
										FileOutputStream fosErr = new FileOutputStream(newErrFile);
										fosErr.write(fileData);
										fosErr.close();
										//isInErr = true;
									}
									catch(Exception e){
										logger.info("LOGGER:"+"ошибка записи в каталог ошибок источника ERR_DIR " + errDir);
										e.printStackTrace();
									}
								}
								else{
									try{
										File newArchFile = new File(directoryArchPath + "\\" + fileObj.getName());
										FileOutputStream fosArch = new FileOutputStream(newArchFile);
										fosArch.write(fileData);
										fosArch.close();
										//isInArch = true;
									}
									catch(Exception e){
										logger.info("LOGGER:"+"ошибка записи в архивный каталог источника ARCHIVE_SRC_DIR " + directoryArchPath);
										e.printStackTrace();
									}
								}
								
								
								//if (isInArch || isInErr){
									//удаление
									boolean isDeleted = true;
									try{
										isDeleted = fileObj.delete();
										if (!isDeleted){
											//entity.setStatus(MessageTransport.Statuses.DELETING_SRC_ERROR);
											logger.info("LOGGER:"+"не удачная попытка удаление файла из исходного каталога " + fileObj.getName());
										}
									}
									catch(Exception e){
										isDeleted = false;
										logger.info("LOGGER:"+"не удачная попытка удаление файла из исходного каталога " + fileObj.getName());
										e.printStackTrace();
										//entity.setStatus(MessageTransport.Statuses.DELETING_SRC_ERROR);
									}
									
									if (isDeleted){
										//установка остальных свойств интервала и тд
										entity.setFileName(fileObj.getName());
										entity.setInterval(source.getSrcInterval());
										entity.setQuantToSend(source.getSrcQuantToSend());
										
										DBKObject field1 = new DBKObject(entity, "SourceFile");
										arr.Add(field1);
									}
								//}
								countRead++;
								if(countRead == quantityRead){
									break;
								}
							}	
						}
					}
					if(countRead == quantityRead){
						Thread.sleep(readInterval);
						break;
					}
				}
			}
		}
		paramsInit.SetObject("Files", arr);
		paramsInit.Set("Files", arr);
		
		transOut.Initialize(paramsInit);
		return transOut;
	}
	
	public DataExTransport Read(SmtpServerConection connnection) throws Exception {
		throw new UnsupportedOperationException();
	}

	public void Initialize(DBKParams params) {
		this.params = params;
		try{
			DBKArray arraySources = (DBKArray)params.Get("Sources");
			if(arraySources == null)
				return;
			DBKArray array = arraySources.GetAt_Array(0);
			if(array == null){
				return;	
			}
			for(int k = 0; k < array.Count(); k++)
			{
				DBKArray arr = (DBKArray)array.GetAt_Array(k);
				if(arr == null)
					continue;
				
				//SRC_DIR
				String srcDirStr = arr.GetAt_String(0);
				Integer srcPriorInt = null;
				Integer srcIntervalInt = null;
				Integer srcQuantToSend = null;
				if(srcDirStr.isEmpty()){
					logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: не задан исходный каталог SRC_DIR " + srcDirStr);
				}
				else{
					File srcDir = new File(srcDirStr);
					File[] listFiles = srcDir.listFiles();
					if (listFiles == null){
						logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: ошибка доступа к файлам исходного каталога SRC_DIR "+ srcDirStr);
					}
				}
				//SRC_PRIOR
				try{
					srcPriorInt = Integer.parseInt(arr.GetAt_String(1));
				}
				catch(NumberFormatException e){
					logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: не верно указан приоритет");
					e.printStackTrace();
				}
				//ARCHIVE_SRC_DIR
				String srcArchivDirStr = arr.GetAt_String(2);
				if(srcArchivDirStr.isEmpty()){
					logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: не задан каталог архивных файлов ARCHIVE_SRC_DIR " + srcArchivDirStr);
				}
				else{
					File srcArchivDir = new File(srcArchivDirStr);
					
					File[] listFiles = srcArchivDir.listFiles();
					if (listFiles == null){
						logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: ошибка доступа к файлам каталога архивных файлов ARCHIVE_SRC_DIR " + srcArchivDirStr);
					}
				}
				//ERROR_DIR
				String errDirStr = arr.GetAt_String(3);
				if(errDirStr.isEmpty()){
					logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: не задан каталог ошибок файлов ERR_DIR " + errDirStr);
				}
				else{
					File errDir = new File(errDirStr);
					File[] listFiles = errDir.listFiles();
					if (listFiles == null){
						logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: ошибка доступа к файлам каталога архивных файлов ERR_DIR " + errDirStr);
					}
				}
				
				//INTERVAL
				try{
					srcIntervalInt = Integer.parseInt(arr.GetAt_String(4));
				}
				catch(NumberFormatException e){
					logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: не верно указан интервал");
					e.printStackTrace();
				}
				//QUANT_TO_SEND
				try{
					srcQuantToSend = Integer.parseInt(arr.GetAt_String(5));
				}
				catch(NumberFormatException e){
					logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener: не верно указанно количество повторной отправки");
					e.printStackTrace();
				}
				if (!srcDirStr.isEmpty() && srcPriorInt != null && !srcArchivDirStr.isEmpty() && !errDirStr.isEmpty() && srcIntervalInt != null && srcIntervalInt != null){
					Source source = new Source(srcDirStr, srcPriorInt, srcArchivDirStr, errDirStr, srcIntervalInt, srcQuantToSend);
					listSources.add(source);
				}
			}
			Collections.sort(listSources, new SourceComparable());
			quantityRead = Integer.parseInt(params.GetString("QuantityRead"));
		}
		catch(Exception e){
			logger.info("LOGGER:"+"ошибка задания конфигурации источника Listener");
			e.printStackTrace();
		}
		
	}
	
	public int Write(DataExTransport data) {
		throw new UnsupportedOperationException();
	}
}
