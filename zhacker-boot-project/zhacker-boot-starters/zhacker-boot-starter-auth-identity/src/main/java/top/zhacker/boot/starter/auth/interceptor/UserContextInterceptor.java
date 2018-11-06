package top.zhacker.boot.starter.auth.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.zhacker.boot.starter.auth.util.HttpConvertUtil;
import top.zhacker.core.request.Operator;
import top.zhacker.core.request.UserContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserContextInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(UserContextInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse respone, Object arg2) throws Exception {
//		Operator user = new Operator(HttpConvertUtil.httpRequestToMap(request));
//		if(StringUtils.isEmpty(user.getId()) && StringUtils.isEmpty(user.getName())) {
//			log.error("the user is null, please access from gateway or check user info");
//			return false;
//		}
//		UserContextHolder.set(user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse respone, Object arg2, ModelAndView arg3)
			throws Exception {
		// DOING NOTHING
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse respone, Object arg2, Exception arg3)
			throws Exception {
		UserContextHolder.remove();
	}
	
	
	
}
