/**
 * generated by Xtext 2.29.0
 */
package org.example.entities;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class EntitiesStandaloneSetup extends EntitiesStandaloneSetupGenerated {
  public static void doSetup() {
    new EntitiesStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
