package com.user.service.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Table(name = "language_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageEntry {


    @Size(max = 50)
    @Column(name = "language_name", length = 50)
    private String name;       // ej: "English", "Spanish"

    @Enumerated(EnumType.STRING)
    @Column(name = "language_level", length = 20)
    private LanguageLevel level;

}

