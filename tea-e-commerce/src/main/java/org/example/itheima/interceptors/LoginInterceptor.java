package org.example.itheima.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.core.ApplicationContext;
import org.example.itheima.pojo.Result;
import org.example.itheima.utils.JwtUtil;
import org.example.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从redis中获取相同的token

            //因为拦截器是一个特殊的组件，它并不直接由Spring容器管理。因此，直接在拦截器中使用@Autowired进行依赖注入可能会导致注入失败
            //这里我们使用WebApplicationContextUtils来获取WebApplicationContext，然后通过注入
            if (stringRedisTemplate == null) {
                WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                stringRedisTemplate = context.getBean(StringRedisTemplate.class);
            }

            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String RedisToken = operations.get(token);
            if (RedisToken == null){
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);

            //把业务数据存储到Thread中
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            //http响应码为401
            e.printStackTrace();
            response.setStatus(401);
            return false;
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清理ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
