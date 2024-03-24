package consulting.reason.tax_forms_api.validator;

import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;

public class TaxFormRequestValidator {
    private static final String assessedValueException = "Assessed value out of range\n";
    private static final String appraisedValueException = "Appraised value out of range\n";
    private static final String ratioException = "Ratio out of range\n";
    private static final String commentsException = "Max characters exceeded\n";

    private final Integer assessedValue;
    private final Long appraisedValue;
    private final Double ratio;
    private final String comments;

    private String exceptionMessage = "";

    public TaxFormRequestValidator(TaxFormDetailsRequest taxFormDetailsRequest) {
        this.assessedValue = taxFormDetailsRequest.assessedValue;
        this.appraisedValue = taxFormDetailsRequest.appraisedValue;
        this.ratio = taxFormDetailsRequest.ratio;
        this.comments = taxFormDetailsRequest.comments;
    }

    public String validate() {
        exceptionMessage += validateAppraisedValue();
        exceptionMessage += validateAssessedValue();
        exceptionMessage += validateRatio();
        exceptionMessage += validateComments();

        return exceptionMessage;
    }

    private String validateAssessedValue() {
        if(assessedValue < 0 || assessedValue > 100000) {
            return assessedValueException;
        }

        return "";
    }

    private String validateAppraisedValue() {
        if(appraisedValue < 0 || appraisedValue > 100000) {
            return appraisedValueException;
        }

        return "";
    }

    private String validateRatio() {
        if(ratio < 0 || ratio > 1) {
            return ratioException;
        }

        return "";
    }

    private String validateComments() {
        if(comments.length() > 500) {
            return commentsException;
        }

        return "";
    }
}
