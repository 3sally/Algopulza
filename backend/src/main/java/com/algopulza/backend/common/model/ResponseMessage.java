package com.algopulza.backend.common.model;

public class ResponseMessage {

    private ResponseMessage() {
    }

    // Common
    public static final String BAD_REQUEST = "유효하지 않은 요청";
    public static final String DUPLICATE_INPUT_VALUE = "중복된 입력값";
    public static final String NOT_FOUND = "유효하지 않은 리소스 요청";

    // Auth
    public static final String INVALID_REFRESH_TOKEN = "유효하지 않은 리프레시 토큰";
    public static final String UNAUTHORIZED = "인증되지 않은 회원";
    public static final String ACCESS_DENIED = "접근권한 없음";
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String LOGOUT_FAIL = "로그아웃 실패";

    // Member
    public static final String NOT_FOUND_MEMBER = "존재하지 않는 회원";
    public static final String GET_MEMBER_INFO_SUCCESS = "회원 정보 조회 성공";
    public static final String GET_MEMBER_INFO_FAIL = "회원 정보 조회 실패";
    public static final String MODIFY_MEMBER_INFO_SUCCESS = "회원 정보 수정 성공";
    public static final String MODIFY_MEMBER_INFO_FAIL = "회원 정보 수정 실패";

    // Problem
    public static final String GET_PROBLEM_LIST_SUCCESS = "문제 목록 조회 성공";

}