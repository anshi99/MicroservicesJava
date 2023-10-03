package com.nagarro.microservices.orderService.controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.microservices.orderService.model.Order;
import com.nagarro.microservices.orderService.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	
	@Resource
	OrderService orderService;
	
	@GetMapping("/{orderId}")
	Order getOrderDetails(@PathVariable(name = "orderId") String id) {
		return orderService.getOrderDetails(id);
	}
	@PostMapping("/createOrder")
	public Order creatOrder(@RequestBody Order order) {
		return orderService.createOrder(order);	
	}
	@GetMapping("/doPayment/{orderId}")
	String doPayment(@PathVariable(name = "orderId") String orderId) {
		String paymentStatus = orderService.doPayment(orderId);
		return paymentStatus;
	}
}
