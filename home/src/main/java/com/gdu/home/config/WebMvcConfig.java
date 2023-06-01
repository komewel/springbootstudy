package com.gdu.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.home.intercept.LoginCheckInterceptor;
import com.gdu.home.util.MyFileUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	//field
	private final LoginCheckInterceptor loginCheckInterceptor;
	private final MyFileUtil myFileUtil;

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor) // 인터셉터 태그 등록 (자바로 등록)
		  .addPathPatterns("/bbs/write.html", "/upload/write.html")
		  .addPathPatterns("/user/logout.do");
		registry.addInterceptor(loginCheckInterceptor)
		.addPathPatterns("/**") 				// 모든요청, exclude를 쓸때 이요청을 제외하고 전부 다 라고 할때 흔히쓰는 패턴(모든 요청을 받는데 몇개만 제외할때, 적용할 패턴이 너무 많을때 몇개만 제외할때)
		.excludePathPatterns("/user/leave.do"); // 제외할 요청
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/imageLoad/**") // 리소스 태그 등록 (자바로 등록)
		.addResourceLocations("file:" + myFileUtil.getSummernoteImagePath() + "/");
	}
	
	// 기능을 필드로 가져와서 자바코드로 bena화 시키는것같음 
}
