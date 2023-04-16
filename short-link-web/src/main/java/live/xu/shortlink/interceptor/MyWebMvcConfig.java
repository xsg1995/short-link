package live.xu.shortlink.interceptor;

import live.xu.shortlink.controller.ViewController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        return false;
                    }
                })
                .addPathPatterns("/**.*")  //过滤静态资源访问controller接口
                .excludePathPatterns("/**.htm"); //允许访问html页面

        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        //.htm后缀只能访问 ViewController
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        return handlerMethod.getBean() instanceof ViewController;
                    }
                })
                .addPathPatterns("/**.htm");
    }
}