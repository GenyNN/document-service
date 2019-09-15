package bss.ServiceDocument.Settings;

import org.w3c.dom.Node;

public class Sources extends SettingBase {
	
	
	public static Sources CreateFromNode(Node node){
		if(node == null)
			return null;
		
		Sources objOut = new Sources();
		objOut.FillFromNode(node);
		return objOut;
	}
	
}
