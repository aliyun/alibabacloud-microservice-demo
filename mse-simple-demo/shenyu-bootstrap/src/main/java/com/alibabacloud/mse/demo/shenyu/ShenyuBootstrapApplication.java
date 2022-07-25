package com.alibabacloud.mse.demo.shenyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShenyuBootstrapApplication {

	/**
	 * Main Entrance.
	 *
	 * @param args startup arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(ShenyuBootstrapApplication.class, args);
	}
}
