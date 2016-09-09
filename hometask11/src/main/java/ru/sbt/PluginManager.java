package ru.sbt;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private final String pluginRootDirectory;
    private final PluginLoader pluginLoader;
    private final Map<String, Class<?>> loadedClasses;//cache.

    public PluginManager(String pluginRootDirectory) throws MalformedURLException {
        this.pluginRootDirectory = pluginRootDirectory;
        pluginLoader=new PluginLoader(new URL[]{new URL("file://"+pluginRootDirectory+"/")});
        loadedClasses=new HashMap<>();
    }

    public Plugin load(String pluginName, String pluginClassName) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> pluginClass=loadedClasses.get(pluginClassName);
        if(pluginClass == null) {//если раньше такой класс не загружался в этом pluginManager
            pluginLoader.addURL(new URL("file://" + pluginRootDirectory + "/" + pluginName + "/"));
            pluginClass = pluginLoader.loadClass(pluginClassName);
            loadedClasses.put(pluginClassName, pluginClass);
        }
        return (Plugin) pluginClass.newInstance();
    }
     private class PluginLoader extends URLClassLoader{

        public PluginLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        public PluginLoader(URL[] urls) {
            super(urls);
        }

        public PluginLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
            super(urls, parent, factory);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            Class<?> result;
            try {
                result=findClass(name);//сначала искать в директории для плагинов.
            } catch (ClassNotFoundException e) {//если там такой плагин отсутствует,
                result = super.loadClass(name);//то использовать обычный механизм загрузки.
            }
            return result;
        }
        @Override
        protected void addURL(URL url){
            super.addURL(url);
        }
    }
}
