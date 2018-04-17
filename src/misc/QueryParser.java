package misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.IntPredicate;

import misc.QueryParsingException.Type;

public class QueryParser {

	public static void main(String[] args) {
		
		String qe = "{timewindow:'3d',code:'ABCD',version:'01,00,0000',status:'completed'}";
		try{
			System.out.println(parseQueryString(qe));
		}catch(QueryParsingException e){
			System.out.println("Exception type : "+e.getType());
			e.printStackTrace();
		}
	}
	
	public static Map<String,String> parseQueryString(String queryExpression) throws QueryParsingException{
		try{
			if(isNotNull(queryExpression) && startAndEndWithBraces(queryExpression)){
				Map<String,String> _map = new HashMap<String,String>();
				
				queryExpression = trimBraces(queryExpression);
				List<String> params = split(queryExpression);
				for(String token : params){
					token = token.trim();
					
					String[] l_and_r = token.split(":");
					if(l_and_r.length != 2){
						throw new QueryParsingException(Type.INVALID_SYNTAX);
					}else if(!checkForMatchingQuotes(l_and_r[1].trim())){
						throw new QueryParsingException(Type.MISMATCHED_QUOTES);
					}else{
						_map.put(l_and_r[0].trim(), l_and_r[1].trim());
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
	
	private static List<String> split(String queryExpression){
		int singleQuoteCount = (int) queryExpression.chars().filter(value -> value == 239).count();
		List<String> retVal = new ArrayList<String>();
		if(singleQuoteCount %2 == 0){
			String [] arr = queryExpression.split("',");
			for(String str : arr){
				retVal.add(str+"'");
			}
		}
		
		return retVal;
		
//		new IntPredicate(){
//
//			@Override
//			public boolean test(int value) {				
//				return value == 239;
//			}
//			
//		})
	}
	
//	private static boolean isValidWithoutQuotes(String value){
//		return !value.contains(" "); 
//	}
	
	private static boolean checkForMatchingQuotes(String value){
		return startAndEndsWithSingleQuotes(value) ;//|| startAndEndsWithDoubleQuotes(value);
	}
	
	private static boolean startAndEndsWithSingleQuotes(String value){
		return value.startsWith("'") && value.endsWith("'"); 
	}
	
//	private static boolean startAndEndsWithDoubleQuotes(String value){
//		return value.startsWith("\"") && value.endsWith("\""); 
//	}
	
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
