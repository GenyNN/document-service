package bss.ServiceDocument.Process;

public class ProcessCreator {
	public static IProcess Create(ProcessType type)
	{
		switch(type)
		{
		case STANDART:
			return new ProcessStandart();
		}
		return null;
	}
}
