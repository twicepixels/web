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
    protected MessageSource messageSource;

    @Autowired
    protected LocaleResolver localeResolver;

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
        for (Object object : result.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                Locale loc = localeResolver.resolveLocale(getRequest());
                String message = messageSource.getMessage(fieldError, loc);
                errors.add(fieldError.getField(), message);
// logger.info("ErrorMessage : " +
// message + " - "  + fieldError.getField());
            }
        }
        return errors;
    }
}
