package com.edu.lx.cms.interceptor;

import com.edu.lx.cms.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * JwtInterceptor类实现了HandlerInterceptor接口，用于拦截进入Controller之前的请求，
 * 并验证请求头中的JWT是否有效。
 */
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    /**
     * 使用LoggerFactory创建一个Logger实例，用于记录日志信息。
     */
    //private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    /**
     * preHandle方法在请求处理之前被调用，用于在Controller方法执行之前进行预处理。
     * 如果该方法返回true，则请求继续向下传递到下一个拦截器或Controller；
     * 如果返回false，则请求将被中断，不会继续处理。
     *
     * @param request  HttpServletRequest对象，表示当前请求。该对象包含请求的信息，如请求头、查询参数等。
     * @param response HttpServletResponse对象，表示当前响应。该对象用于构造响应，如设置状态码、响应头等。
     * @param handler  当前请求的处理者，可能是Controller方法或另一个拦截器。这个参数可以用来判断请求是否被其他拦截器处理过。
     * @return boolean值，表示是否继续执行请求。如果返回true，则请求继续向下传递；如果返回false，则请求将被中断。
     * @throws Exception 可能抛出的异常，例如在处理请求或响应时发生的异常。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 从请求头中获取"Authorization"字段，它应该包含Bearer令牌。
         * 这个令牌用于验证用户的身份。
         */
        String token = request.getHeader("Authorization");

        /**
         * 检查token是否为空或不以"Bearer "开头。
         * 如果令牌丢失或无效，设置响应状态为401 Unauthorized，并返回错误信息。
         */
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            /*response.getWriter().write("令牌丢失或无效");*/
            log.error("令牌丢失或无效");
            return false; // 中断请求
        }

        /**
         * 提取令牌字符串，去掉"Bearer "前缀。
         * 这个字符串是JWT的实际内容，需要被验证。
         */
        String authToken = token.substring(7);

        /**
         * 使用JwtUtil验证令牌是否有效。
         * JwtUtil类提供了JWT的生成和验证功能。
         */
        boolean isValid = JwtUtil.verifyToken(authToken);
        if (!isValid) {
            /**
             * 如果令牌无效或已过期，设置响应状态为401 Unauthorized，并返回错误信息。
             */
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            /*response.getWriter().write("令牌无效或已过期");*/
            log.error("令牌无效或已过期");

            return false; // 中断请求
        }

        /**
         * 如果令牌验证成功，返回true以继续执行下一个拦截器或Controller。
         */
        return true;
    }
}