Feature: Test Intelligent sales for accessibility

  Scenario Outline: Run all automated accessibility tests.
    Given the web page "<url>" is displayed
    When I scan for accessibility concerns
    Then no accessibility issues should be found
    Examples:
      |url|
      |https://kc-pep-is-dev.web.app/|
      |https://app.starbucks.com/|
      |https://2048game.com/|
