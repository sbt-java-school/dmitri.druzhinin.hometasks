package ru.sbt;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

public class PluginManagerTest {
    /**
     * test will performed if the directory will contain MyPlugin.class
     */
    @Test
    public void testSystemLoaderAndPluginLoader() throws MalformedURLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        PluginManager pluginManager=new PluginManager("C:/Plugins/");
        Plugin plugin=new MyPlugin();//SystemLoader
        Plugin plugin1=pluginManager.load("SuperPlugin", "ru.sbt.MyPlugin");//PluginLoader
        Assert.assertFalse(plugin.getClass()==plugin1.getClass());
    }
}
