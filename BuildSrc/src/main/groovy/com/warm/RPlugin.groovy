package com.warm

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class RPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {


        def android = project.extensions.findByName("android")

        if (android) {
            android.defaultConfig.javaCompileOptions.annotationProcessorOptions.argument(Config.KEY_MODULE_NAME, project.name)
        }
        if (project.plugins.hasPlugin(AppPlugin)) {
            project.android.registerTransform(new RTransform(project))
        }
    }
}