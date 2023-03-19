package com.whahn.repository;

import com.whahn.entity.ApiMetaInformation;
import com.whahn.type.blog.CorporationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.*;

@EnableJpaAuditing
@DataJpaTest
class ApiMetaInformationRepositoryTest {

    @Autowired
    private ApiMetaInformationRepository apiMetaInformationRepository;

    @Test
    @DisplayName("[성공] api 메타정보 저장하기")
    void saveApiMetaInformation() {
        ApiMetaInformation testApiMeta = ApiMetaInformation.builder()
                .apiKey("testApiKey")
                .corporationName(CorporationType.KAKAO.getCorporationName())
                .build();
        ApiMetaInformation apiMetaInformation = apiMetaInformationRepository.save(testApiMeta);

        Assertions.assertThat(testApiMeta.getApiKey()).isEqualTo(apiMetaInformation.getApiKey());
        Assertions.assertThat(testApiMeta.getCorporationName()).isEqualTo(apiMetaInformation.getCorporationName());
    }

    @Test
    @DisplayName("[성공] api 메타정보 가져오기 by 회사명")
    void findApiMetaInformationByCorporationNameSuccessTest() {
        ApiMetaInformation apiMetaInformation = apiMetaInformationRepository.findApiMetaInformationByCorporationName(CorporationType.KAKAO.getCorporationName())
                .orElse(null);
        Assertions.assertThat(apiMetaInformation).isNotNull();
    }

    @Test
    @DisplayName("[성공] api 메타정보 가져오기 by 회사명 (null)")
    void findApiMetaInformationByCorporationNameSuccessTest2() {
        ApiMetaInformation emptyApiMetaInformation = apiMetaInformationRepository.findApiMetaInformationByCorporationName(CorporationType.NAVER.getCorporationName())
                .orElse(null);
        Assertions.assertThat(emptyApiMetaInformation).isNull();
    }
}