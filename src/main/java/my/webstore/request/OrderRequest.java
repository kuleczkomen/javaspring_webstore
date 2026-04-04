package my.webstore.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import my.webstore.model.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

public record OrderRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        Date date,
        OrderStatus status,
        BigDecimal price) {
}
