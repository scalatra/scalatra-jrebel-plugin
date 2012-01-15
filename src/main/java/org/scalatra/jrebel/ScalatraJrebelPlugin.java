package org.scalatra.jrebel;

import org.zeroturnaround.javarebel.*;

public class ScalatraJrebelPlugin implements Plugin {
    public static final String SCALATRA_KERNEL_CLASSNAME = "org.scalatra.ScalatraKernel";

    public void preinit() {
        // Register the CBP
        Integration i = IntegrationFactory.getInstance();
        ClassLoader cl = ScalatraJrebelPlugin.class.getClassLoader();
        String traitImpl = SCALATRA_KERNEL_CLASSNAME + "$class";
        i.addIntegrationProcessor(cl, traitImpl, new ReloadScalatraKernelCBP());

        // Set up the reload listener
        ReloaderFactory.getInstance().addClassReloadListener(
                new ClassEventListener() {
                    public void onClassEvent(int eventType, Class klass) {
                        try {
                            Class scalatraKernelClass = Class.forName(SCALATRA_KERNEL_CLASSNAME);

                            // Check if it is child of AbstractCanvas
                            if (scalatraKernelClass.isAssignableFrom(klass)) {
                                LoggerFactory.getInstance().echo("Reloading " + klass);
                                ScalatraKernelReloader.classReloaded();
                            }
                        } catch (Exception e) {
                            LoggerFactory.getInstance().error(e);
                            System.out.println(e);
                        }
                    }

                    public int priority() {
                        return 0;
                    }
                }
        );

    }

    public boolean checkDependencies(ClassLoader classLoader, ClassResourceSource classResourceSource) {
        return classResourceSource.getClassResource(SCALATRA_KERNEL_CLASSNAME) != null;
    }

    public String getId() {
        return "scalatra-jrebel-plugin";
    }

    public String getName() {
        return "Scalatra JRebel Plugin";
    }

    public String getDescription() {
        return "Reconstructs Scalatra kernels to pick up new routes";
    }

    public String getAuthor() {
        return "The Scalatra Project";
    }

    public String getWebsite() {
        return "http://www.scalatra.org/";
    }
}
