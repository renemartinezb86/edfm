package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.SheetType;

/**
 * A BucoSheet.
 */
@Entity
@Table(name = "buco_sheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BucoSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sheet_name")
    private String sheetName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sheet_type")
    private SheetType sheetType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private BucoVersion bucoVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSheetName() {
        return sheetName;
    }

    public BucoSheet sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public SheetType getSheetType() {
        return sheetType;
    }

    public BucoSheet sheetType(SheetType sheetType) {
        this.sheetType = sheetType;
        return this;
    }

    public void setSheetType(SheetType sheetType) {
        this.sheetType = sheetType;
    }

    public BucoVersion getBucoVersion() {
        return bucoVersion;
    }

    public BucoSheet bucoVersion(BucoVersion bucoVersion) {
        this.bucoVersion = bucoVersion;
        return this;
    }

    public void setBucoVersion(BucoVersion bucoVersion) {
        this.bucoVersion = bucoVersion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BucoSheet bucoSheet = (BucoSheet) o;
        if (bucoSheet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bucoSheet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BucoSheet{" +
            "id=" + getId() +
            ", sheetName='" + getSheetName() + "'" +
            ", sheetType='" + getSheetType() + "'" +
            "}";
    }
}
