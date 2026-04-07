package my.webstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price; // decimal with a given no of decimal points
    private Integer rating; // one to five
    private Integer inStock;
    private Integer sellerId;
    // private String imageUrl;

}
