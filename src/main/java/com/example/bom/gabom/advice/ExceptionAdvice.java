package com.example.bom.gabom.advice;

import com.example.bom.gabom.advice.exception.*;
import com.example.bom.gabom.model.response.CommonResult;
import com.example.bom.gabom.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice // 예외 발생 시 json 형태로 결과 반환 | 프로젝트의 모든 Controller에 로직 적용
// @RestControllerAdvice(basePackages = "com.example.pepega") : pepega 하위의 Controller 에만 로직 적용
public class ExceptionAdvice {

    private final ResponseService responseService; // 결과에 대한 정보를 도출하는 클래스 명시
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // CommonResult : 응답 결과에 대한 정보
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
        // 예외 처리 메시지를 MessageSource에서 가져오도록 수정, exception_ko.yml 파일에서 가져온 것임
        // getFailResult : setSuccess, setCode, setMsg
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        // CommonResult : 응답 결과에 대한 정보
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
        // 예외 처리 메시지를 MessageSource에서 가져오도록 수정
        // getFailResult : setSuccess, setCode, setMsg
    }

    @ExceptionHandler(CEmailAuthTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailAuthTokenNotFound(HttpServletRequest request, CEmailAuthTokenNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailAuthTokenNotFound.code")), getMessage("emailAuthTokenNotFound.msg"));
    }

    @ExceptionHandler(CNickNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult nickNameAlreadyExistsException(HttpServletRequest request, CNickNameAlreadyExistsException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("nickNameAlreadyExists.code")), getMessage("nickNameFailedAlreadyExists.msg"));
    }

    @ExceptionHandler(CUserIdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userIdAlreadyExistsException(HttpServletRequest request, CUserIdAlreadyExistsException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userIdAlreadyExists.code")), getMessage("userIdAlreadyExists.msg"));
    }

    @ExceptionHandler(CUserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userAlreadyExists(HttpServletRequest request, CUserAlreadyExistsException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userAlreadyExists.code")), getMessage("userAlreadyExists.msg"));
    }

    @ExceptionHandler(CEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult((Integer.valueOf(getMessage("accessDenied.code"))), getMessage("accessDenied.msg"));
    }

    // code 정보에 해당하는 메시지를 조회한다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code 정보, 추가 argument로 현재 locale에 맞는 메시지를 조회한다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}