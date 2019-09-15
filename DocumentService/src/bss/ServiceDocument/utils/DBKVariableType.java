package bss.ServiceDocument.utils;

/**
 * Created by IntelliJ IDEA.
 * User: krasilnikov
 * Date: 26.03.11
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public enum DBKVariableType
{
	
    NONE(0), INTEGER(1), DOUBLE(2), STRING(3),
    ARRAY(4), SHORT(5), VECTOR_VARIABLES(6), VECTOR_ARRAY(7), OBJECT(8);
    
    private int id = 0;
    
    DBKVariableType(int val){
    	id = val;
    }
    
    public int getID(){
    	return id;
    }
    
    public static DBKVariableType valueOf(int value){
    	DBKVariableType type = null;
    	switch(value){
    		case 0:
    			type = NONE; break;
    		case 1:
    			type = INTEGER; break;
    		case 2:
    			type = DOUBLE; break;
    		case 3:
    			type = STRING; break;
    		case 4:
    			type = ARRAY; break;
    		case 5:
    			type = SHORT; break;
    		case 6:
    			type = VECTOR_VARIABLES; break;
    		case 7:
    			type = VECTOR_ARRAY; break;
    		case 8:
    			type = OBJECT; break;
    	}
    	return type;
    }
}
