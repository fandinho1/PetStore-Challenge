# PetStore Apis

## Configuration
This automation was built using Java, Serenity BDD, RestAssured, Cucumber, Junit4 and Gradle.

## Test Cases

### Basic CRUD:

- Get pet
- Add pet
- Update pet
- Delete pet

### Special scenarios for get pet

- Non Existing pet
- Empty id
- Negative id
- Float id
- Characters including special characters

## Explanation of solution implemented

I automated the basic CRUD due it is fundamental for any automation, for these scenarios I automated the happy path. 
Additionally I automated negative scenarios of the Get pet API to ensure the coverage of this API, since it is an exercise I didn't automate more scenarios.

Regarding the solution implemented, I used Scenarios Outlines to cover the most of the negative test cases and also in order to have a scalable and maintainable automation.
I also used classes as models to represent the json files. To manage these files I used Google's Gson library because I think it is easy to use and provide many useful methods.