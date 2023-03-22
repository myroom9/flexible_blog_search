package com.whahn.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_EXCEPTION(500, "50000000", "서비스 내부에서 알 수 없는 에러가 발생하였습니다."),
    NOT_SUPPORT_API(405, "40500000", "지원하지않는 API입니다."),
    NOT_SUPPORT_API_CORPORATION(400, "40000000", "API를 지원하지 않는 회사입니다."),
    NOT_SUPPORT_SERVICE_CORPORATION(400, "40000001", "블로그 조회 서비스를 지원하지 않는 회사입니다."),

    FEIGN_CLIENT_ERROR(500, "50000001", "API 통신 에러 발생 사유: %s"),

    NOT_CORRECT_VALUE(400, "40000000", "올바르지 않은 값입니다."),

    ;

    private final int status;
    private final String message;
    private final String code;

    ErrorCode(int status, String code, String message) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
