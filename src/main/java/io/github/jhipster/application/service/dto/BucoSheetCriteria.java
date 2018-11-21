package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.SheetType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the BucoSheet entity. This class is used in BucoSheetResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /buco-sheets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BucoSheetCriteria implements Serializable {
    /**
     * Class for filtering SheetType
     */
    public static class SheetTypeFilter extends Filter<SheetType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter sheetName;

    private SheetTypeFilter sheetType;

    private LongFilter bucoVersionId;

    public BucoSheetCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSheetName() {
        return sheetName;
    }

    public void setSheetName(StringFilter sheetName) {
        this.sheetName = sheetName;
    }

    public SheetTypeFilter getSheetType() {
        return sheetType;
    }

    public void setSheetType(SheetTypeFilter sheetType) {
        this.sheetType = sheetType;
    }

    public LongFilter getBucoVersionId() {
        return bucoVersionId;
    }

    public void setBucoVersionId(LongFilter bucoVersionId) {
        this.bucoVersionId = bucoVersionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BucoSheetCriteria that = (BucoSheetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sheetName, that.sheetName) &&
            Objects.equals(sheetType, that.sheetType) &&
            Objects.equals(bucoVersionId, that.bucoVersionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sheetName,
        sheetType,
        bucoVersionId
        );
    }

    @Override
    public String toString() {
        return "BucoSheetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sheetName != null ? "sheetName=" + sheetName + ", " : "") +
                (sheetType != null ? "sheetType=" + sheetType + ", " : "") +
                (bucoVersionId != null ? "bucoVersionId=" + bucoVersionId + ", " : "") +
            "}";
    }

}
