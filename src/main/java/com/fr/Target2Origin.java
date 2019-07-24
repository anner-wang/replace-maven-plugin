package com.fr;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author anner
 * @date 19-7-23
 **/
@Mojo(name = "back")
public class Target2Origin extends AbstractMojo {
    @Parameter(property = "origins")
    private String[] origins;
    @Parameter(property = "targets")
    private String[] targets;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Replace replace=new Replace(targets,origins);
        replace.run();
    }
}
