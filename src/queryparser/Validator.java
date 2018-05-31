package queryparser;

import java.util.List;

public interface Validator {
	public int validate(String target, String s1,String s2) throws QueryParserException;
}
