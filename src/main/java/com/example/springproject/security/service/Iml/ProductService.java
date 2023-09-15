package com.example.springproject.security.service.Iml;

import com.example.springproject.DTO.ProductDto;
import com.example.springproject.Repository.CategoryRepostitory;
import com.example.springproject.Repository.ProductRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.*;
import com.example.springproject.payload.Request.ProductRequest;
import com.example.springproject.payload.Response.ProductPageResponse;
import com.example.springproject.security.service.Interface.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {


    private final CategoryRepostitory categoryRepostitory;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(CategoryRepostitory categoryRepostitory, ProductRepository productRepository, UserRepository userRepository) {
        this.categoryRepostitory = categoryRepostitory;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(), product.getName(),product.getDescription(),product.getPrice(),product.getCategory()
        );
    }

    @Override
    public Product save(ProductRequest productRequest, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User dont exist or session error"));
        System.out.println(user.getUsername());
        if(productRequest.getNameCate() == null || Objects.equals(productRequest.getNameCate(), "")) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        for (Product product: user.getProducts()) {
            if (product.getName().contains(productRequest.getName())) {
                throw new IllegalArgumentException("Name product have been exist, please choose another name");
            }
        }
        Product product = new Product(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice());
        product.setApprove(user.getRoles().toString().contains("ROLE_ADMIN"));
        Category category = categoryRepostitory.findByName(productRequest.getNameCate())
                    .orElseThrow(()-> new RuntimeException("Cant found Category"));
        product.setCategory(category);
        product.setUser(user);
        user.getProducts().add(product);
        userRepository.save(user);
        return product;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getByid(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product
               .orElseThrow(() -> new RuntimeException("Product not found with Id: " + productId));
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest) {
//        Optional<Product> oddproduct = productRepository.findById(productId);
//        if(oddproduct.isPresent()) {
//            try {
//                Product product = oddproduct.get();
//                product.setName(productRequest.getName());
//                product.setDescription(productRequest.getDescription());
//                product.setPrice(productRequest.getPrice());
//                if(!Objects.equals(product.getCategory().getName(), productRequest.getNameCate())) {
//                    product.getCategory().getProduct().remove(product);
//                    categoryRepostitory
//                            .save(product.getCategory());
//                    Category category = categoryRepostitory.findByName(productRequest.getNameCate())
//                            .orElseThrow(()-> new RuntimeException("Cant found Category"));
//                    product.setCategory(category);
//                }
//                return productRepository.save(product);
//            }
//            catch (RuntimeException e) {
//                throw new RuntimeException("An error occurred while saving the product");
//            }
//        }
//        else {
//            throw new RuntimeException("Product not found with Id: " + productId);
//        }
        return  null;
    }

    @Override
    public void deteleProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            try {
                productRepository.delete(product.get());
            }
            catch (RuntimeException e) {
                throw new RuntimeException("An error occurred while deleting the product");
            }
        }
        else {
            throw new RuntimeException("Product not found with Id: " + productId);
        }
    }
    @Override
    public void deleteAllProduct(Long id) {
        Optional<User> userOp = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User dont exist or session error")));
        User user;
        if(userOp.isPresent()) {
            user = userOp.get();
            user.getProducts().removeAll(user.getProducts());
            userRepository.save(user);
        }
    }

    @Override
    public List<Product> filterProducts(String cateName) {
        Optional<Category> category = Optional.ofNullable(categoryRepostitory.findByName(cateName)
                .orElseThrow(() -> new RuntimeException("can not found cate")));
        return productRepository.findAllByCategoryOrderByPriceDesc(category.get());
    }

    @Override
    public Product ApproveProduct(Long id) {
        Optional<Product> productOp = Optional.ofNullable(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Cant found product to approved")));

        Product product = productOp.get();
        product.setApprove(true);

        return productRepository.save(product);
    }

    @Override
    public String RejectProduct(Long userId, Long productId) {
        Optional<Product>  productOptional = Optional.ofNullable(
                productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("SomeThings are wrong with session and request"))
        );
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User dont exist or session error"));
        productOptional.ifPresent(product -> user.getProducts().remove(product));
        productOptional.ifPresent(productRepository::delete);
        userRepository.save(user);
        return "Succesfully";
    }

    @Override
    public ProductPageResponse getALlProductPage(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

        Page<Product> products = productRepository.findAll(pageable);
        List<Product> content = products.getContent();

        List<ProductDto> productDtos =
                content.stream().map(
                        this::mapToProductDto
                ).collect(Collectors.toList());

        return new ProductPageResponse (
                productDtos,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast()
        ) ;
    }

}
