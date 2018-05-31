package queryparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import queryparser.QueryExpression.Subexpression;
import static queryparser.Validation.*;

public class NonRegexQueryParser2 {
	private static final String COMMA = ",";
	private static final String OPENING_BRACE = "{";
	private static final String CLOSING_BRACE = "}";
    
    private static final HashMap<Validation,Integer> code = new HashMap<Validation,Integer>();
    private static final HashMap<Validation,Integer> sum = new HashMap<Validation,Integer>();
    
    static {
    	code.put(S1_NULL_CHECK, 1);
    	code.put(S1_STARTS_ENDS_WITH, 5);
    	code.put(S1_EMPTY_QUERY, 13);
    	code.put(S1_ZERO_BOUNDARY_CHARS, 23);
    	code.put(S1_ODD_BOUNDARY_CHARS, 57);
    	code.put(S2_NULL_CHECK, 103);
    	code.put(S2_CONTAINS_KEY_VAL_DELIM, 211);
    	code.put(S3_NULL_CHECK, 431);
    	code.put(S3_KEYS_WITHIN_ALLOWED_KEYS, 881);
    	code.put(S3_BOUNDARY_CHARS, 1877);
    	code.put(S4_NULL_CHECK, 3793);//3793
    	
    	sum.put(S1_NULL_CHECK, 1);
    	sum.put(S1_STARTS_ENDS_WITH, 6);
    	sum.put(S1_EMPTY_QUERY, 19);
    	sum.put(S1_ZERO_BOUNDARY_CHARS, 42);
    	sum.put(S1_ODD_BOUNDARY_CHARS, 99);
    	sum.put(S2_NULL_CHECK, 202);
    	sum.put(S2_CONTAINS_KEY_VAL_DELIM, 413);
    	sum.put(S3_NULL_CHECK, 844);
    	sum.put(S3_KEYS_WITHIN_ALLOWED_KEYS, 1725);
    	sum.put(S3_BOUNDARY_CHARS, 3602);
    	sum.put(S4_NULL_CHECK, 7395);
    }
    private static Map<Validation,Validator>  validators = new HashMap<Validation,Validator> ();
    private static Trimmer trimmer = null;
    private static Splitter splitter = null;
    
    
	public static void main(String[] args) {
		
		String qe = "{timewindow:'3d',code:'//'}";
		try{
			List<Subexpression> subexs = parseQE(qe, ":", Arrays.asList("/","'"), "timewindow,code,version,status");
			System.out.println("-----------------------------------------------");
			for(Subexpression s : subexs){
				System.out.println(s.getLhTerm()+" "+ s.getOperator() + " "+s.getBoundary()+""+s.getRhTerm()+""+s.getBoundary());
			}
		}catch(QueryParserException e){
			System.out.println("Exception : "+e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	public static void initUtilities(String queryExpression){
		//for the sake of shortening the lambda exprs, lets just call the 
		//target string as 't'
		//start string as 's' i.e., boundary start should be / , the param name is just 's'
		//end string as 'e' 
		// some cases we need to ignore string, they will be i1, i2
		//other strings : b - boundary chars, b1,b2
		validators.put(S1_NULL_CHECK,(t,i1,i2) -> (t!=null && t.trim().length()>0 ? code.get(S1_NULL_CHECK) : 0));
		validators.put(S1_STARTS_ENDS_WITH,(t,s,e) -> (t.startsWith(s) && t.endsWith(e) ? code.get(S1_STARTS_ENDS_WITH) : 0));
		validators.put(S1_EMPTY_QUERY,(t,s,e) -> (t.replace(s,"").replace(e,"").trim().length()>0 ? code.get(S1_EMPTY_QUERY) : 0));
		validators.put(S1_ZERO_BOUNDARY_CHARS,(t,s,e) -> ((t.chars().filter(_char -> _char == s.charAt(0)).count() != 0 || t.chars().filter(_char -> _char == e.charAt(0)).count() != 0) ? code.get(S1_ZERO_BOUNDARY_CHARS) : 0));
		validators.put(S1_ODD_BOUNDARY_CHARS,(t,s,e) -> ((t.chars().filter(_char -> _char == s.charAt(0)).count() %2 == 0) && (t.chars().filter(_char -> _char == e.charAt(0)).count() %2 == 0) ? code.get(S1_ODD_BOUNDARY_CHARS) : 0));
		
		
		validators.put(S2_NULL_CHECK,(t,i1,i2) -> (t!=null && t.trim().length()>0 ? code.get(S2_NULL_CHECK) : 0));
		validators.put(S2_CONTAINS_KEY_VAL_DELIM,(t,d,i) -> (t!=null && t.contains(d) ? code.get(S2_CONTAINS_KEY_VAL_DELIM) : 0));
		
		
		validators.put(S3_NULL_CHECK,(t,i1,i2) -> (t!=null && t.trim().length()>0 ? code.get(S3_NULL_CHECK) : 0));
		validators.put(S3_KEYS_WITHIN_ALLOWED_KEYS,(t,keys,ignore) -> (keys!=null && t!=null && keys.contains(t) ? code.get(S3_KEYS_WITHIN_ALLOWED_KEYS) : 0));
		validators.put(S3_BOUNDARY_CHARS,(t,b1,b2) -> (t!=null && t.startsWith(b1) && t.endsWith(b1) || t!=null && t.startsWith(b2) && t.endsWith(b2))  ? code.get(S3_BOUNDARY_CHARS) : 0);		
		
		
		validators.put(S4_NULL_CHECK,(target,ignore1,ignore2) -> (target!=null && target.trim().length()>0 ? code.get(S4_NULL_CHECK) : 0));
		
		
		
		trimmer = (target,replaceStr1,replaceStr2) -> (replaceStr1 == null && replaceStr2 == null ? ( target!=null ? target.trim() : null) : ( target!=null && !target.isEmpty() ? target.trim().replace(replaceStr1, "").replace(replaceStr2, "") : null));
		splitter = (target,delim) -> (target!=null && !target.isEmpty() && delim!=null && !delim.isEmpty() ? Arrays.asList(target.split(delim)) : null);
		
		
	}
	
	public static List<Subexpression> parseQE(String queryExpression, String delimiter, List<String> allowedBoundaries, String allowedKeys){
		initUtilities(queryExpression);
		return reallyParse(queryExpression,delimiter,allowedBoundaries,allowedKeys);
	}
	
	private static List<Subexpression> reallyParse(String queryExpression, String delimiter, List<String> boundary, String allowedKeys){
		
		if(boundary==null || boundary.size()!=2) {
			System.out.println("Invalid boundary chars.");
			//throw exception here
		}
		
		List<Subexpression> subexs = new ArrayList<QueryExpression.Subexpression>();
			
			
		//okay, i forgot, at each validation layer, we need to throw some exceptions.
		int output = validators.get(S1_NULL_CHECK).validate(queryExpression, null, null);
		output +=  output == sum.get(S1_NULL_CHECK) ? validators.get(S1_STARTS_ENDS_WITH).validate(queryExpression, OPENING_BRACE, CLOSING_BRACE) : 0;
		output +=  output == sum.get(S1_STARTS_ENDS_WITH)? validators.get(S1_EMPTY_QUERY).validate(queryExpression, OPENING_BRACE, CLOSING_BRACE) : 0;
		output +=  output == sum.get(S1_EMPTY_QUERY) ? validators.get(S1_ZERO_BOUNDARY_CHARS).validate(queryExpression, boundary.get(0), boundary.get(1)) : 0;
		output +=  output == sum.get(S1_ZERO_BOUNDARY_CHARS) ? validators.get(S1_ODD_BOUNDARY_CHARS).validate(queryExpression, boundary.get(0), boundary.get(1)) : 0;
		
		//if output > 42 all 4 conditions true, we trim, split and move to next stage
		if(output == sum.get(S1_ODD_BOUNDARY_CHARS)){
			queryExpression = trimmer.trim(queryExpression, OPENING_BRACE, CLOSING_BRACE);
			List<String> queries = splitter.split(queryExpression, COMMA);
			
			if(queries !=null){
				
				for(int i=0;i<queries.size();i++){
					
					String query = queries.get(i);
					//stage 2 validations start here
					output = sum.get(S1_ODD_BOUNDARY_CHARS);				
					output += validators.get(S2_NULL_CHECK).validate(query, null, null); //output == 99
					output += output == sum.get(S2_NULL_CHECK) ? validators.get(S2_CONTAINS_KEY_VAL_DELIM).validate(query, delimiter, null) : 0; //output == 202
					
					if(output == sum.get(S2_CONTAINS_KEY_VAL_DELIM)){
						
						query = trimmer.trim(query, null, null);
						List<String> leftAndRight = splitter.split(query, delimiter);
						String left = leftAndRight==null || leftAndRight.isEmpty() ? null: leftAndRight.get(0).trim();
						String right = leftAndRight!=null && !leftAndRight.isEmpty() && leftAndRight.size()==2 ? leftAndRight.get(1).trim() : null;
						
						//Stage 3 validations starts here
						int l_notnull = validators.get(S3_NULL_CHECK).validate(left, null, null);
						int r_notnull = validators.get(S3_NULL_CHECK).validate(right, null, null);
						
						if(l_notnull == code.get(S3_NULL_CHECK) && r_notnull == code.get(S3_NULL_CHECK)){
							output += code.get(S3_NULL_CHECK); // we passed the null checks. output should be 413
							output += validators.get(S3_KEYS_WITHIN_ALLOWED_KEYS).validate(left, allowedKeys, null); //output == 844
							output += output == sum.get(S3_KEYS_WITHIN_ALLOWED_KEYS) ?  validators.get(S3_BOUNDARY_CHARS).validate(right, boundary.get(0), boundary.get(1)) : 0; //output == 1725
							
							if(output == sum.get(S3_BOUNDARY_CHARS)){
								String _boundary = String.valueOf(right.charAt(0));
								left = trimmer.trim(left, null, null);
								right = trimmer.trim(right, _boundary, _boundary);
								
								output += validators.get(S4_NULL_CHECK).validate(right, null, null);
								if(output == sum.get(S4_NULL_CHECK)){
									Subexpression sub = new Subexpression(left, delimiter, right, _boundary);
									subexs.add(sub);
								}else{
									throw new QueryParserException(right,S4_NULL_CHECK,"Value is null in query term : "+query);
								}
							}else{
								if(output == sum.get(S3_KEYS_WITHIN_ALLOWED_KEYS))
									throw new QueryParserException(right,S3_BOUNDARY_CHARS, "Value "+right+" doesn't start and/or end with boundary chars [ ' / ] OR doesn't have matching boundary chars");
								else
									throw new QueryParserException(right,S3_KEYS_WITHIN_ALLOWED_KEYS,"Key "+left+" is not allowed. Allowed keys: "+allowedKeys);
							}
						}else{
							throw new QueryParserException(query,S3_NULL_CHECK,"Key or Value is null. Key : "+left+" , value: "+right);
						}
						
					}else{
						if(output == sum.get(S2_NULL_CHECK))
							throw new QueryParserException(query,S2_CONTAINS_KEY_VAL_DELIM,"Delimiter ["+delimiter+"] not found in query expression: "+query);
						else
							throw new QueryParserException(queryExpression,S2_NULL_CHECK,"Query is null : "+queryExpression+" .Possibly there are extra commas");
					}
					
				}// end of for loop
			}else{
				throw new QueryParserException(queryExpression,NULL_QUERY,"Query expression is null or empty. QueryExpression : "+queryExpression);
			}
		}else{
			if(output == 0)
				throw new QueryParserException(queryExpression,NULL_QUERY,"Query expression is null or empty. QueryExpression : "+queryExpression);			
			else if(output == sum.get(S1_NULL_CHECK))
	    		throw new QueryParserException(queryExpression, S1_STARTS_ENDS_WITH, "Query expression doesn't start with '{' and/or end with '}' ");
	    	else if(output == sum.get(S1_STARTS_ENDS_WITH))
	    		throw new QueryParserException(queryExpression,S1_EMPTY_QUERY,"No content inside braces");
	    	else if(output == sum.get(S1_EMPTY_QUERY))
	    		throw new QueryParserException(queryExpression,S1_ZERO_BOUNDARY_CHARS,"Query Expression doesn't have boundary chars ['/]. Values must be enclosed in a pair of boundary chars. For Example: code : 'XYZ' or code : /XYZ/");
	    	else
	    		throw new QueryParserException(queryExpression,S1_ODD_BOUNDARY_CHARS,"Query Expression should have matching boundary chars ['/] for value");
			
			
		}
		
		return subexs;
	}
	
}
