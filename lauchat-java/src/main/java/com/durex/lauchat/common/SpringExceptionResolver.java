package com.durex.lauchat.common;

import com.durex.lauchat.exception.LauChatException;
import com.durex.lauchat.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class SpringExceptionResolver {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object handlerException(HttpServletRequest request, Exception e) {
        JsonData result = null;
        if (e instanceof LauChatException || e instanceof ParamException) {
            result = JsonData.fail(e.getMessage());
        } else {
            log.info("Exception:{}", e);
            result = JsonData.fail("System error");
        }
        return result.toMap();
    }
}