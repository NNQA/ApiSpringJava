package com.example.springproject.security.service.Iml;

import com.example.springproject.DTO.OrderItemDTO;
import com.example.springproject.Repository.OrderItemRepository;
import com.example.springproject.Repository.OrderRepository;
import com.example.springproject.Repository.ProductRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.Oder;
import com.example.springproject.models.OrderItem;
import com.example.springproject.models.Product;
import com.example.springproject.models.User;
import com.example.springproject.payload.Request.OrderItemRequest;
import com.example.springproject.payload.Response.OrderDetails;
import com.example.springproject.security.service.Interface.IOrderService;
import org.cache2k.CacheManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private CacheManager cacheManager;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addOrder(Long productId, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot found user or session login fail")));
        Optional<List<Oder>> oder = Optional.empty();
        if (user.isPresent()) {
            oder = orderRepository.findAllByUser(user.get());
        }

        if(oder.isPresent() && oder.get().isEmpty()) {
            Optional<Product> product = productRepository.findById(productId);
            OrderItem orderItem = new OrderItem();
            product.ifPresent(orderItem::setProduct);

            Oder order = new Oder().addItem(orderItem).addUser(user.get());

            orderItem.setOrder(order);
            orderItem.setQuantity(Long.valueOf(0));
            orderRepository.save(order);
        } else {
            if (!Objects.requireNonNull(oder.get().stream().reduce((first, second) -> second)
                    .orElse(null)).isCheckout()) {
                Optional<Oder> oder1 = oder.get().stream().reduce((f, s) -> s);
                OrderItem orderItem = new OrderItem();
                Optional<Product> product = productRepository.findById(productId);
                product.ifPresent(orderItem::setProduct);

                oder1.get().getOrderItems().add(orderItem);
                orderItem.setOrder(oder1.get());
                orderRepository.save(oder1.get());
            } else {
                Optional<Product> product = productRepository.findById(productId);
                OrderItem orderItem = new OrderItem();
                product.ifPresent(orderItem::setProduct);

                Oder order = new Oder().addItem(orderItem).addUser(user.get());

                orderItem.setOrder(order);
                orderItem.setQuantity(Long.valueOf(0));
                orderRepository.save(order);
            }
        }
    }

    @Override
    @Cacheable(cacheNames = "orderCaching", key = "#userId")
    public OrderDetails getOrderDetails(Long userId) {
        System.out.println("Cache hit for userId: " + userId);
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot found user or session login fail")));
        Optional<List<Oder>> order = Optional.empty();
        if(user.isPresent()) {
            order = orderRepository.findAllByUser(user.get());

        }
        Optional<Oder> oder1 = order.get().stream().reduce((f, s) -> s);
        if(!oder1.get().isCheckout()) {
            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
            oder1.get().getOrderItems().forEach(orderItem -> {
                System.out.println(orderItem.getProduct().getName());
                orderItemDTOS.add(new OrderItemDTO(orderItem.getProduct().getName(), orderItem.getQuantity()));
            });

            return new OrderDetails(oder1.get().getUser().getUsername(), orderItemDTOS);
        }
        return new OrderDetails(null, null);
    }

    @Override
//    @Cacheable(cacheNames = "orderCaching", key = "#userId")
    public void updateOrderItem(Long oderId,Long userId, OrderItemRequest orderItemRequest) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot found user or session login fail")));
        Optional<Oder> oder = orderRepository.findById(oderId);
        if(oder.isPresent()) {
            oder.get().getOrderItems().stream().filter(orderItem -> orderItem.getProduct().getId() == orderItemRequest.getProductId())
                    .findFirst()
                    .ifPresentOrElse(
                            orderItem -> {
                                orderItem.setQuantity(orderItemRequest.getQuantity());
                            },
                            () -> new RuntimeException("Cannot found item in order")
                    );
            orderRepository.save(oder.get());
        }

    }

    @Override
    @Cacheable(cacheNames = "orderCaching", key = "#userId")
    public void checkout(Long orderId, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot found user or session login fail")));
        Optional<Oder> oder = orderRepository.findById(orderId);
        oder.ifPresent(oder1 -> oder1.setCheckout(true));
        orderRepository.save(oder.get());
    }

    @Override
    @CacheEvict(cacheNames = "orderCaching", key = "#userId")
    public void deleteItem(Long oderId, Long itemId, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cannot found user or session login fail")));
        Optional<Oder> oder = orderRepository.findById(oderId);

        oder.ifPresent(value -> value.getOrderItems()
                .stream()
                .filter(
                        orderItem -> orderItem.getId() == itemId
                ).findFirst().ifPresentOrElse(
                orderItem -> {
                    orderItemRepository.delete(orderItem);
                    value.getOrderItems().remove(orderItem);
                },
                () -> new RuntimeException("Cannot delete OrderItem")
        ));
        orderRepository.save(oder.get());
    }
}
