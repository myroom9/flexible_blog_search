package com.whahn.entity;

import com.whahn.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "api_meta_information")
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiMetaInformation extends BaseEntity {

    @Builder
    public ApiMetaInformation(String corporationName, String apiKey, String extraKey) {
        this.corporationName = corporationName;
        this.apiKey = apiKey;
        this.extraKey = extraKey;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corporation_name", length = 20, nullable = false)
    private String corporationName;

    @Column(name = "api_key", length = 100, nullable = false)
    private String apiKey;

    @Column(name = "extra_key", length = 100)
    private String extraKey;
}
