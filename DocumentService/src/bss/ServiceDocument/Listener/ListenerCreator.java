package bss.ServiceDocument.Listener;

public class ListenerCreator {
	public static IListener Create(ListenerType type)
	{
		switch(type)
		{
		case STANDART:
			return new ListenerStandart();
		}
		return null;
	}
}
