package com.example.order.service;

import com.example.inventory.dto.InventoryDTO;
import com.example.order.common.ErrorOrderResponse;
import com.example.order.common.OrderResponse;
import com.example.order.common.SuccessOrderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.model.Orders;
import com.example.order.repo.OrderRepo;

import com.example.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;


    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired

    public OrderService(WebClient inventoryWebClient,WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse saveOrder(OrderDTO OrderDTO) {

        Integer itemId = OrderDTO.getItemId();

        try{
            InventoryDTO inventoryResponse=inventoryWebClient.get()
                    .uri(uriBuilder->uriBuilder.path("/item/{itemid}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

            Integer productId = inventoryResponse.getProductId();

            ProductDTO productResponse=productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            assert productResponse != null;

            if (inventoryResponse.getQuantity() > 0){
                if (productResponse.getForSale() == 1) {
                    orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
                }
                else{
                    return new ErrorOrderResponse("Sorry, This item is not for sale.");
                }
                return new SuccessOrderResponse(OrderDTO);
            }
            else{
                return new ErrorOrderResponse("Sorry, This item is not available, Please try later.");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
        return OrderDTO;
    }

    public String deleteOrder(Integer orderId) {
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }

    public OrderDTO getOrderById(Integer orderId) {
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
