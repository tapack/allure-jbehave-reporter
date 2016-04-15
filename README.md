[![release](http://github-release-version.herokuapp.com/github/tapack/allure-jbehave-reporter/release.svg?style=flat)](https://github.com/tapack/allure-jbehave-reporter/releases/latest) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.tapack/allure-jbehave-reporter/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/io.tapack/allure-jbehave-reporter)


# Allure JBehave Reporter
This reporter allows to generate allure xml reports after JBehave test execution. (Scenario -> Test)

Is other mapping:
  - Story -> Allure Test Suite
  - Scenario -> Allure Test Case
  - Step -> Allure Step
  - Scenario with Examples -> Allure Test Cases

## Usage
Simply add **allure-jbehave-reporter** as dependency to your project and add it to your JBehave configuration.

### Example
```java
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
                        .withReporters(new AllureJBehaveReporter()));
    }

}
```