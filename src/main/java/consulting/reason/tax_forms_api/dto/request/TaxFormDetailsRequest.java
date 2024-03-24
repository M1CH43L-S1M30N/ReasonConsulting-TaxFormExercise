package consulting.reason.tax_forms_api.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TaxFormDetailsRequest {
    // Make fields public to access for validation
    public Integer assessedValue;
    public Long appraisedValue;
    public Double ratio;
    public String comments;
}
