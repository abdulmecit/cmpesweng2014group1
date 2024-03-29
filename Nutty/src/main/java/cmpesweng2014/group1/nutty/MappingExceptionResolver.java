package cmpesweng2014.group1.nutty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class MappingExceptionResolver extends SimpleMappingExceptionResolver {
    public MappingExceptionResolver() {
        // Enable logging by providing the name of the logger to use
        setWarnLogCategory(MappingExceptionResolver.class.getName());
    }

    @Override
    public String buildLogMessage(Exception e, HttpServletRequest req) {
        return "MVC exception: " + e.getLocalizedMessage();
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception exception) {
        // Call super method to get the ModelAndView
        ModelAndView mav = super.doResolveException(request, response, handler, exception);

        // Make the full URL available to the view - note ModelAndView uses addObject()
        // but Model uses addAttribute(). They work the same. 
        mav.addObject("url", request.getRequestURL());
        return mav;
    }
}


