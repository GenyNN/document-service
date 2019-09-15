package bss.ServiceDocument.Main;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import bss.ServiceDocument.Settings.Setting;
import bss.ServiceDocument.Settings.SettingAccess;

/**
 * Servlet implementation class MainServlet
 */

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<EmlHandler> emlHandlers = new ArrayList<EmlHandler>();
    /**
     * Default constructor. 
     */
    public MainServlet() {
    	super();
        // TODO Auto-generated constructor stub
    }
    
    private void initConfigAccess() throws Exception{
		ServletConfig config = getServletConfig();
		
		String AccessType = config.getInitParameter("AccessType");
		SettingAccess.Type accessType = SettingAccess.Type.valueOf(AccessType);
		HashMap<String, Object> settingMap = new HashMap<String, Object>();
		settingMap.put(SettingAccess.ACCESS_TYPE, accessType);
		
		SettingAccess.init(settingMap);
	}
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try {
			initConfigAccess();
			Setting.Create(this.getServletConfig().getInitParameter("SettingFullFileName"));
			for(int i = 0; i < Setting.getCountEmlHandlers(); i++){
				try{
					EmlHandler emlHandler = new EmlHandler(Setting.getSetting(i));				
					emlHandlers.add(emlHandler);
					emlHandler.start();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy(); 
		for(EmlHandler emlHandler: emlHandlers)
		{
			emlHandler.stop();
		}
	}

}
