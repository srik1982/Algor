package misc;

public class QueryParsingException extends RuntimeException{
	public static enum Type{
		NULL_EXPRESSION,
		NOT_STARTS_ENDS_WITH_BRACES,
		NO_CONTENT,
		INVALID_SYNTAX,
		MISMATCHED_QUOTES,
		INVALID_WITHOUT_QUOTES,
		COMMA_NOT_ALLOWED_IN_VALUE,
		KEY_NOT_ALLOWED,
		VALIDATOR_REJECTION
	}
	
	private Type type;
	private String key;
	private String value;
	
	public QueryParsingException(Type type) {
		this.type = type;
	}
	public QueryParsingException(Type type, String key) {
		this.type = type;
		this.key = key;
	}
	public QueryParsingException(Type type, String key, String value) {
		this.type = type;
		this.value = value;
		this.key = key;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Type").append(" : ").append(type).append(" , ");
		sb.append("Key").append(" : ").append(key).append(" , ");
		sb.append("Value").append(" : ").append(value);
		return sb.toString();
	}
	
}
