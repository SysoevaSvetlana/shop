package ru.gb.shop.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "new_product")
    private Boolean newProduct;

    @Column(name = "hot_product")
    private Boolean hotProduct;

    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    private String image;

    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.EAGER)// опционально для больших текстовых полей
    private String description;


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();



    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setProduct(this); // Устанавливаем обратную связь
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setProduct(null); // Разрываем обратную связь
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*@Column(length = 65535)
    @Type(type = "text")
    private String description;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_type_id")
    private ProductType productType;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Boolean newProduct) {
        this.newProduct = newProduct;
    }

    public Boolean getHotProduct() {
        return hotProduct;
    }

    public void setHotProduct(Boolean hotProduct) {
        this.hotProduct = hotProduct;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
