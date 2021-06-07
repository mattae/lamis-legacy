/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lamisplus.modules.lamis.legacy.domain.entities;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.lamisplus.modules.base.domain.entities.Province;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author User10
 */
@Entity
@Table(name = "ddd_outlet")
@SQLDelete(sql = "update ddd_outlet set archived = true, last_modified = current_timestamp where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "archived = false")
@Data
public class DDDOutlet implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @NotNull
    @JoinColumn(name = "LGA_ID")
    @ManyToOne
    private Province lga;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @Size(max = 300)
    @Column(name = "ADDRESS")
    private String address;

    @Size(max = 25)
    @Column(name = "PHONE")
    private String phone;

    @Size(max = 25)
    @Column(name = "PHONE1")
    private String phone1;

    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PIN")
    private String pin;

    private String type;

    private Boolean archived = false;

    private String uuid;

    private Boolean active = true;

    private LocalDateTime lastModified;

    @PrePersist
    public void prePersist() {
        lastModified = LocalDateTime.now();
        uuid = UUID.randomUUID().toString();

        name = WordUtils.capitalize(StringUtils.lowerCase(name));
        address = WordUtils.capitalize(StringUtils.replace(StringUtils.lowerCase(address), ",", ", ").replaceAll("\\s+", " "));
    }

    @PreUpdate
    public void preUpdate() {
        lastModified = LocalDateTime.now();

        name = WordUtils.capitalize(StringUtils.lowerCase(name));
        address = WordUtils.capitalize(
            StringUtils.trimToEmpty(StringUtils.replace(StringUtils.lowerCase(address), ",", ", "))
                .replaceAll("\\s+", " "));
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
