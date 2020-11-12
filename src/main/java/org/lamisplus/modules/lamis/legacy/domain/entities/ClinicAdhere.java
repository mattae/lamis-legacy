package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClinicAdhere implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("adhere")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Adhere adhere;
}
