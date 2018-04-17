package misc;

public class QueryParsingException extends RuntimeException{
	public static enum Type{
		NULL_EXPRESSION,
		NOT_STARTS_ENDS_WITH_BRACES,
		NO_CONTENT,
		INVALID_SYNTAX,
		MISMATCHED_QUOTES,
		INVALID_WITHOUT_QUOTES
	}
	
	private Type type;
	
	public QueryParsingException(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}	
}
