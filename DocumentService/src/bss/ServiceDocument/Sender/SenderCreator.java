package bss.ServiceDocument.Sender;

public class SenderCreator {
	public static ISender Create(SenderType type)
	{
		switch(type)
		{
		case STANDART_DATASEND:
			return new SenderStandartDataSend();
		}
		return null;		
	}
}
