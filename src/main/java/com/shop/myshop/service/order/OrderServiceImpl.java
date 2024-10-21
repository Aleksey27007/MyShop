package com.shop.myshop.service.order;

import com.shop.myshop.dto.OrderDto;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.model.Bucket;
import com.shop.myshop.model.Order;
import com.shop.myshop.model.OrderProduct;
import com.shop.myshop.model.Product;
import com.shop.myshop.model.enums.OrderStatusEnum;
import com.shop.myshop.repository.OrderRepository;
import com.shop.myshop.repository.ProductRepository;
import com.shop.myshop.service.bucket.BucketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final BucketService bucketService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Bucket bucket = bucketService.getBucketByUserId(userId);
        Order order = createOrder(bucket);
        List<OrderProduct> orderProductList = createOrderProducts(order, bucket);
        order.setOrderProducts(new HashSet<>(orderProductList));
        order.setTotalAmount(calculateTotalAmount(orderProductList));
        Order savedOrder = orderRepository.save(order);
        bucketService.clearBucket(bucket.getId());
        return savedOrder;
    }

    private Order createOrder(Bucket bucket) {
        Order order = new Order();
        order.setUser(bucket.getUser());
        order.setOrderStatus(OrderStatusEnum.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderProduct> createOrderProducts(Order order, Bucket bucket) {
        return bucket.getProducts().stream().map(bucketProduct -> {
            Product product = bucketProduct.getProduct();
            product.setQuantity(product.getQuantity() - bucketProduct.getQuantity());
            productRepository.save(product);
            return new OrderProduct(
                    order,
                    product,
                    bucketProduct.getQuantity(),
                    bucketProduct.getUnitPrice());
        }).toList();

    }

    private BigDecimal calculateTotalAmount(List<OrderProduct> orderProductList) {
        return orderProductList
                .stream()
                .map(product -> product.getPrice()
                        .multiply(new BigDecimal(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
