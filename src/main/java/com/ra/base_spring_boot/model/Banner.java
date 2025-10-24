package com.ra.base_spring_boot.model;

import com.ra.base_spring_boot.model.base.BaseObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "banners")
public class Banner extends BaseObject {
    @Column(name = "public_id")
    private String publicId;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "title", nullable = true, length = 255)
    private String title;

    @Column(name = "status")
    private Boolean status;

    //HEADER, FOOTER, SIDEBAR
    @Column(name = "position", nullable = true)
    private String position;

    @Column(name = "time_start")
    private LocalDateTime  startAt;

    @Column(name = "time_end")
    private LocalDateTime endAt;

    @Column(name = "target_url", nullable = true)
    private String targetUrl;

}
