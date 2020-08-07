package com.opandares.user.app;

import com.opandares.user.domain.exception.InvalidEmailException;
import com.opandares.user.domain.exception.InvalidFormatException;
import com.opandares.user.domain.exception.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionAdvice {

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<Map<String,String>> handlerUserExistException(UserExistException userExistException){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","El correo ya registrado");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public  ResponseEntity<Map<String,String>> handlerInvalidEmailException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Formato email incorrecto");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    public ResponseEntity<Map<String,String>> handlerInvalidFormatException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Formato password incorrecto");
        return new ResponseEntity<>(map,HttpStatus.BAD_GATEWAY);
    }

}
