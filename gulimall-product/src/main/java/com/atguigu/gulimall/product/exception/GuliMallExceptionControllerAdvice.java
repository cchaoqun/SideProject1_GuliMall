package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chaoqun Cheng
 * @date 2021-06-2021/6/17-0:33
 */

/**
 * 几种处理所有异常
 */

//@ResponseBody
//@ControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")

@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller" )
public class GuliMallExceptionControllerAdvice {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("数据校验出现异常{}, 异常类型:{}", e.getMessage(), e.getClass());
        Map<String, String> errorMap = new HashMap<>();
        BindingResult bindingResult =  e.getBindingResult();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VALID_EXCEPTION.getCode(), BizCodeEnume.VALID_EXCEPTION.getMsg() ).put("data" ,errorMap);
    }

    @ExceptionHandler(value=Throwable.class)
    public R handleException(Throwable throwable){
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
