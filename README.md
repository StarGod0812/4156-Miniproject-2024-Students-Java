# 4156 Individual Project 1

## Ruoxing Liao(rl3392)

This project includes the implementation of various Java classes and uses PMD for static code analysis.

## Running PMD

To run PMD and analyze the source code, use the following command:

```bash
pmd check -d /Users/liaoruoxing/IdeaProjects/4156-Miniproject-2024-Students-Java -R rulesets/java/quickstart.xml -f text
```

This command will analyze the source code in the specified directory and generate a report with any issues found.

## Bug Finder Results

The following bugs were identified and fixed during the development of this project:

- **UnnecessaryAnnotationValueElement**
  - Files: `RouteController.java`
  - Lines: 275, 276, 308, 335, 364, 365, 404, 405, 406, 441, 442, 443, 479, 480, 481
  - Description: Avoid the use of value in annotations when it's the only element.

- **LooseCoupling**
  - Files: `RouteController.java`
  - Lines: 282, 284, 312, 339, 371, 373, 412, 414, 449, 451, 487, 489
  - Description: Avoid using implementation types like 'HashMap'; use the interface instead.

- **UnusedLocalVariable**
  - Files: `RouteController.java`
  - Line: 287
  - Description: Avoid unused local variables such as 'requestedCourse'.

## Additional Warnings

- **LineLength**
  - Files: `Department.java`, `IndividualProjectApplication.java`, `RouteController.java`
  - Description: Line has more than 100 characters.

- **CommentsIndentation**
  - Files: `IndividualProjectApplication.java`
  - Lines: 181, 212, 243, 276
  - Description: Comments should be indented like the code they are describing.

- **LeftCurly**
  - Files: `IndividualProjectApplication.java`
  - Line: 278
  - Description: '{' should be on the next line.

- **OneStatementPerLine**
  - Files: `IndividualProjectApplication.java`
  - Line: 278
  - Description: Prohibit more than one statement per line.

- **RightCurlySame**
  - Files: `IndividualProjectApplication.java`
  - Line: 278
  - Description: '}' should be on the same line as the next code block.

- **RightCurlyAlone**
  - Files: `IndividualProjectApplication.java`
  - Line: 278
  - Description: '}' should be alone on a line.

- **EmptyLineSeparator**
  - Files: `IndividualProjectApplication.java`
  - Line: 280
  - Description: There should be an empty line before "VARIABLE_DEF".

## License

This project is open-source and available under the MIT License.

## Contact

For any questions or further information, please contact Ruoxing Liao at rl3392@columbia.edu
```

