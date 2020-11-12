package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class PharmacyLine  implements Serializable {
    public PharmacyLine() {}

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "MORNING")
    private Double morning;

    @Column(name = "AFTERNOON")
    private Double afternoon;

    @Column(name = "EVENING")
    private Double evening;

    @JsonProperty("regimen_id")
    private Long regimenId;

    @JsonProperty("regimen_type_id")
    private Long regimenTypeId;

    @JsonProperty("regimen_drug_id")
    private Long regimenDrugId;
}
