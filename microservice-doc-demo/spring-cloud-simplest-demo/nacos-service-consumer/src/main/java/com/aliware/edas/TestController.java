package com.aliware.edas;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.cloud.nacos.ribbon.NacosServerList;

import com.netflix.client.config.DefaultClientConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author edas
 */
@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EchoService echoService;

	@Autowired
	private NacosDiscoveryProperties properties;

	@RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
	public String rest(@PathVariable String str) {
		return restTemplate.getForObject("http://service-provider/echo/" + str,
				String.class);
	}

	@RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
	public String feign(@PathVariable String str) {
		return echoService.echo(str);
	}

	@RequestMapping(value = "/servers", method = RequestMethod.GET)
	public List<NacosServer> servers() {

		DefaultClientConfigImpl iClientConfig = new DefaultClientConfigImpl();
		iClientConfig.setClientName("service-provider");

		NacosServerList serverList = new NacosServerList(properties);
		serverList.initWithNiwsConfig(iClientConfig);

		return serverList.getInitialListOfServers();
	}

}
