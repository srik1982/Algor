package queryparser;

import java.util.List;

public interface Splitter {
	public List<String> split(String target, String delimiter) throws QueryParserException;
}
