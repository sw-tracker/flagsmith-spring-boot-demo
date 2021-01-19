package com.flagsmithspringboot.demo;

import com.flagsmithspringboot.demo.dtos.EditFeatureFlagInboundRequestDTO;
import com.solidstategroup.bullettrain.Flag;
import com.solidstategroup.bullettrain.Trait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/feature-flags-demo")
public class FeatureFlagController {

  private final FeatureFlagService featureFlagService;

  @Autowired
  public FeatureFlagController(FeatureFlagService featureFlagService) {
    this.featureFlagService = featureFlagService;
  }

  /**
   * Get flags for an environment
   */
  @GetMapping()
  public ResponseEntity<List<Flag>> getFeatureTogglesForProject() {
    return ResponseEntity.ok(this.featureFlagService.getFeatureTogglesForProject());
  }

  /**
   * Get flags for a user
   * IMPORTANT: endpoint should be protected so that we can identify the user
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<Flag>> getFeatureTogglesForUser(@PathVariable String userId) {
    return ResponseEntity.ok(this.featureFlagService.getFeatureTogglesForUser(userId));
  }

  /**
   * Create or update a user trait
   * IMPORTANT: endpoint should be protected so that we can identify the user
   */
  @PutMapping()
  public ResponseEntity<List<Flag>> updateTraitForCurrentUser(@RequestBody @Valid Trait updateFeatureFlagDTO) {
    this.featureFlagService.updateTraitForCurrentUser(updateFeatureFlagDTO);
    return this.getFeatureTogglesForUser(updateFeatureFlagDTO.getIdentity().getIdentifier());
  }

  /**
   * This is a way of overriding a user flag by updating a trait that will make this user fall into/out of a segment.
   * IMPORTANT: endpoint should be protected so that we can identify the user
   */
  @PutMapping("/{userId}")
  public ResponseEntity<List<Flag>> updateTraitForCurrentUser(@PathVariable String userId,
                                                              @RequestBody @Valid EditFeatureFlagInboundRequestDTO updateFeatureFlagDTO) {
    this.featureFlagService.updateTraitForCurrentUser(userId, updateFeatureFlagDTO);
    return this.getFeatureTogglesForUser(userId);
  }
}
