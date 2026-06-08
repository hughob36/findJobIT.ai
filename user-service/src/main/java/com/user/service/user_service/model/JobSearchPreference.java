package com.user.service.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_search_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSearchPreference {

    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Size(max = 150)
    @Column(length = 150)
    private String desiredRole;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private WorkModality preferredModality;

    @DecimalMin("0.0")
    @Column(precision = 10, scale = 2)
    private BigDecimal expectedSalaryMin;

    @DecimalMin("0.0")
    @Column(precision = 10, scale = 2)
    private BigDecimal expectedSalaryMax;

    @Size(max = 3)
    @Column(length = 3)
    private String salaryCurrency;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ContractType preferredContractType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_preferred_countries", joinColumns = @JoinColumn(name = "search_preference_id"))
    @Column(name = "country", length = 100)
    @Builder.Default
    private List<String> preferredCountries = new ArrayList<>();
}