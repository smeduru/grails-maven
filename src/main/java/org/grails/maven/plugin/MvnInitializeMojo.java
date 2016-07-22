/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Validate consistency between Grails and Maven settings.
 *
 * @author <a href="mailto:aheritier@gmail.com">Arnaud HERITIER</a>
 * @version $Id$
 * @description Determines whether the current directory contains a Grails application or not, and creates one in the latter case.
 * @since 0.1
 */
@Mojo(name = "init", requiresDependencyResolution = ResolutionScope.RUNTIME, defaultPhase = LifecyclePhase.INITIALIZE)
public class MvnInitializeMojo extends AbstractGrailsMojo {

    /**
     * The artifact id of the project.
     */
    @Parameter(name = "project.artifactId", required = true, readonly = true)
    private String artifactId;

    /**
     * The version id of the project.
     */
    @Parameter(name = "project.version", required = true, readonly = true)
    private String version;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getGrailsServices().readProjectDescriptor();
        } catch (final MojoExecutionException ex) {
            // Initialise the app.
            getLog().info("Cannot read application info, so initialising new application.");
            runGrails("CreateApp", "--inplace --appVersion=" + version + " " + artifactId);
        }
    }
}
