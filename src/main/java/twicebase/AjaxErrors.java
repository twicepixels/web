package twicebase;

import java.util.ArrayList;
import java.util.List;

public class AjaxErrors {
    private List<ErrorMessage> errors = new ArrayList<ErrorMessage>();

    public Boolean getHasErrors() {
        return errors.size() > 0;
    }

    public Object[] getErrors() {
        return errors.toArray();
    }

    public void add(String field, String message) {
        errors.add(new ErrorMessage() {{
            setField(field);
            setMessage(message);
        }});
    }

    class ErrorMessage {
        private String field;
        private String message;

        public String getField() {
            return field;
        }

        void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        void setMessage(String message) {
            this.message = message;
        }
    }
}