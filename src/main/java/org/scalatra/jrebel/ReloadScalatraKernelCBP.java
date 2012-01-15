package org.scalatra.jrebel;

import org.zeroturnaround.bundled.javassist.*;
import org.zeroturnaround.javarebel.LoggerFactory;
import org.zeroturnaround.javarebel.integration.support.JavassistClassBytecodeProcessor;

public class ReloadScalatraKernelCBP extends JavassistClassBytecodeProcessor {
    public void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception {
        LoggerFactory.getInstance().echo("Patching " + ctClass.getName());
        try {
            cp.importPackage("org.scalatra.jrebel");

            CtMethod initMethod = ctClass.getDeclaredMethod("$init$");
            initMethod.insertAfter("ScalatraKernelReloader.register($this);");

            CtMethod handleMethod = ctClass.getDeclaredMethod("handle");
            handleMethod.insertAfter("if (ScalatraKernelReloader.needsReload($this)) { ScalatraKernelReloader.reload($this); }");
        } catch (NotFoundException e) {
            e.printStackTrace();
            LoggerFactory.getInstance().error(e);
        }
    }
}
