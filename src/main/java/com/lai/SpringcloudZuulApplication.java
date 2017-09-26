package com.lai;

import com.lai.Filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @SpringCloudApplication注解, 整合了@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker
 */
@SpringBootApplication
@EnableZuulProxy   //开启Zuul
@EnableDiscoveryClient  //开启发现服务
@EnableCircuitBreaker  //开启熔断器
public class SpringcloudZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudZuulApplication.class, args);
	}

	/**
	 * 在实现了自定义过滤器之后，还需要实例化该过滤器才能生效
	 * @return
	 */
	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}
}
