package twicebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public abstract class TwiceController {

    @Autowired
    protected MessageSource msgSource;

    @Autowired
    protected LocaleResolver localeResolver;

    protected String getMessage(String code) {
        return getMessage(code, null);
    }

    protected String getMessage(String code, Object[] args) {
        Locale loc = localeResolver.resolveLocale(getRequest());
        return msgSource.getMessage(code, args, "", loc);
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
    }
}
