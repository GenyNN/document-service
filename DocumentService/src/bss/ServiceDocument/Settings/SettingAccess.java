package bss.ServiceDocument.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public abstract class SettingAccess {

	public enum Type{
		REMOTE, LOCAL
	}
	
	public final static String ACCESS_TYPE = "AccessType";
	public final static String DOMAIN = "domain";
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	public final static String USER_INFO = "user_info";
	public final static String HOST = "host";
	
	private static SettingAccess access = null;
	
	public static void init(HashMap<String, Object> settings) throws Exception{
		if(settings.containsKey(ACCESS_TYPE) == false)
			throw new IllegalArgumentException("Invalid access type");
		Type t = (Type)settings.get(ACCESS_TYPE);
 		if(t == Type.LOCAL){
			access = new LocalSettingAccess();
		}
	}
	
	public static InputStream getInputStream(String url) throws IOException{
		return access.getInputStreamImpl(url);
	}
	
	protected abstract InputStream getInputStreamImpl(String url) throws IOException;
	
	private static class LocalSettingAccess extends SettingAccess{

		public LocalSettingAccess(){
			
		}
		@Override
		protected InputStream getInputStreamImpl(String url) throws IOException {
			InputStream stream = new FileInputStream(url);
			return stream;
		}
	}
	
}
