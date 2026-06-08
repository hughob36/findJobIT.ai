package com.user.service.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_experience_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserExperienceProfile {

    @Id
    private UUID id; // Compartirá el mismo ID que UserProfile

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Min(0) @Max(50)
    private Integer yearsOfExperience;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SeniorityLevel seniorityLevel;

    @Column(length = 255) private String linkedinUrl;
    @Column(length = 255) private String githubUrl;
    @Column(length = 255) private String portfolioUrl;
    @Column(length = 500) private String cvFileUrl;

    @ElementCollection(fetch = FetchType.LAZY) // Cambiado a LAZY para evitar dolores de cabeza
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "experience_profile_id"))
    @Column(name = "skill", length = 100)
    @Builder.Default
    private List<String> skills = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_languages", joinColumns = @JoinColumn(name = "experience_profile_id"))
    @Builder.Default
    private List<LanguageEntry> languages = new ArrayList<>();
}
