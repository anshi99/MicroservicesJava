package com.nagarro.microservices.providerService.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nagarro.microservices.providerService.model.Provider;
import com.nagarro.microservices.providerService.model.ServiceInfo;
import com.nagarro.microservices.providerService.service.ProviderService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
@RequestMapping("/service")
public class ProviderController {
	@Autowired
	EurekaClient eurekaClient;
	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;
	@Resource
	ProviderService providerService;
	@GetMapping("/test")
	String testProvider() {
		return "Welcome to Provider Service";
	}
	@GetMapping("/getService/{id}")
	ServiceInfo getServiceDetails(@PathVariable(name = "id") String id) {
		return providerService.getServiceDetails(id);
	}
	@GetMapping(value = "/getProvider/{id}")
	Provider getProviderDetails(@PathVariable(name = "id") String id) {
		return providerService.getProviderDetails(id);
	}
	@GetMapping("/getAllServices")
	Map<String, String> findAllServices() {
		return providerService.getAllServices();
	}
	@PostMapping("/addProvider")
	public Provider adduser(@RequestBody Provider provider) {
		return providerService.addProvider(provider);
	}
	@PostMapping("/registerService")
	public ServiceInfo adduser(@RequestBody ServiceInfo service) {
		return providerService.registerService(service);
	}
		@GetMapping(value = "/price/{id}")
		double getPriceForService(@PathVariable(name = "id") String id) {
			ServiceInfo service = providerService.getServiceDetails(id);
			String url = "/price/get/" + service.getId();
			InstanceInfo instance = eurekaClient.getNextServerFromEureka("price", false);
			double price = restTemplate.getForObject(instance.getHomePageUrl() + url, double.class);
			return price;
		}

}
