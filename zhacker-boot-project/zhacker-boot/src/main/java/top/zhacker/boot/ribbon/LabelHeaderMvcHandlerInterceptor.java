package top.zhacker.boot.ribbon;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 如果使用了拦截器 把标签放在HystrixRequestVariableDefault中
 *
 */
public class LabelHeaderMvcHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LabelHeaderMvcHandlerInterceptor.class);

	public static final String HEADER_LABEL = "x-label";
	public static final String HEADER_LABEL_SPLIT = ",";

	public static final HystrixRequestVariableDefault<List<String>> label = new HystrixRequestVariableDefault<>();

	public static void initHystrixRequestContext(String labels) {
		logger.info("label: " + labels);
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}

		if (!StringUtils.isEmpty(labels)) {
			LabelHeaderMvcHandlerInterceptor.label.set(Arrays
					.asList(labels.split(LabelHeaderMvcHandlerInterceptor.HEADER_LABEL_SPLIT)));
		}
		else {
			LabelHeaderMvcHandlerInterceptor.label.set(Collections.emptyList());
		}
	}

	public static void shutdownHystrixRequestContext() {
		if (HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		LabelHeaderMvcHandlerInterceptor.initHystrixRequestContext(
				request.getHeader(LabelHeaderMvcHandlerInterceptor.HEADER_LABEL));
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		LabelHeaderMvcHandlerInterceptor.shutdownHystrixRequestContext();
	}
}
