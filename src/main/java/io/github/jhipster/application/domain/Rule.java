package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.RuleType;

/**
 * A Rule.
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rule_id")
    private String ruleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    private RuleType ruleType;

    @Column(name = "definition")
    private String definition;

    @Column(name = "scenario")
    private String scenario;

    @Column(name = "detail")
    private String detail;

    @ManyToMany(mappedBy = "rules")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ProductOffering> productOfferings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public Rule ruleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public Rule ruleType(RuleType ruleType) {
        this.ruleType = ruleType;
        return this;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getDefinition() {
        return definition;
    }

    public Rule definition(String definition) {
        this.definition = definition;
        return this;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getScenario() {
        return scenario;
    }

    public Rule scenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getDetail() {
        return detail;
    }

    public Rule detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public Rule productOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
        return this;
    }

    public Rule addProductOffering(ProductOffering productOffering) {
        this.productOfferings.add(productOffering);
        productOffering.getRules().add(this);
        return this;
    }

    public Rule removeProductOffering(ProductOffering productOffering) {
        this.productOfferings.remove(productOffering);
        productOffering.getRules().remove(this);
        return this;
    }

    public void setProductOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
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
        Rule rule = (Rule) o;
        if (rule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", ruleId='" + getRuleId() + "'" +
            ", ruleType='" + getRuleType() + "'" +
            ", definition='" + getDefinition() + "'" +
            ", scenario='" + getScenario() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
