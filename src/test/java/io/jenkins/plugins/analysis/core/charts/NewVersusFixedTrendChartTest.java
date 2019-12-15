package io.jenkins.plugins.analysis.core.charts;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.util.AnalysisBuildResult;
import io.jenkins.plugins.echarts.BuildResult;
import io.jenkins.plugins.echarts.ChartModelConfiguration;
import io.jenkins.plugins.echarts.LineSeries;
import io.jenkins.plugins.echarts.LinesChartModel;
import io.jenkins.plugins.echarts.Palette;

import static io.jenkins.plugins.analysis.core.charts.BuildResultStubs.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.*;

/**
 * Tests the class {@link NewVersusFixedTrendChart}.
 *
 * @author Fabian Janker
 */
class NewVersusFixedTrendChartTest {
    @Test
    void shouldCreateALinesChartModel() {
        NewVersusFixedTrendChart chart = new NewVersusFixedTrendChart();

        List<BuildResult<AnalysisBuildResult>> results = new ArrayList<>();
        results.add(createResultWithNewAndFixedIssues(2, 20, 21));
        results.add(createResultWithNewAndFixedIssues(1, 10, 11));

        LinesChartModel model = chart.create(results, new ChartModelConfiguration());

        verifySeries(model.getSeries().get(0), Palette.RED, "New", 10, 20);
        verifySeries(model.getSeries().get(1), Palette.GREEN, "Fixed", 11, 21);

        assertThatJson(model).node("domainAxisLabels")
                .isArray().hasSize(2)
                .contains("#1")
                .contains("#2");

        assertThatJson(model).node("series")
                .isArray().hasSize(2);
    }

    private void verifySeries(final LineSeries series, final Palette normalColor,
            final String newVersusFixedSeriesBuilderName, final int... values) {
        assertThatJson(series).node("itemStyle").node("color").isEqualTo(normalColor.getNormal());
        assertThatJson(series).node("name").isString().isEqualTo(newVersusFixedSeriesBuilderName);
        for (int value : values) {
            assertThatJson(series).node("data").isArray().hasSize(values.length).contains(value);
        }
    }
}
