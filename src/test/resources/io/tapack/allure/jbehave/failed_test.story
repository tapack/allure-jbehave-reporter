Narrative:

    Display failed scenarios in report as tests
    Report should display results according to mapping bellow.

    Mapping:
        - Failed Step -> Allure - step failed, rest steps in scenario is canceled, test is failed
        - Error in Step -> Allure - step broken, rest steps in scenario is canceled, test is broken
        - Step is undefined -> Allure - step pending, rest steps in scenario is canceled, test is pending

Scenario: Calculate sum once and fail
Given the first number 2
And the second number 2
When I add them together
Then the sum is equal to 5

Scenario: Calculate sum of two digits and fail
Given the first number <first>
And the second number <second>
When I add them together
Then the sum is equal to <result>
Examples:
| first | second | result |
| 1     | 2      | 3      |
| 4     | 5      | 7      |
| 5     | 5      | 10     |

Scenario: Check number and throw Error
Given the first number 2
And the wrong number 2
When I add them together
Then the sum is equal to 4

Scenario: Check number with undefined steps
Given the first number 2
And the non-existing number N
When I add them together
Then the sum is equal to 4

Scenario: Calculate sum once again right
Given the first number 2
And the second number 2
When I add them together
Then the sum is equal to 4
