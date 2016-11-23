package com.fymod.ftf.spring;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView model = new ModelAndView();
		String errorView = null;
		model.setViewName(errorView);
		if (request.getHeader("accept")!=null && !(request.getHeader("accept").indexOf("application/json") > -1 || (request  
                .getHeader("X-Requested-With")!= null && request  
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			try {
				response.sendRedirect(request.getContextPath()+"/"+errorView);
			} catch (IOException e) {
				e.printStackTrace();
			}
            return model;
        } else {// JSON格式返回  
            try {  
                PrintWriter writer = response.getWriter();
                writer.write("数据加载失败");  
                writer.flush();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            return null;
        }
	}

}
