package com.getjavajob.djcrgr.socialnetwork.util;

import com.getjavajob.djcrgr.socialnetwork.interceptors.AccountIdentityInterceptor;
import com.getjavajob.djcrgr.socialnetwork.interceptors.AuthorizeInterceptor;
import com.getjavajob.djcrgr.socialnetwork.interceptors.EncodingInterceptor;
import com.getjavajob.training.karpovn.socialnetwork.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.getjavajob.djcrgr.socialnetwork")
@ComponentScan(basePackages = {"com.getjavajob.training.karpovn.socialnetwork.common"})
@Import(ConfigService.class)
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AuthorizeInterceptor authorizeInterceptor;
	@Autowired
	private AccountIdentityInterceptor accountIdentityInterceptor;
	@Autowired
	private EncodingInterceptor encodingInterceptor;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/home").setViewName("home");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizeInterceptor).addPathPatterns("/home", "/");
		registry.addInterceptor(accountIdentityInterceptor).addPathPatterns("/home");
		registry.addInterceptor(encodingInterceptor).addPathPatterns("/*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resourses/css/**").addResourceLocations("/resourses/css/");
		registry.addResourceHandler("/resourses/images/**").addResourceLocations("/resourses/images/");
		registry.addResourceHandler("/resourses/js/**").addResourceLocations("/resourses/js/");
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10500000);
		return multipartResolver;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/look/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}
