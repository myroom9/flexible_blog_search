package com.whahn.repository;

import com.whahn.entity.ApiMetaInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiMetaInformationRepository extends JpaRepository<ApiMetaInformation, Long> {

    /**
     * API 메타정보 조회 by corporationName (kakao, naver)
     */
    Optional<ApiMetaInformation> findApiMetaInformationByCorporationName(String corporationName);

}
