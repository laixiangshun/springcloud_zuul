package com.lai.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lailai on 2017/9/26.
 * 自定义服务过滤
 * 服务网关中定义过滤器只需要继承ZuulFilter抽象类实现其定义的四个抽象函数就可对请求进行拦截与过滤
 */
public class AccessFilter extends ZuulFilter{
    private static Logger logger= LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 过滤器类型：四种不同生命周期的过滤器类型
     * pre：可以在请求被路由之前调用
     * routing：在路由请求时候被调用
     * post：在routing和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 通过int值来定义过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * @return
     */
    @Override
    public Object run() {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request=context.getRequest();
        logger.info(String.format("%s request to %s",request.getMethod(),request.getRequestURL().toString()));
        Object accessToken=request.getParameter("accessToken");
        if(accessToken==null){
            logger.warn("access token is empty");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
//            String text="不符合访问条件";
//            String body= null;
//            try {
//                body = URLEncoder.encode(new String(text.getBytes("UTF-8")), "UTF-8");
//                logger.info(String.format("body is %s",body));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            HttpServletResponse response=context.getResponse();
            try {
                //response.setHeader("Content-type","text/html;charset=UTF-8");
                //告诉servlet用UTF-8转码，而不是用默认的ISO8859
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("不符合访问条件");
            } catch (IOException e) {
                e.printStackTrace();
            }
            // context.setResponseBody(body);
            return null;
        }
        logger.info("access token ok");
        return null;
    }
}
