package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.example.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrderServiceClient {
    private static final String ORDER_SERVICE_NAME = "order-service";
    private static final String ORDERS_ENDPOINT = "/orders/user/";
    private static final String USER_ID_PARAM = "/{id}";

    private final DiscoveryClient discoveryClient;

    //@Autowired
    private RestTemplate restTemplate;

    @Autowired
    public OrderServiceClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = new RestTemplate();
    }


    public List<OrderDTO> getOrderByUser(Long userID){
        ServiceInstance orderInstance = getServiceInstance();
        String orderUrl = orderInstance.getUri().toString() + ORDERS_ENDPOINT + userID;// + USER_ID_PARAM;
        System.out.println(orderUrl);
        //ResponseEntity<OrderDTO> response = restTemplate.getForEntity(orderUrl, OrderDTO.class, userID);
        ResponseEntity<String> response = restTemplate.exchange( //List<OrderDTO>
                orderUrl,
                HttpMethod.GET,
                null,
                String.class //new ParameterizedTypeReference<List<OrderDTO>>() {}
        );
        String responseBody = response.getBody();
        System.out.println(responseBody);
        List<OrderDTO> orderDTOList = convertResponseToOrderDTOList(responseBody);

        //List<OrderDTO> orders = response.getBody();

        // Convert the List<Order> to List<OrderDTO> using custom conversion logic
        //List<OrderDTO> orderDTOList = convertToOrderDTOList(orders);

        return orderDTOList;
        //return response.getBody();
    }

    private List<OrderDTO> convertResponseToOrderDTOList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(responseBody, new TypeReference<List<OrderDTO>>() {});
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());// Handle the exception accordingly
        }

        return List.of();
    }

    private ServiceInstance getServiceInstance() {
        return discoveryClient.getInstances(ORDER_SERVICE_NAME)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order Service not found"));
    }
}
