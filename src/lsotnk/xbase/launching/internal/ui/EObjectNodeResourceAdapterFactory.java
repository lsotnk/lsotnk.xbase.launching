package lsotnk.xbase.launching.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

public class EObjectNodeResourceAdapterFactory implements IAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
        if (adaptableObject instanceof EObjectNode && adapterType == IResource.class) {
            EObjectNode node = (EObjectNode) adaptableObject;
            URI uri = node.getEObjectURI();
            if (uri != null && uri.isPlatformResource()) {
                IPath path = new Path(uri.toPlatformString(true));
                IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
                if (file.exists()) {
                    return (T) file;
                }
            }
        }
        return null;
    }

    @Override
    public Class<?>[] getAdapterList() {
        return new Class<?>[] { IResource.class };
    }

}
