package org.broadleafcommerce.core.catalog.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.broadleafcommerce.common.copy.CreateResponse;
import org.broadleafcommerce.common.copy.MultiTenantCloneable;
import org.broadleafcommerce.common.copy.MultiTenantCopyContext;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author Chris Kittrell (ckittrell)
 */
@Embeddable
public class OrderedSkuMediaXrefImpl implements OrderedSkuMediaXref, MultiTenantCloneable<OrderedSkuMediaXref>, Comparable<OrderedSkuMediaXref> {

    @Parent
    protected SkuMediaXref skuMediaXref;

    @Column(name = "DISPLAY_ORDER")
    @AdminPresentation(friendlyName = "OrderedSkuMediaXref_displayOrder", visibility = VisibilityEnum.FORM_EXPLICITLY_HIDDEN)
    protected BigDecimal displayOrder;

    @Column(name = "SHOW_IN_GALLERY")
    @AdminPresentation(friendlyName = "OrderedSkuMediaXref_showInGallery",
            group = "Options",
            order = 1000,
            defaultValue = "true")
    private Boolean showInGallery = true;

    @Override
    public BigDecimal getDisplayOrder() {
        return displayOrder == null ? new BigDecimal(Integer.MAX_VALUE) : displayOrder;
    }

    @Override
    public void setDisplayOrder(BigDecimal displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public boolean getShowInGallery() {
        return (showInGallery == null)? Boolean.TRUE : showInGallery;
    }

    @Override
    public void setShowInGallery(boolean showInGallery) {
        this.showInGallery = showInGallery;
    }

    @Override
    public SkuMediaXref getSkuMediaXref() {
        return skuMediaXref;
    }

    @Override
    public void setSkuMediaXref(SkuMediaXref skuMediaXref) {
        this.skuMediaXref = skuMediaXref;
    }

    @Override
    public int compareTo(OrderedSkuMediaXref o) {
        return getDisplayOrder().compareTo(o.getDisplayOrder());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
            OrderedSkuMediaXrefImpl other = (OrderedSkuMediaXrefImpl) obj;
            return new EqualsBuilder()
                    .append(skuMediaXref, other.skuMediaXref)
                    .append(displayOrder, other.displayOrder)
                    .append(showInGallery, other.showInGallery)
                    .build();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(skuMediaXref)
                .append(displayOrder)
                .append(showInGallery)
                .toHashCode();
    }

    @Override
    public <G extends OrderedSkuMediaXref> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OrderedSkuMediaXref cloned = createResponse.getClone();
        cloned.setDisplayOrder(displayOrder);
        cloned.setShowInGallery(showInGallery);

        //Don't clone references to other Product and Category collections - those will be handled by another MultiTenantCopier call
        return createResponse;
    }

}
