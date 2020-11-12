package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "LABORATORY")
@Data
@SQLDelete(sql = "update laboratory set archived = true, last_modified = current_timestamp where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
@EqualsAndHashCode(of = {"patient", "dateResultReceived"}, callSuper = true)
@ToString(of = "dateResultReceived", callSuper = true)
public class Laboratory extends TransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "PATIENT_ID")
    @ManyToOne
    private Patient patient;

    private LocalDate dateResultReceived;

    private LocalDate dateSampleCollected;

    private LocalDate dateAssay;

    @Size(max = 15)
    @Column(name = "LABNO")
    private String labNo;

    @Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
    private JsonNode extra;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<LaboratoryLine> lines = new HashSet<>();
}
