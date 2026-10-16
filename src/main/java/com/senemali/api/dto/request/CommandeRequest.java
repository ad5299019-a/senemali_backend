package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CommandeRequest {
    @NotEmpty private List<@NotNull @Valid DetailCommandeRequest> details;
}
