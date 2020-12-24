# a11y-selenium-axe

Accessibility testing using [axe-core Selenium (Java) Integration](https://github.com/dequelabs/axe-core-maven-html)

This project consists of 4 sample tests, which include a11y analysis for the following cases:

- Whole page
- Whole page by checking only specific accessibility tags (e.g. *wcag2a*)
- Part of page including and excluding elements
- Specific element

The reports are exported to JSON and TXT formats and can be found under [src/test/java/results/json](https://github.com/taniapil/a11y-selenium-axe/tree/main/src/test/java/results/json) and [src/test/java/results/txt](https://github.com/taniapil/a11y-selenium-axe/tree/main/src/test/java/results/txt) paths respectively.

In the JSON report there's a whole analysis for the page/elements under test, whereas the TXT report includes only the accessibility violations.