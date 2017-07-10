package cn.edu.nju.cs.seg.interceptor;

import cn.edu.nju.cs.seg.controller.OtherController;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fwz on 2017/6/3.
 */
public class ApiInterceptor implements HandlerInterceptor {
    private static Logger log = Logger.getLogger(OtherController.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String uri = request.getMethod() + " " + request.getRequestURI();
//        System.out.println(uri);
//        InputStream is = request.getInputStream();
////        request.getPart("descrpition");
//        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf8"));
//        String str = br.readLine();
//        while (str != null) {
//            System.out.println(str);
//            str = br.readLine();
//        }
//        br.close();
//        is.close();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String uri = request.getMethod() + " " + request.getRequestURI();
//        log.info(request.getMethod() + " " + request.getRequestURI());
        System.out.println(uri + " " + response.getStatus());
    }
}
