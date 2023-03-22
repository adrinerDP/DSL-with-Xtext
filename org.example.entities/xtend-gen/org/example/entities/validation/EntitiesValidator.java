package org.example.entities.validation;

import java.util.HashSet;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.example.entities.entities.Attribute;
import org.example.entities.entities.EntitiesPackage;
import org.example.entities.entities.Entity;

@SuppressWarnings("all")
public class EntitiesValidator extends AbstractEntitiesValidator {
  protected static final String ISSUE_CODE_PREFIX = "org.example.entities.*";

  public static final String HIERARCHY_CYCLE = (EntitiesValidator.ISSUE_CODE_PREFIX + "HierarchyCycle");

  public static final String HIERARCHY_LEVEL = (EntitiesValidator.ISSUE_CODE_PREFIX + "HierarchyLevel");

  public static final String INVALID_ENTITY_NAME = (EntitiesValidator.ISSUE_CODE_PREFIX + "InvalidEntityName");

  public static final String INVALID_ATTRIBUTE_NAME = (EntitiesValidator.ISSUE_CODE_PREFIX + "InvalidAttributeName");

  @Check
  public void checkNoCycleInEntityHierarchy(final Entity entity) {
    Entity _superType = entity.getSuperType();
    boolean _tripleEquals = (_superType == null);
    if (_tripleEquals) {
      return;
    }
    final HashSet<Entity> visitedEntities = CollectionLiterals.<Entity>newHashSet(entity);
    Entity current = entity.getSuperType();
    while ((current != null)) {
      {
        boolean _contains = visitedEntities.contains(current);
        if (_contains) {
          String _name = current.getName();
          String _plus = ("cycle in hierarchy of entity \'" + _name);
          String _plus_1 = (_plus + "\'");
          this.error(_plus_1, 
            EntitiesPackage.eINSTANCE.getEntity_SuperType(), 
            EntitiesValidator.HIERARCHY_CYCLE, 
            current.getSuperType().getName());
          return;
        }
        visitedEntities.add(current);
        current = current.getSuperType();
      }
    }
  }

  @Check
  public void checkEntityNameStartsWithCapical(final Entity entity) {
    boolean _isLowerCase = Character.isLowerCase(entity.getName().charAt(0));
    if (_isLowerCase) {
      this.warning(
        "Entity name should start with a capital", 
        EntitiesPackage.eINSTANCE.getEntity_Name(), 
        EntitiesValidator.INVALID_ENTITY_NAME, 
        entity.getName());
    }
  }

  @Check
  public void checkAttributeNameStartsWithLowercase(final Attribute attr) {
    boolean _isUpperCase = Character.isUpperCase(attr.getName().charAt(0));
    if (_isUpperCase) {
      this.warning(
        "Attribute name should start with a lowercase", 
        EntitiesPackage.eINSTANCE.getAttribute_Name(), 
        EntitiesValidator.INVALID_ATTRIBUTE_NAME, 
        attr.getName());
    }
  }

  @Check
  public void checkMaxHierarchyLevel(final Entity entity) {
    Entity _superType = entity.getSuperType();
    boolean _tripleEquals = (_superType == null);
    if (_tripleEquals) {
      return;
    }
    Entity current = entity;
    final HashSet<Entity> visitedEntities = CollectionLiterals.<Entity>newHashSet(current);
    while ((current != null)) {
      {
        visitedEntities.add(current);
        current = current.getSuperType();
        int _size = visitedEntities.size();
        boolean _greaterThan = (_size > 3);
        if (_greaterThan) {
          this.warning(
            "Hierarchy level should lower than two", 
            EntitiesPackage.eINSTANCE.getEntity_Name(), 
            EntitiesValidator.HIERARCHY_LEVEL, 
            entity.getName());
          return;
        }
      }
    }
  }
}
