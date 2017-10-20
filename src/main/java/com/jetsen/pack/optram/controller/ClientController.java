package com.jetsen.pack.optram.controller;

import com.jetsen.pack.optram.bean.Greeting;
import com.jetsen.pack.optram.mq.RabbitConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lenovo on 2017/10/17.
 */
@RestController
public class ClientController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/")
    @ResponseBody
    String home() {
        String result = "hello world";

		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("key", "first.last.com");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<?> response  =  restTemplate.postForEntity("http://localhost:8080/redis/getForValue",request,String.class);*/

        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8080/redis/getForValue",String.class,"hello");
        String entityResponse = (String) response.getBody();
        return entityResponse;
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    Greeting sayHello(String name){
        String template = "Hello, %s!";
        return new Greeting(new AtomicLong().incrementAndGet(),String.format(template,name));
    }

    @RequestMapping(value = "/mq",method = RequestMethod.GET)
    @ResponseBody
    String sayHelloMQ(String msg){
        System.out.println("Sending message ...");
        rabbitTemplate.convertAndSend("change-db-exchange","ok.change-db","change db to master error!");
        rabbitTemplate.convertAndSend("change-db-exchange","error.change-db","change db to master error!");
        return "ok";
    }

    @RequestMapping(value = "/mq/rev",method = RequestMethod.GET)
    @ResponseBody
    String sayHoMQ(String msg){
        System.out.println("Receiving message ...");
        String re = (String)rabbitTemplate.receiveAndConvert("change-db");
        System.out.println(re);
        return "ok";
    }


}
