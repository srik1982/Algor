package queryparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import queryparser.QueryParserException.Type;
import misc.BiValidator;

public class QueryParser {

//	public static void main(String[] args) {
//		
//		String qe = "{,timewindow''}";
//		try{
//			Map<String,BiValidator> keyValidatorPairs = new HashMap<String,BiValidator>();
//			keyValidatorPairs.put("status", (_key,_value)->Arrays.asList("completed","failed","aborted").contains(_value.toLowerCase()));
//			System.out.println(parseQueryString(qe,Arrays.asList("timewindow","code","version","status"),keyValidatorPairs));
//		}catch(QueryParserException e){
//			System.out.println("Exception : "+e.getMessage());
//			e.printStackTrace();
//		}
//	}
	
    private static final int SINGLE_QUOTE_ASCII_VALUE = 39;
    private static final int COMMAND_ASCII_VALUE = 44;

    public static Map<String, String> parseQueryString(String queryExpression) throws QueryParserException {
        return parseQueryString(queryExpression, null, null);
    }

    public static Map<String, String> parseQueryString(String queryExpression,
                                                       List<String> allowedKeys) throws QueryParserException {
        return parseQueryString(queryExpression, allowedKeys, null);
    }

    public static Map<String, String> parseQueryString(String queryExpression, List<String> allowedKeys,
                                                       Map<String, BiValidator> keyValidatorPairs) throws QueryParserException {
        try {
            //qe should not be null && should start and end with { }
            if (isNotNull(queryExpression) && startAndEndWithBraces(queryExpression)) {
                Map<String, String> _map = new HashMap<String, String>();
                
                //trim braces. those are necessary only for syntax
                queryExpression = trimBraces(queryExpression);
                
                //apply validation rules -
                // 1. mismatching single quotes?
                // 2. extra commas?
                // 3. value contains commas?
                // split with comma as delimiter
                List<String> params = validateAndSplit(queryExpression);
                for (String token : params) {
                    
                    //
                    // 1. no content : { }
                    // 2. no content between commas : {timewindow:'3d', , }
                    // 3. token not contains :
                    if(token==null || token.trim().length()==0 || !token.contains(":")){
                        throw new QueryParserException(Type.INVALID_SYNTAX,queryExpression);
                    }
                    
                    //remove extra spaces around token
                    token = token.trim();
                  
                    
                    //split individual param and its value using :
                    String[] l_and_r = token.split(":");
                    String _key = l_and_r[0];
                    String _value = l_and_r[1];
                    
                    //validate 3 things here
                    // 1. null key and value
                    // 2. value starts and ends with single quotes '
                    // 3. if allowedKeys != null, key should be in that list
                    if (validateKeyValuePair(_key, _value, allowedKeys)) {
                        //trim extra spaces
                        _key = _key.trim();
                        _value = _value.trim();
                        
                        //KeyValidatorPairs are not used currently. But in some later point of time we can use this to validate value
                        // for eg., flow status value can only be Failed, Aborted or Completed not any random string.
                        // Because we retrofit this parser with existing QueryExpression, keyValidatorPairs is not being used.
                        if (keyValidatorPairs != null && keyValidatorPairs.containsKey(_key)) {                            
                            BiValidator validator = keyValidatorPairs.get(_key);
                            if (validator.validate(_key, _value)) {
                                if(_map.containsKey(_key)){
                                    throw new QueryParserException(Type.DIPLICATE_KEYS,_key,_value);
                                }else{
                                    _map.put(_key, trimQuotes(_value));
                                }
                            } else {
                                throw new QueryParserException(Type.VALIDATOR_REJECTION, _key, _value);
                            }
                        } else {
                            if(_map.containsKey(_key)){
                                throw new QueryParserException(Type.DIPLICATE_KEYS,_key,_value);
                            }else{
                                _map.put(_key, trimQuotes(_value));
                            }
                        }
                    }
                }

                return _map;
            } else {
                return null;
            }
        } catch (QueryParserException qe) {
            throw qe;
        }
    }
    
    private static String trimQuotes(String str){
        return str.replaceAll("'", "");
    }

    private static boolean validateKeyValuePair(String key, String value, List<String> allowedKeys) {
        if (key == null || value == null) {
            throw new QueryParserException(Type.INVALID_SYNTAX, key, value);
        } else if (!checkForMatchingQuotes(value.trim())) {
            if(value.trim().chars().filter(_char -> _char == SINGLE_QUOTE_ASCII_VALUE).count() % 2 != 0)
                throw new QueryParserException(Type.MISMATCHED_QUOTES, key, value);
            else
                throw new QueryParserException(Type.INVALID_WITHOUT_QUOTES, key, value);
        } else if (allowedKeys != null && !allowedKeys.contains(key.trim())) {
            throw new QueryParserException(Type.KEY_NOT_ALLOWED, key, value);
        }
        
        return true;
    }
   

    private static List<String> validateAndSplit(String queryExpression) {
        int quoteCount = (int) queryExpression.chars()
                                              .filter(_char -> _char == SINGLE_QUOTE_ASCII_VALUE) //39 is single quotes
                                              .count();
        
        int commaCount = (int) queryExpression.chars()
                                              .filter(_char -> _char == COMMAND_ASCII_VALUE)
                                              .count();
        
        if(quoteCount == 0){
            throw new QueryParserException(Type.INVALID_WITHOUT_QUOTES,queryExpression);
        }
        else if (quoteCount % 2 != 0) {            
            throw new QueryParserException(Type.MISMATCHED_QUOTES,queryExpression);
        }
       
        String subStr = queryExpression;
        for (int i = 0; i < quoteCount; i++) {
            int beginIndex = subStr.indexOf("'");
            if (beginIndex == -1)
                break;
            subStr = subStr.substring(beginIndex + 1);

            int endIndex = subStr.indexOf("'");

            String value = subStr.substring(0, endIndex);
            if (value.contains(",")) {
                throw new QueryParserException(Type.COMMA_NOT_ALLOWED_IN_VALUE,null,value);
            }

            subStr = subStr.substring(endIndex + 1);
        }
        
        List<String> retVal = Arrays.asList(queryExpression.split(","));
        if(commaCount+1 != retVal.size()){
            throw new QueryParserException(Type.EXTRA_COMMA,queryExpression);
        }
        return retVal;
    }

    private static boolean checkForMatchingQuotes(String value) {
        return startAndEndsWithSingleQuotes(value); //|| startAndEndsWithDoubleQuotes(value);
    }

    private static boolean startAndEndsWithSingleQuotes(String value) {
        return value.startsWith("'") && value.endsWith("'");
    }

    private static boolean isNotNull(String qe) {
        boolean retVal = qe != null && qe.trim().length() > 0;

        if (!retVal) {
            throw new QueryParserException(Type.NULL_EXPRESSION,qe);
        } else {
            return true;
        }
    }

    private static boolean startAndEndWithBraces(String qe) {
        boolean retVal = qe.startsWith("{") && qe.endsWith("}");
        if (!retVal) {
            throw new QueryParserException(Type.NOT_STARTS_ENDS_WITH_BRACES,qe);
        } else {
            return true;
        }
    }

    private static String trimBraces(String qe) {
        if (qe.length() > 2) {
            return qe.substring(1, qe.length() - 1);
        } else {
            throw new QueryParserException(Type.NO_CONTENT,qe);
        }
    }


}
