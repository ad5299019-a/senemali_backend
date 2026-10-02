package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class CommandeRequest {
    @NotEmpty private List<DetailCommandeRequest> details;
}
