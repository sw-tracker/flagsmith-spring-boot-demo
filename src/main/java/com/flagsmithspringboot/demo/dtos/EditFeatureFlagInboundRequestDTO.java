package com.flagsmithspringboot.demo.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

public class EditFeatureFlagInboundRequestDTO {
  @NotNull
  private FeatureFlagsEnum feature;
  @NotNull
  private Boolean enabled;

  public FeatureFlagsEnum getFeature() {
    return feature;
  }

  public void setFeature(FeatureFlagsEnum feature) {
    this.feature = feature;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EditFeatureFlagInboundRequestDTO that = (EditFeatureFlagInboundRequestDTO) o;
    return feature == that.feature &&
        Objects.equals(enabled, that.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(feature, enabled);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EditFeatureFlagInboundRequestDTO.class.getSimpleName() + "[", "]")
        .add("feature=" + feature)
        .add("enabled=" + enabled)
        .toString();
  }
}
