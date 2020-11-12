package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClinicOpportunisticInfection implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("opportunisticInfection")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private OpportunisticInfection opportunisticInfection;
}
