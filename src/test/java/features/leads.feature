Feature: Leads tests Intelligent sales

  @Leads @992314
  Scenario Outline: A user who wants to view information on a Leads Card
    Given I'm a signed-in user "<browser>"
    When I view a Leads card in either the My Region: List: All or My Region: List: My Priorities tabs
    Then I see these field values on the card: Lead type (Prospect or Customer), Company Name, Address, Next Meeting, Composite Score (Prospect only), Contract Size (Customer only), Distance from User (based on map integration & GPS location)
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |


  @Leads @992314
  Scenario Outline: A user who wants to select a Lead as a Priority
    Given I'm a signed-in user "<browser>"
    When I select a star icon from a Leads card
    Then I see that Leads Card under the My Region: My Priorities tab
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |

  @Leads @992314
  Scenario Outline: A user who wants to deselect Leads from the 'My Priorities' page.
    Given I'm a signed-in user "<browser>"
    Then select My Priorities lead cards
    When I deselect a star icon
    Then that Lead card is removed from the My Priorities page.
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |

  @Leads @992314
  Scenario Outline: A user who wants to deselect Leads all at once from the 'My Priorities' page.
    Given I'm a signed-in user "<browser>"
    Then selected My Priorities lead cards
    When I click on the Clear All button
    Then all associated Leads cards are removed from the My Priorities page.
    Examples:
      | browser |
      | Ipad    |
      | Iphone  |
      | Safari  |
      | Firefox |
      | Chrome  |