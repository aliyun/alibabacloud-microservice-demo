package com.aliware.edas;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author edas
 */
@RestController
public class EchoController {
	@RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
	public String echo(@PathVariable String string) {
		try {

			TimeUnit.SECONDS.sleep(60);
		}
		catch (Exception e) {

		}
		return string;
	}
}