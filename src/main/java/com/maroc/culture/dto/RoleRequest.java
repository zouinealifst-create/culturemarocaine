package com.maroc.culture.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    private Long personId;
    private String role; // مثلاً: "Réalisateur", "Acteur", "Scénariste"
}
