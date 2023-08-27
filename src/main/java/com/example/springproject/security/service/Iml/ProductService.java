package com.example.springproject.security.service.Iml;

import com.example.springproject.DTO.ProductDto;
import com.example.springproject.Repository.CategoryRepostitory;
import com.example.springproject.Repository.ProductRepository;
import com.example.springproject.models.Category;
import com.example.springproject.models.Product;
import com.example.springproject.payload.Request.ProductRequest;
import com.example.springproject.payload.Response.ProductPageResponse;
import com.example.springproject.security.service.Interface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private CategoryRepostitory categoryRepostitory;
    @Autowired
    private ProductRepository productRepository;

    public ProductService(CategoryRepostitory categoryRepostitory, ProductRepository productRepository) {
        this.categoryRepostitory = categoryRepostitory;
        this.productRepository = productRepository;
    }

    public ProductDto mapToProductDto(Product product) {
//        ProductDto productDto =new ProductDto(
//                product.getId(), product.getName(),product.getDecription(),product.getPrice(),product.getCategory()
//        );
//        ProductDto productDto = new ProductDto();
//        productDto.setId(product.getId());
//        productDto.setCategory(product.getCategory());
//        productDto.setDecription(productDto.getDecription());
//        productDto.setName(product.getName());
//        productDto.setCategory(product.getCategory());
        return new ProductDto(
                product.getId(), product.getName(),product.getDecription(),product.getPrice(),product.getCategory()
        );
    }

    @Override
    public Product save(ProductRequest productRequest) {
        if(productRequest.getNameCate() == null || Objects.equals(productRequest.getNameCate(), "")) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        Product product = new Product(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice());

        Category category = categoryRepostitory.findByName(productRequest.getNameCate())
                    .orElseThrow(()-> new RuntimeException("Cant found Category"));
        product.setCategory(category);
        category.getProduct().add(product);
        categoryRepostitory.save(category);
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
        Optional<Product> oddproduct = productRepository.findById(productId);
        if(oddproduct.isPresent()) {
            try {
                Product product = oddproduct.get();
                product.setName(productRequest.getName());
                product.setDecription(productRequest.getDescription());
                product.setPrice(productRequest.getPrice());
                if(!Objects.equals(product.getCategory().getName(), productRequest.getNameCate())) {
                    product.getCategory().getProduct().remove(product);
                    categoryRepostitory
                            .save(product.getCategory());
                    Category category = categoryRepostitory.findByName(productRequest.getNameCate())
                            .orElseThrow(()-> new RuntimeException("Cant found Category"));
                    product.setCategory(category);
                }
                return productRepository.save(product);
            }
            catch (RuntimeException e) {
                throw new RuntimeException("An error occurred while saving the product");
            }
        }
        else {
            throw new RuntimeException("Product not found with Id: " + productId);
        }
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
    public List<Product> filterProducts(String cateName) {
        Optional<Category> category = Optional.ofNullable(categoryRepostitory.findByName(cateName)
                .orElseThrow(() -> new RuntimeException("can not found cate")));
        return productRepository.findAllByCategoryOrderByPriceDesc(category.get());
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
