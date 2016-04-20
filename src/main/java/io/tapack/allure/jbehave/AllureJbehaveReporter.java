package io.tapack.allure.jbehave;

import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Jbehave Reporter that map Cucumber Scenario to Allure test.
 */
public class AllureJBehaveReporter implements StoryReporter {

    private Allure lifecycle = Allure.LIFECYCLE;
    private final Map<String, String> suites = new HashMap<>();
    private String uid;
    private boolean withExamples = false;
    private String exampleName;


    @Override
    public void storyNotAllowed(Story story, String filter) {

    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {

    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        uid = generateSuiteUid(story.getName());
        TestSuiteStartedEvent event = new TestSuiteStartedEvent(uid, story.getPath());
        event.withLabels(AllureModelUtils.createTestFrameworkLabel("JBehave"));
        event.withTitle(story.getName());
        getLifecycle().fire(event);
    }

    @Override
    public void afterStory(boolean givenStory) {
        getLifecycle().fire(new TestSuiteFinishedEvent(uid));
    }

    @Override
    public void narrative(Narrative narrative) {

    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {

    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {

    }

    @Override
    public void beforeScenario(String scenarioTitle) {
        getLifecycle().fire(new TestCaseStartedEvent(uid, scenarioTitle));
        getLifecycle().fire(new ClearStepStorageEvent());
    }

    @Override
    public void scenarioMeta(Meta meta) {

    }

    @Override
    public void afterScenario() {
        getLifecycle().fire(new TestCaseFinishedEvent());
    }

    @Override
    public void givenStories(GivenStories givenStories) {

    }

    @Override
    public void givenStories(List<String> storyPaths) {

    }

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {
        withExamples = true;
    }

    @Override
    public void example(final Map<String, String> tableRow) {
        final String rowName = tableRow.toString();
        if (withExamples) {
            getLifecycle().fire(new TestCaseEvent() {
                @Override
                public void process(TestCaseResult context) {
                    exampleName = context.getName();
                    String name = exampleName + " " + rowName;
                    context.setName(name);
                    context.setTitle(name);
                }
            });
        } else {
            String name = exampleName + " " + rowName;
            getLifecycle().fire(new TestCaseFinishedEvent());
            getLifecycle().fire(new TestCaseStartedEvent(uid, name).withTitle(name));
        }
        withExamples = false;
    }

    @Override
    public void afterExamples() {
        getLifecycle().fire(new TestCaseFinishedEvent());
    }

    @Override
    public void beforeStep(String step) {
        getLifecycle().fire(new StepStartedEvent(step).withTitle(step));
    }

    @Override
    public void successful(final String step) {
        updateStep(step);
        getLifecycle().fire(new StepFinishedEvent());
    }

    private void updateStep(final String step) {
        getLifecycle().fire(new StepEvent() {
            @Override
            public void process(Step context) {
                context.setName(step);
                context.setTitle(step);
            }
        });
    }

    @Override
    public void ignorable(String step) {
        getLifecycle().fire(new StepCanceledEvent());
    }

    @Override
    public void pending(String step) {
        getLifecycle().fire(new StepStartedEvent(step).withTitle(step));
        getLifecycle().fire(new StepCanceledEvent());
        getLifecycle().fire(new StepFinishedEvent());
        getLifecycle().fire(new TestCasePendingEvent().withMessage("PENDING: Step is not implemented yet!"));
    }

    @Override
    public void notPerformed(String step) {
        getLifecycle().fire(new StepStartedEvent(step).withTitle(step));
        getLifecycle().fire(new StepCanceledEvent());
        getLifecycle().fire(new StepFinishedEvent());
    }

    @Override
    public void failed(String step, Throwable cause) {
        updateStep(step);
        getLifecycle().fire(new StepFailureEvent().withThrowable(cause.getCause()));
        getLifecycle().fire(new StepFinishedEvent());
        getLifecycle().fire(new TestCaseFailureEvent().withThrowable(cause.getCause()));
    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {

    }

    @Override
    public void restarted(String step, Throwable cause) {

    }

    @Override
    public void dryRun() {

    }

    @Override
    public void pendingMethods(List<String> methods) {

    }

    private String generateSuiteUid(String suiteName) {
        String uid = UUID.randomUUID().toString();
        synchronized (getSuites()) {
            getSuites().put(suiteName, uid);
        }
        return uid;
    }

    private Allure getLifecycle() {
        return lifecycle;
    }

    private Map<String, String> getSuites() {
        return suites;
    }
}

