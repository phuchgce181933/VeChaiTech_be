package com.ra.base_spring_boot.model;
import java.math.BigDecimal;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Orders extends BaseObject {

     @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "recycler_id")
    private User recycler; // null khi chưa ai nhận đơn

    @ManyToOne
    @JoinColumn(name = "waste_listing_id")
    private WasteListings wasteListing;

     // chỉ nhập sau khi thu gom tới nơi
    @Column(name = "quantity")
    private BigDecimal quantity; // kg, null lúc tạo đơn

    @Column(name = "total_price")
    private BigDecimal totalPrice; // null lúc tạo đơn

    @Column(name = "address_public")
    private String addressPublic; // gửi cho recycler

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "address_full")
    private String addressFull;

    @Column(name = "status")
    private String status; // PENDING, CLAIMED, COMPLETED
    
}
