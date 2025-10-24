package com.ra.base_spring_boot.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecyclerDemandsResponse {
    private Long Id;
    private String name;
    private String description;
    private String imageUrl;
    private String latitude;
    private String longitude;
}
