package by.academy.rentApp.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(AppException.class)
    public String applicationError(AppException e, Model model) {
        model.addAttribute("error",e);
        return "testException";
    }

    @ExceptionHandler(IOException.class)
    public String inputOutputError(Exception  e, Model model) {
        model.addAttribute("error",e);
        return "testException";
    }

}
