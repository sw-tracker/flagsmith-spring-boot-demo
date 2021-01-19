package com.flagsmithspringboot.demo;

import com.flagsmithspringboot.demo.dtos.EditFeatureFlagInboundRequestDTO;
import com.solidstategroup.bullettrain.BulletTrainClient;
import com.solidstategroup.bullettrain.FeatureUser;
import com.solidstategroup.bullettrain.Flag;
import com.solidstategroup.bullettrain.Trait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagService {
  private final BulletTrainClient flagsmithClient;
  private final String baseURI;

  public FeatureFlagService(@Value("${feature-flags.base-uri}") String baseURI,
                            @Value("${feature-flags.env-key}") String envKey) {
    this.baseURI = baseURI;
    this.flagsmithClient = BulletTrainClient.newBuilder()
        .setApiKey(envKey)
        .withApiUrl(this.baseURI)
        .build();
  }

  public List<Flag> getFeatureTogglesForProject() {
    return this.flagsmithClient.getFeatureFlags();
  }

  public List<Flag> getFeatureTogglesForUser(String userId) {
    FeatureUser user = new FeatureUser();
    user.setIdentifier(userId);
    return this.flagsmithClient.getFeatureFlags(user);
  }

  public void updateTraitForCurrentUser(Trait trait) {
    this.flagsmithClient.updateTrait(trait.getIdentity(), trait);
  }

  public void updateTraitForCurrentUser(String userId, EditFeatureFlagInboundRequestDTO updateFeatureFlagDTO) {
    final FeatureUser user = this.getNewUser(userId);

    // Create trait object
    final Trait userTrait = new Trait();
    userTrait.setIdentity(user);
    userTrait.setKey(updateFeatureFlagDTO.getFeature().getValue());
    // dont use "true"/"false" because Flagsmith is written in python
    // the value here will stay in lower case, but when you create the segment, the value will become "True"/"False"
    // and therefore this user will not be part of the segment
    userTrait.setValue(updateFeatureFlagDTO.getEnabled() ? "1" : "0");

    // update trait in Flagsmith
    this.flagsmithClient.updateTrait(user, userTrait);
  }

  private FeatureUser getNewUser(String userIdentifier) {
    FeatureUser user = new FeatureUser();
    user.setIdentifier(userIdentifier);
    return user;
  }
}
