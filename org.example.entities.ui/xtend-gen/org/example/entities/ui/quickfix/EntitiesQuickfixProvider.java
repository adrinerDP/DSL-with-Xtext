package org.example.entities.ui.quickfix;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.example.entities.entities.Attribute;
import org.example.entities.entities.EntitiesFactory;
import org.example.entities.entities.Entity;
import org.example.entities.entities.Model;
import org.example.entities.validation.EntitiesValidator;

@SuppressWarnings("all")
public class EntitiesQuickfixProvider extends DefaultQuickfixProvider {
  @Fix(EntitiesValidator.INVALID_ENTITY_NAME)
  public void capitalizeEntityNameFirstLetter(final Issue issue, final IssueResolutionAcceptor acceptor) {
    String _get = issue.getData()[0];
    String _plus = ("Capitalize first letter of \'" + _get);
    String _plus_1 = (_plus + "\'");
    final IModification _function = (IModificationContext context) -> {
      final IXtextDocument xtextDocument = context.getXtextDocument();
      final String firstLetter = xtextDocument.get((issue.getOffset()).intValue(), 1);
      xtextDocument.replace((issue.getOffset()).intValue(), 1, StringExtensions.toFirstUpper(firstLetter));
    };
    acceptor.accept(issue, 
      "Capitalize first letter", _plus_1, 
      "Entity.gif", _function);
  }

  @Fix(EntitiesValidator.INVALID_ATTRIBUTE_NAME)
  public void uncapitalizeAttributeNameFirstLetter(final Issue issue, final IssueResolutionAcceptor acceptor) {
    String _get = issue.getData()[0];
    String _plus = ("Uncapitalize first letter of \'" + _get);
    String _plus_1 = (_plus + "\'");
    final ISemanticModification _function = (EObject element, IModificationContext context) -> {
      ((Attribute) element).setName(StringExtensions.toFirstLower(issue.getData()[0]));
    };
    acceptor.accept(issue, 
      "Uncapitalize first letter", _plus_1, 
      "Attribute.gif", _function);
  }

  @Fix(EntitiesValidator.HIERARCHY_CYCLE)
  public void removeSuperType(final Issue issue, final IssueResolutionAcceptor acceptor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Remove supertype \'");
    String _get = issue.getData()[0];
    _builder.append(_get);
    _builder.append("\' ");
    final ISemanticModification _function = (EObject element, IModificationContext context) -> {
      ((Entity) element).setSuperType(null);
    };
    acceptor.accept(issue, 
      "Remove supertype", _builder.toString(), 
      "delete_obj.gif", _function);
  }

  @Fix(Diagnostic.LINKING_DIAGNOSTIC)
  public void createMissingEntity(final Issue issue, final IssueResolutionAcceptor acceptor) {
    final ISemanticModification _function = (EObject element, IModificationContext context) -> {
      final Entity currentEntity = EcoreUtil2.<Entity>getContainerOfType(element, Entity.class);
      EObject _eContainer = currentEntity.eContainer();
      final Model model = ((Model) _eContainer);
      EList<Entity> _entities = model.getEntities();
      int _indexOf = model.getEntities().indexOf(currentEntity);
      int _plus = (_indexOf + 1);
      Entity _createEntity = EntitiesFactory.eINSTANCE.createEntity();
      final Procedure1<Entity> _function_1 = (Entity it) -> {
        try {
          it.setName(context.getXtextDocument().get((issue.getOffset()).intValue(), (issue.getLength()).intValue()));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      Entity _doubleArrow = ObjectExtensions.<Entity>operator_doubleArrow(_createEntity, _function_1);
      _entities.add(_plus, _doubleArrow);
    };
    acceptor.accept(issue, 
      "Create missing entity", 
      "Create missing entity", 
      "Entity.gif", _function);
  }

  @Fix(EntitiesValidator.HIERARCHY_LEVEL)
  public void removeLevelSuperType(final Issue issue, final IssueResolutionAcceptor acceptor) {
    String _get = issue.getData()[0];
    String _plus = ("Remove entity \'" + _get);
    String _plus_1 = (_plus + "\'");
    final ISemanticModification _function = (EObject element, IModificationContext context) -> {
      EcoreUtil.delete(element);
    };
    acceptor.accept(issue, _plus_1, 
      "", 
      "Entity.gif", _function);
    String _get_1 = issue.getData()[0];
    String _plus_2 = ("Remove supertype of \'" + _get_1);
    String _plus_3 = (_plus_2 + "\'");
    final ISemanticModification _function_1 = (EObject element, IModificationContext context) -> {
      ((Entity) element).setSuperType(null);
    };
    acceptor.accept(issue, _plus_3, 
      "", 
      "delete_obj.gif", _function_1);
  }
}
