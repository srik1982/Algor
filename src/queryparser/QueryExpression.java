package queryparser;

public class QueryExpression {
    public static class Subexpression {
        private String lhTerm;
        private String operator;
        private String rhTerm;
        private String boundary;

        public Subexpression(final String lhTerm, final String operator, final String rhTerm) {
            this.lhTerm = lhTerm;
            this.operator = operator;
            this.rhTerm = rhTerm;
        }

        public Subexpression(final String lhTerm, final String operator, final String rhTerm, final String boundary) {
            this.lhTerm = lhTerm;
            this.operator = operator;
            this.rhTerm = rhTerm;
            this.boundary = boundary;
        }

        public String getLhTerm() {
            return lhTerm;
        }

        public String getOperator() {
            return operator;
        }

        public String getRhTerm() {
            return rhTerm;
        }

        public String getBoundary() {
            return boundary;
        }

        public void setBoundary(String boundary) {
            this.boundary = boundary;
        }
    }
}
