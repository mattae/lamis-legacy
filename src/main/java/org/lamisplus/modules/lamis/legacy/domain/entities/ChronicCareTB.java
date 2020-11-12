package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChronicCareTB implements Serializable {
    @JsonProperty("question")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private DMScreen dmScreen;

    @JsonProperty("answer")
    private String description;
}
