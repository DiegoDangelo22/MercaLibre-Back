package com.merca.back.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrdenDto {
    @NotNull
    private int draggedOrden;
    @NotNull
    private int targetOrden;

    public UpdateOrdenDto() {}

    public UpdateOrdenDto(int draggedOrden, int targetOrden) {
        this.draggedOrden = draggedOrden;
        this.targetOrden = targetOrden;
    }
}