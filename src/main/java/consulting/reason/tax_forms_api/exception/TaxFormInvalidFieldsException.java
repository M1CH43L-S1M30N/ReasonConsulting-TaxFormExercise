package consulting.reason.tax_forms_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaxFormInvalidFieldsException extends ResponseStatusException {
    public TaxFormInvalidFieldsException(String exceptions) {
        super(
                HttpStatus.BAD_REQUEST,
                "Invalid fields \n\n" + exceptions
        );
    }
}
