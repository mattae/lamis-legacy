package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class LaboratoryLine implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 10)
    @Column(name = "RESULT")
    private String result;

    @Size(max = 100)
    @Column(name = "COMMENT")
    private String comment;

    private String indication;

    @JsonProperty("lab_test_id")
    private Long labTestId;
}
