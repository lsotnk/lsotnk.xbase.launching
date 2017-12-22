package lsotnk.xbase.launching.internal.ui;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.xbase.ui.launching.JavaElementDelegate;
import org.eclipse.xtext.xbase.ui.launching.JavaElementDelegateJunitLaunch;
import org.eclipse.xtext.xbase.ui.launching.JavaElementDelegateMainLaunch;

@SuppressWarnings("restriction")
public class EObjectNodeJavaElementDelegateAdapterFactory implements IAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
        if (adaptableObject instanceof EObjectNode && adapterType == JavaElementDelegateJunitLaunch.class) {
            return (T) getDelegate(JavaElementDelegateJunitLaunch.class);
        } else if (adaptableObject instanceof EObjectNode && adapterType == JavaElementDelegateMainLaunch.class) {
            return (T) getDelegate(JavaElementDelegateMainLaunch.class);
        }
        return null;
    }

    private <T extends JavaElementDelegate> T getDelegate(final Class<T> type) {
        IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        if (editor == null) {
            return null;
        }
        XtextEditor xtextEditor = getXtextEditor(editor);
        if (xtextEditor == null) {
            return null;
        }
        T delegate = ((XtextEditor) editor).getDocument().readOnly(
                resource -> resource.getResourceServiceProvider().get(type));
        if (delegate == null) {
            return null;
        }
        delegate.initializeWith(editor);
        return delegate;
    }

    @Override
    public Class<?>[] getAdapterList() {
        return new Class<?>[] { JavaElementDelegateJunitLaunch.class, JavaElementDelegateMainLaunch.class };
    }

    private XtextEditor getXtextEditor(final IEditorPart editor) {
        if (editor instanceof XtextEditor) {
            return (XtextEditor) editor;
        }
        return Adapters.adapt(editor, XtextEditor.class, true);
    }

}
