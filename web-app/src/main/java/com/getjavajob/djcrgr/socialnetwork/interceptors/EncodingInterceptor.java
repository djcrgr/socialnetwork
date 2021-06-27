package com.getjavajob.djcrgr.socialnetwork.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class EncodingInterceptor extends HandlerInterceptorAdapter {

	@Value("utf-8")
	private String encoding;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding(encoding);
		return true;
	}
}
