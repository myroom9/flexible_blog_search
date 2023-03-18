package com.whahn.service;

import com.whahn.common.ErrorCode;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.exception.cumtom.BusinessException;
import com.whahn.repository.ApiMetaInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiMetaInformationService {

    private final ApiMetaInformationRepository apiMetaInformationRepository;

    /**
     * API 메타정보 조회 by 사명 코드
     */
    public ApiMetaInformation getApiMetaInformationByCorporationName(String corporationName) {
        return apiMetaInformationRepository.findApiMetaInformationByCorporationName(corporationName)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_SUPPORT_API_CORPORATION));
    }

}
