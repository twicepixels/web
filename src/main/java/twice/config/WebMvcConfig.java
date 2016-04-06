package twice.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;
import twice.config.Interceptor.TestInterceptor;

import java.util.Locale;
//import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
//import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
//import org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView;

@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new TestInterceptor());
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es"));
        localeResolver.setCookieName("locale");
        return localeResolver;
    }

    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("/WEB-INF/i18n/messages");
        messageSource.setBasenames(
                "/WEB-INF/i18n/images",
                "/WEB-INF/i18n/messages");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean(name = "templateResolver")
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean(name = "urlTemplateResolver")
    public UrlTemplateResolver urlTemplateResolver() {
        return new UrlTemplateResolver();
    }

    @Bean(name = "templateEngine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(templateResolver());
        templateEngine.addTemplateResolver(urlTemplateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new LayoutDialect());
//        templateEngine.addDialect(new TilesDialect());
        return templateEngine;
    }

    /**
     * Handles all views except for the ones that are handled by Tiles.
     * This view resolver will be executed as first one by Spring.
     */
    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        vr.setCharacterEncoding("UTF-8");
        vr.setOrder(Ordered.HIGHEST_PRECEDENCE);
        // all message/* views will not be handled
        // by this resolver as they are Tiles views
        vr.setExcludedViewNames(new String[]{"message/*"});
        return vr;
    }

    /**
     * Handles Tiles views.
     */
//    @Bean
//    public ViewResolver tilesViewResolver() {
//        ThymeleafViewResolver vr = new ThymeleafViewResolver();
//        vr.setTemplateEngine(templateEngine());
//        vr.setViewClass(ThymeleafTilesView.class);
//        vr.setCharacterEncoding("UTF-8");
//        vr.setOrder(Ordered.LOWEST_PRECEDENCE);
//        return vr;
//    }

//    @Bean
//    public ThymeleafTilesConfigurer tilesConfigurer() {
//        ThymeleafTilesConfigurer ttc = new ThymeleafTilesConfigurer();
//        ttc.setDefinitions(new String[]{"/WEB-INF/views/message/tiles-defs.xml"});
//        return ttc;
//    }
}
