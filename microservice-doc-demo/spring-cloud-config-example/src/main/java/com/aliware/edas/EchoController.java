package com.aliware.edas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author edas
 */
@RestController
@RefreshScope
public class EchoController {

	@Value("${test.name}")
	private String userName;

	@RequestMapping(value = "/")
	public String echo() {
		return userName;
	}
}
