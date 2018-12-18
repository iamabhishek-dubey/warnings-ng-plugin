package io.jenkins.plugins.analysis.core.filter;

import javax.annotation.Nonnull;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Report.IssueFilterBuilder;

import org.kohsuke.stapler.DataBoundConstructor;
import hudson.Extension;
import org.jenkinsci.Symbol;

import io.jenkins.plugins.analysis.core.model.Messages;

/**
 * Defines a filter criteria for a {@link Report}.
 *
 * @author Ullrich Hafner
 */
public class IncludeFile extends RegexpFilter {
    private static final long serialVersionUID = 6549206934593163281L;

    /**
     * Creates a new instance of {@link IncludeFile}.
     *
     * @param pattern
     *         the regular expression of the filter
     */
    @DataBoundConstructor
    public IncludeFile(final String pattern) {
        super(pattern);
    }

    @Override
    public void apply(final IssueFilterBuilder builder) {
        builder.setIncludeFileNameFilter(getPattern());
    }

    /**
     * Descriptor for {@link IncludeFile}.
     *
     * @author Ullrich Hafner
     */
    @Extension
    @Symbol("includeFile")
    public static class DescriptorImpl extends RegexpFilterDescriptor {
        @Nonnull
        @Override
        public String getDisplayName() {
            return Messages.Filter_Include_File();
        }
    }
}