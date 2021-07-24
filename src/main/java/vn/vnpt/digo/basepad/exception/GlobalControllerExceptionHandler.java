/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.exception;

import vn.vnpt.digo.basepad.dto.DigoHttpExceptionDto;
import vn.vnpt.digo.basepad.util.Translator;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author DELL
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public static int ENITY_NOT_FOUND_EXCEPTION_CODE = 10048;

    @Autowired
    private Translator translator;

    protected Logger logger;

    public GlobalControllerExceptionHandler() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @ExceptionHandler(DigoHttpException.class)
    @ResponseBody
    public DigoHttpExceptionDto handleDigoHttpException(HttpServletResponse response, DigoHttpException e) {
        response.setStatus(e.getStatusCode());
        e.getDigoHttpExceptionDto().bindMessageSource(translator, e.getArguments()).getMessage();
        logger.warn(e.getDigoHttpExceptionDto().toString());
        return e.getDigoHttpExceptionDto();
    }

}
