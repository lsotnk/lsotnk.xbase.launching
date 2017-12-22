package lsotnk.xbase.launching.internal.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.ILaunchable;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

public class EObjectNodeLaunchableAdapterFactory implements IAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
        if (adaptableObject instanceof EObjectNode && adapterType == ILaunchable.class) {
            return (T) XbaseLaunchable.INSTANCE;
        }
        return null;
    }

    @Override
    public Class<?>[] getAdapterList() {
        return new Class<?>[] { ILaunchable.class };
    }

    static class XbaseLaunchable implements ILaunchable {

        static final ILaunchable INSTANCE = new XbaseLaunchable();

    }

}
