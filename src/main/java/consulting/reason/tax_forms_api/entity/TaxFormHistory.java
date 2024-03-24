package consulting.reason.tax_forms_api.entity;

import consulting.reason.tax_forms_api.enums.TaxFormHistoryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaxFormHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "tax_form_id")
    private Integer id;
    @CreationTimestamp
    private ZonedDateTime createdAt;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TaxFormHistoryType type;

}
