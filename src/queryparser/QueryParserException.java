package queryparser;

public class QueryParserException extends RuntimeException{
	private Validation validator;
	private String invalidExpr;
	private String message;
	
	public QueryParserException(String invalidPart, Validation validator, String message) {
		this.invalidExpr = invalidPart;
		this.validator = validator;
		this.message = message;
	}

	public Validation getValidator() {
		return validator;
	}

	public void setValidator(Validation validator) {
		this.validator = validator;
	}

	public String getInvalidExpr() {
		return invalidExpr;
	}

	public void setInvalidExpr(String invalidExpr) {
		this.invalidExpr = invalidExpr;
	}

	@Override
	public String toString() {
		return "QueryParserException [validator=" + validator + ", invalidExpr=" + invalidExpr + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
