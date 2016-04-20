Narrative:

    Display passed scenarios in report as tests
    Report should display results according to mapping bellow.

    Mapping:
        - Story -> Allure Test Suite
        - Scenario -> Allure Test Case
        - Step -> Allure Step
        - Scenario with Examples -> Allure Test Cases

GivenStories: io/tapack/allure/precondition.story

Scenario: Calculate sum once
Given the second number 2
When I add them together
Then the sum is equal to 4

Scenario: Calculate sum of two digits
Given the first number <first>
And the second number <second>
When I add them together
Then the sum is equal to <result>
Examples:
| first | second | result |
| 1     | 2      | 3      |
| 4     | 5      | 9      |
| 5     | 5      | 10     |