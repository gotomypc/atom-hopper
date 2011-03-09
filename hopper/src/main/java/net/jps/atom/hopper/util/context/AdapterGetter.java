/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jps.atom.hopper.util.context;

import com.rackspace.cloud.commons.util.StringUtilities;
import com.rackspace.cloud.commons.util.reflection.ReflectionTools;
import com.rackspace.cloud.commons.util.servlet.context.ApplicationContextAdapter;

/**
 *
 * 
 */
public class AdapterGetter {

    private final ApplicationContextAdapter contextAdapter;

    public AdapterGetter(ApplicationContextAdapter contextAdapter) {
        this.contextAdapter = contextAdapter;
    }

    public <T> T getByName(String referenceName, Class<T> classToCastTo) {
        if (StringUtilities.isBlank(referenceName)) {
            throw new IllegalArgumentException("Bean reference for an adapter must not be empty or null");
        }

        final Object reference = contextAdapter.fromContext(referenceName, classToCastTo);

        if (reference == null) {
            throw new AdapterNotFoundException("Unable to find adapter by name: " + referenceName);
        } else if (!classToCastTo.isInstance(reference)) {
            throw new IllegalArgumentException("Class: "
                    + reference.getClass().getCanonicalName()
                    + " does not implement " + classToCastTo.getCanonicalName());
        }

        return (T) reference;
    }

    public <T> T getByClassDefinition(Class<?> configuredAdapterClass, Class<T> classToCastTo) {
        if (!classToCastTo.isAssignableFrom(configuredAdapterClass)) {
            throw new IllegalArgumentException("Class: "
                    + configuredAdapterClass.getCanonicalName()
                    + " does not implement " + classToCastTo.getCanonicalName());
        }

        try {
            final T instance = (T) contextAdapter.fromContext(configuredAdapterClass);

            return instance != null
                    ? instance
                    : (T) ReflectionTools.construct(configuredAdapterClass, new Object[0]);
        } catch (Exception ex) {
            throw new AdapterConstructionException("Failed to get and or construct class: "
                    + configuredAdapterClass.getCanonicalName(), ex);
        }
    }
}
