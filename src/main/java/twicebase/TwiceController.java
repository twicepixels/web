package twicebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public abstract class TwiceController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    protected String getMessage(String code) {
        return getMessage(code, null);
    }

    protected String getMessage(String code, Object[] args) {
        Locale loc = localeResolver.resolveLocale(getRequest());
        return messageSource.getMessage(code, args, "", loc);
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
    }

    protected boolean isAjaxRequest() {
        HttpServletRequest request = getRequest();
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }

    protected AjaxErrors getErrors(BindingResult result) {
        AjaxErrors errors = new AjaxErrors();
        result.getAllErrors().stream()
                .filter(e -> e instanceof FieldError)
                .forEach(e -> {
                    FieldError fieldError = (FieldError) e;
                    Locale loc = localeResolver.resolveLocale(getRequest());
                    String message = messageSource.getMessage(fieldError, loc);
                    errors.add(fieldError.getField(), message);
                });
        return errors;
    }
}
