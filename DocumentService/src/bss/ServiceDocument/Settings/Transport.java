package bss.ServiceDocument.Settings;

import org.w3c.dom.Node;

public class Transport extends SettingBase {
	

	public static Transport CreateFromNode(Node node)
	{
		if(node == null)
			return null;
		
		Transport objOut = new Transport();
		
		objOut.FillFromNode(node);
		return objOut;
	}
	
}
