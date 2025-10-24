package com.ra.base_spring_boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ra.base_spring_boot.model.base.BaseObject;
import com.ra.base_spring_boot.model.constants.WasteType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "WasteListings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WasteListings extends BaseObject {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "waste_type")
    private WasteType wasteType;

    @Column(name = "waste_url")
    private String wasteUrl;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "status")
    private Boolean status;

    // 🔗 Khóa ngoại - thuộc về RecyclerDemands
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recycler_demand_id")
    private RecyclerDemands recyclerDemand;
}
