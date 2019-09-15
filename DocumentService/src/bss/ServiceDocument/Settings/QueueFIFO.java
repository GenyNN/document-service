package bss.ServiceDocument.Settings;

import org.w3c.dom.Node;

public class QueueFIFO extends SettingBase {
	
	public static QueueFIFO CreateFromNode(Node node)
	{
		if(node == null)
			return null;
		
		QueueFIFO objOut = new QueueFIFO();
		objOut.FillFromNode(node);
		
		return objOut;
	}
}
