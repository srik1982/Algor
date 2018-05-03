package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import misc.QueryParsingException.Type;

public class QueryParser {

	public static void main(String[] args) {
		
		String qe = "{timewindow:null,code:'ABCD',version:'01.00.0000',status:'abcd'}";
		try{
			Map<String,BiValidator> keyValidatorPairs = new HashMap<String,BiValidator>();
			keyValidatorPairs.put("status", (_key,_value)->Arrays.asList("completed","failed","aborted").contains(_value.toLowerCase()));
			System.out.println(parseQueryString(qe,Arrays.asList("timewindow","code","version","status"),keyValidatorPairs));
		}catch(QueryParsingException e){
			System.out.println("Exception : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static Map<String,String> parseQueryString(String queryExpression) throws QueryParsingException{
		return parseQueryString(queryExpression,null,null);
	}
	public static Map<String,String> parseQueryString(String queryExpression, List<String> allowedKeys) throws QueryParsingException{
		return parseQueryString(queryExpression,allowedKeys,null);
	}
	
	public static Map<String,String> parseQueryString(String queryExpression,List<String> allowedKeys, Map<String,BiValidator> keyValidatorPairs) throws QueryParsingException{
		try{
			if(isNotNull(queryExpression) && startAndEndWithBraces(queryExpression)){
				Map<String,String> _map = new HashMap<String,String>();
				
				queryExpression = trimBraces(queryExpression);
				List<String> params = split(queryExpression);
				for(String token : params){
					token = token.trim();
					
					String[] l_and_r = token.split(":");
					String _key = l_and_r[0];
					String _value = l_and_r[1];
					if(validateKeyValuePair(_key, _value, allowedKeys)){
						if(keyValidatorPairs!=null && keyValidatorPairs.containsKey(_key)){
							BiValidator validator = keyValidatorPairs.get(_key);
							if(validator.validate(_key, _value)){
								_map.put(_key.trim(), _value.trim());
							}else{
								throw new QueryParsingException(Type.VALIDATOR_REJECTION, _key, _value);
							}
						}else{
							_map.put(_key.trim(), _value.trim());
						}
					}
				}
				
				return _map;
			}else{
				return null;
			}
		}catch(QueryParsingException qe){
			throw qe;
		}
	}
	
	private static boolean validateKeyValuePair(String key, String value, List<String> allowedKeys){
		if(key == null || value == null){
			throw new QueryParsingException(Type.INVALID_SYNTAX,key,value);
		}else if(!checkForMatchingQuotes(value)){
			if(value.chars().filter(_char -> _char == 39).count() %2!=0)
				throw new QueryParsingException(Type.MISMATCHED_QUOTES,key,value);
			else
				throw new QueryParsingException(Type.NOT_STARTS_ENDS_WITH_BRACES,key,value);
		}else if(allowedKeys!=null && !allowedKeys.contains(key)){
			throw new QueryParsingException(Type.KEY_NOT_ALLOWED,key,value);
		}
		
		return true;
	}
	
	private static List<String> split(String queryExpression){
		int quoteCount = (int)queryExpression.chars().filter(_char -> _char == 39).count();
		
		if(quoteCount%2!=0 || quoteCount==0){
			throw new QueryParsingException(Type.MISMATCHED_QUOTES);
		}
		
		String subStr = queryExpression;
		for(int i=0;i<quoteCount;i++){
			int beginIndex = subStr.indexOf("'");
			if(beginIndex == -1)break;
			subStr = subStr.substring(beginIndex+1);
			
			int endIndex = subStr.indexOf("'");
			
			String value = subStr.substring(0, endIndex);
			if(value.contains(",")){
				throw new QueryParsingException(Type.COMMA_NOT_ALLOWED_IN_VALUE);
			}
			
			subStr = subStr.substring(endIndex+1);
		}
		
		List<String> retVal = new ArrayList<String>();
		
		String [] arr = queryExpression.split(",");
		for(String str : arr){
			retVal.add(str);
		}
		
		
		return retVal;		
	}
	
	private static boolean checkForMatchingQuotes(String value){
		return startAndEndsWithSingleQuotes(value) ;//|| startAndEndsWithDoubleQuotes(value);
	}
	
	private static boolean startAndEndsWithSingleQuotes(String value){
		return value.startsWith("'") && value.endsWith("'"); 
	}
	
	private static boolean isNotNull(String qe){
		boolean retVal = qe!=null && qe.trim().length()>0;
		
		if(!retVal){
			throw new QueryParsingException(Type.NULL_EXPRESSION);
		}else{
			return true;
		}
	}
	
	private static boolean startAndEndWithBraces(String qe){
		boolean retVal = qe.startsWith("{") && qe.endsWith("}");
		if(!retVal){
			throw new QueryParsingException(Type.NOT_STARTS_ENDS_WITH_BRACES);
		}else{
			return true;
		}
	}
	
	private static String trimBraces(String qe){
		if(qe.length()>2){
			return qe.substring(1, qe.length()-1);
		}else{
			throw new QueryParsingException(Type.NO_CONTENT);
		}
	}
	
	

}
