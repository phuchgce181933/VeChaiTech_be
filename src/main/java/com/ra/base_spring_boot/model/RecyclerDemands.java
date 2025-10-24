package com.ra.base_spring_boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "recyclerdemands")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RecyclerDemands extends BaseObject {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "public_id")
    private String public_id;

    @Column(name = "street")
    private String street;
    // Phường / Xã
    @Column(name = "ward")
    private String ward;
    // Quận / Huyện
    @Column(name = "district")
    private String district;
    // Tỉnh / Thành phố
    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;
    // latitude vs longitude dùng để backup nếu qua được ải của google
    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "status")
    private Boolean status;

    // 🔗 Quan hệ 1-nhiều với WasteListings
    @JsonIgnore
    @OneToMany(mappedBy = "recyclerDemand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WasteListings> wasteListings;
}
