package com.imovie.cloud.controller;

import com.imovie.cloud.entity.TUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author soysauce
 */
@RestController
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @HystrixCommand(fallbackMethod = "queryUserByIdFallBack")
    @GetMapping("user/{id}")
    public TUser queryUserById(@PathVariable long id) {
        return restTemplate.getForObject("http://imovie-user/user/" + id, TUser.class);
    }

    public Object queryUserByIdFallBack() {
        return TUser.builder().name("默认断路器返回").id(-1L).build();
    }

    @GetMapping("user/getInfo")
    public List<ServiceInstance> showInfo() {
        return discoveryClient.getInstances("imovie-user");
    }

    @GetMapping("user/getInstanceInfo")
    public String getUserInstance() {
        ServiceInstance instance = loadBalancerClient.choose("imovie-user");
        System.out.println("serviceId:" + instance.getServiceId() + "\nhost:" + instance.getHost() + "\nport" + instance.getPort());
        return "serviceId:" + instance.getServiceId() + "\nhost:" + instance.getHost() + "\nport" + instance.getPort();
    }

    public static void main(String[] args) throws IOException {
        ClassPathResource resource = new ClassPathResource("eureka-rest-api-test.xml");
        InputStream inputStream = resource.getInputStream();
        byte arr[] = new byte[1024];
        int temp = 0;
        StringBuilder sb = new StringBuilder();
        while ((temp = inputStream.read(arr)) != -1) {
            sb.append(new String(arr, 0, temp));
        }
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder=factory.newDocumentBuilder();
//        Document doc = builder.parse(resource.getInputStream());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_XML);
//        header.add("Accept", MediaType.APPLICATION_XML_VALUE);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(username, password);
//        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthorization("root", "root").build();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(sb.toString(), header);
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("root", "root"));
        Object s = restTemplate.postForObject("http://localhost:8671/eureka/apps/rest-api-test", entity, Object.class);
        System.out.println(s);

    }
}
