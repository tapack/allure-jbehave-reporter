package io.tapack.allure.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import static org.jbehave.core.reporters.Format.CONSOLE;

public class BaseStory extends JUnitStory {

    @Override
    public Configuration configuration() {
        if (super.hasConfiguration()) {
            return super.configuration();
        }
        return new MostUsefulConfiguration()
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats()
                        .withFormats(CONSOLE)
                        .withReporters(new AllureReporter()));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new CalculateSteps());
    }

    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.embedderControls().doGenerateViewAfterStories(false);
        return embedder;
    }
}
