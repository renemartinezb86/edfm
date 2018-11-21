package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.RuleType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Rule entity. This class is used in RuleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /rules?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RuleCriteria implements Serializable {
    /**
     * Class for filtering RuleType
     */
    public static class RuleTypeFilter extends Filter<RuleType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ruleId;

    private RuleTypeFilter ruleType;

    private StringFilter definition;

    private StringFilter scenario;

    private StringFilter detail;

    private LongFilter productOfferingId;

    public RuleCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRuleId() {
        return ruleId;
    }

    public void setRuleId(StringFilter ruleId) {
        this.ruleId = ruleId;
    }

    public RuleTypeFilter getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleTypeFilter ruleType) {
        this.ruleType = ruleType;
    }

    public StringFilter getDefinition() {
        return definition;
    }

    public void setDefinition(StringFilter definition) {
        this.definition = definition;
    }

    public StringFilter getScenario() {
        return scenario;
    }

    public void setScenario(StringFilter scenario) {
        this.scenario = scenario;
    }

    public StringFilter getDetail() {
        return detail;
    }

    public void setDetail(StringFilter detail) {
        this.detail = detail;
    }

    public LongFilter getProductOfferingId() {
        return productOfferingId;
    }

    public void setProductOfferingId(LongFilter productOfferingId) {
        this.productOfferingId = productOfferingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RuleCriteria that = (RuleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ruleId, that.ruleId) &&
            Objects.equals(ruleType, that.ruleType) &&
            Objects.equals(definition, that.definition) &&
            Objects.equals(scenario, that.scenario) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ruleId,
        ruleType,
        definition,
        scenario,
        detail,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "RuleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ruleId != null ? "ruleId=" + ruleId + ", " : "") +
                (ruleType != null ? "ruleType=" + ruleType + ", " : "") +
                (definition != null ? "definition=" + definition + ", " : "") +
                (scenario != null ? "scenario=" + scenario + ", " : "") +
                (detail != null ? "detail=" + detail + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
