package bss.ServiceDocument.Transport;

public class TransportCreator {
	public static ITransport Create(TransportType type)
	{
		switch(type){
		case QUEUE_INPUT_FILES_BOUNDLE_GET:
			return new TransportQueueInputFilesBoundleGet();
		case INPUT:
			return new TransportIn();
		case OUTPUT:
			return new TransportOut();
		}
		
		return null;
	}
}
