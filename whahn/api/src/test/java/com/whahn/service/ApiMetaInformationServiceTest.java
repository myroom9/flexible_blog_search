package com.whahn.service;

import com.whahn.common.ErrorCode;
import com.whahn.entity.ApiMetaInformation;
import com.whahn.exception.cumtom.BusinessException;
import com.whahn.type.blog.CorporationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiMetaInformationServiceTest {

    @Autowired
    private ApiMetaInformationService apiMetaInformationService;

    @Test
    @DisplayName("[성공] apiMeta 정보 가져오기")
    void getApiMetaInformationByCorporationNameSuccessTest() {
        ApiMetaInformation apiMetaInformation = apiMetaInformationService.getApiMetaInformationByCorporationName(CorporationType.KAKAO.getCorporationName());

        Assertions.assertThat(apiMetaInformation.getApiKey()).isEqualTo("01975332e2b1c7d87aaaf0efa345bb52");
        Assertions.assertThat(apiMetaInformation.getCorporationName()).isEqualTo(CorporationType.KAKAO.getCorporationName());
    }

    @Test
    @DisplayName("[예외] apiMeta 정보 가져오기 예외")
    void getApiMetaInformationByCorporationNameExceptionTest() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            apiMetaInformationService.getApiMetaInformationByCorporationName(CorporationType.EXTRA.getCorporationName());
        });

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_SUPPORT_API_CORPORATION);
    }

}