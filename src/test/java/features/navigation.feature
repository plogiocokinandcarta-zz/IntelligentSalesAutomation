Feature: Navigation tests Intelligent sales

  @Navigation @985309
  Scenario Outline: A user who navigates to the Hamburger Stack icon from home screen.

    Given I'm a signed-in user "<browser>"
    When I click on the hamburger stack menu icon
    Then a right swipe drawer appears with placeholder sections for User info, Profile,Terms of Use,Help,Logout
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |

  @Navigation @985309
  Scenario Outline: A user who wants to logout

    Given I'm a signed-in user "<browser>"
    When I want to logout of the App
    Then I navigate to the My Profile drawer where I can click on Logout towards the bottom of the menu.
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |

  @Navigation @992341
  Scenario Outline: A User who selects the hamburger stack menu.
    Given I'm a signed-in user "<browser>"
    When I click on the hamburger stack menu icon
    Then a navigation drawer swipes right to reveal the My Profile section.
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |

  @Navigation @992341
  Scenario Outline: A User who wants to view a toolbar
    Given I'm a signed-in user "<browser>"
    Then I want to see toolbar options to view My Profile section (Hamburger stack), Search, and Filter
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |