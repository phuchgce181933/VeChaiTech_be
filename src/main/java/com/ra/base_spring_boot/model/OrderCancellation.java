package com.ra.base_spring_boot.model;


import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderCancellation extends BaseObject {
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "cancelled_by")
    private User cancelledBy;
    // customer hoặc recycler

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "evidence_url")
    private String evidenceUrl;
    // link file (S3, Cloudinary, local, etc.)

}
