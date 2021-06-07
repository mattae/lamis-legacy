package org.lamisplus.modules.lamis.legacy.domain.entities;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@SQLDelete(sql = "update biometric set archived = true where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
public class Biometric implements Serializable, Persistable<String> {

    @Id
    @GeneratedValue( generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;

    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "UUID")
    @ManyToOne(optional = false)
    private Patient patient;

    @JoinColumn(name = "FACILITY_ID")
    @ManyToOne(optional = false)
    private Facility facility;

    @NotNull
    private byte[] template;

    @Column(name = "BIOMETRIC_TYPE")
    private String biometricType;

    @Column(name = "TEMPLATE_TYPE")
    private String templateType;

    @Column(name = "ENROLLMENT_DATE")
    private LocalDate date;

    private Boolean archived = false;

    private Boolean iso = false;

    private LocalDateTime lastModified;

    @Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
    private JsonNode extra;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @PrePersist
    @PreUpdate
    public void update() {
        lastModified = LocalDateTime.now();
    }
}
