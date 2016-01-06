# Requirement Analysis #

## 1. General Idea ##
This project is about creating a platform for both web and mobile which users can share their food recipes including its nutrition information and explore others'. Users can search recipes with ingredients they have and they can also suggest ingredient substitutions for recipes. People can select their personal preferences or diseases for limiting some recipes. For example, if they have diabetes, they won't see recipes with sugar or if they don't like tomatoes they won't see any recipe that includes tomato. Application will store users' history, like what they ate or liked and will use these information to recommend new recipes.

Web version of the application will be more to see the overview and specialization of users' taste. On the other hand, mobile application will serve user to see recipes quickly, decide what to cook and see what to pick up from market before going home.


## 2. Functional Requirements ##

  * ### 2.1 User Requirements ###
    * #### 2.1.1 Sign-up Requirements ####
      * 2.1.1.1 New users shall sign up by providing their username, e-mail address/facebook account and password to the system.
      * 2.1.1.2 Users should be able to decide privacy options.
    * #### 2.1.2 Profile Management ####
      * 2.1.2.1 Users shall sign in to the system by entering his/her username and password.
      * 2.1.2.2 Users should be able to create their own profiles which may contain their food intolerances, health issues, food preferences and their diets.
      * 2.1.2.3 Food intolerance option includes allergy, gluten intolerance, and lactose intolerance.
      * 2.1.2.4 Health issue option includes diabet, heart disease, cholesterol, and blood pressure.
      * 2.1.2.5 In food preference section, users should be able to add the foods they don’t eat.
      * 2.1.2.6 If a user chooses private option, all parts of their profile are set as private initially.
      * 2.1.2.7 Users should be able to change which part of their profile is private or public. These parts are health issues, food preferences, food intolerance, their diets, their activities (adding/sharing/liking/rating a recipe).
    * #### 2.1.3 Recipes ####
      * 2.1.3.1 Users should be able to add recipes which may contain text, photos.
      * 2.1.3.2 Users should be able to share their recipes and their dishes on Facebook.
      * 2.1.3.3 Users should be able to give a rate to recipes in terms of health, taste, ease, and cost.
      * 2.1.3.4 Users should be able to like or dislike recipes.
      * 2.1.3.5 Users should be able to show which recipes they have eaten by clicking eat button on the recipe page.
      * 2.1.3.6 Users should be able to assign semantic tags to their recipes, such as cuisines (Turkish, Italian, Argentinian ... etc.) or types (dessert, main dish, soup ... etc.) or ingredients (avocado, potato, meat, fish ... etc) or preferences (Vegan, Vegetarian, Meat lover, Cookie monster ... etc).
      * 2.1.3.7 Users should be able to search for recipes by giving queries or selecting some certain criteria (cuisines,type,vegan,must-have-ingredient) or selecting the type of rating. (health,taste,ease,cost)
      * 2.1.3.8 Users should be able to create new recipes by changing and/or adding an ingredient to other recipes. This kind of recipes are called derived recipes.
      * 2.1.3.9 Users should be able to post comments about others' recipes and like or dislike other users' comments.
      * 2.1.3.10 Users should be able to see which recipes their friends have rated, liked, or disliked.
    * #### 2.1.4 Gamification Requirements ####
      * 2.1.4.1 Users should be able to earn points by other users' liking their recipes, comments.
      * 2.1.4.2 Users should be able to earn points by ratings of their recipes. These points should depend on the score of the rating and how many users have voted.
      * 2.1.4.3 There should be achievement system that makes users to pursue the missions and make them more contributive about feeding the database. These achievements should give points to the users.(For example: Put there recipes, get 100 likes, at least 5 of your recipes should get 1000+ likes, etc)
      * 2.1.4.4 Users should have earn badges by completing the achievements and add one of them to their visible names as a tag.(For example: master chef badge for completing the achievement of getting 1000+ likes for at least 10 recipes or gourmet badge by getting 1000+ likes for comments and modifications.)
    * #### 2.1.5 Social Network ####
      * 2.1.5.1 Users should be able to follow each others' profiles and their activities such as adding recipe. If a user wants to follow a private user than the user should get the private user's consent.
      * 2.1.5.2 Users should be able to link their nutty account with their Facebook account if they want.
    * #### 2.1.6 Guest Access ####
      * 2.1.6.1 Unregistered users should be able to search for recipes and read them.
      * 2.1.6.2 Unregistered users should be able to see rating and comments of a recipe.
      * 2.1.6.3 Unregistered users should be able to see profiles of the users which are not private.

  * ### 2.2 System Requirements ###
    * #### 2.2.1 Accounting Requirements ####
      * 2.2.1.1 The system shall provide login options via Facebook and/or e-mail.
      * 2.2.1.2 The system shall provide help for users who forget their passwords.
      * 2.2.1.3 The system shall enable users to deactivate their accounts when they desire.
      * 2.2.1.4 The system shall enable users to create their profile.
      * 2.2.1.5 The system shall provide privacy setting options to the user.
    * #### 2.2.2 Posting and Searching Recipes ####
      * 2.2.2.1 The system shall enable users to follow some other users about their recipes, likes etc.
      * 2.2.2.2 The system shall provide a notification mechanism of events such as upgrading to the upper level or if their recipe got more than a particular number of likes.
      * 2.2.2.3 The system shall enable all users to comment on, rate or change an ingredient of other recipes that are derivable for them.
      * 2.2.2.4 The system shall provide "Search" option for recipes according to semantic tags and key words. Search results will be brought according to their ratings and likes. The recipe that has the highest like count will come up as the first.
      * 2.2.2.5 The system shall provide some achievements that users can get with their score they get from their recipes or comments.
    * #### 2.2.3 Derived Recipe ####
      * 2.2.3.1 In every recipe page there should be modify-the-recipe option.
      * 2.2.3.2 System should provide an interface for the user to create new recipes by using the existing recipe. (change and/or add ingredients, redesigning the text)
      * 2.2.3.3 Derived recipes should be able to be liked or rated by the users just like the recipes.
      * 2.2.3.4 System should allow users to see the original version of the derived recipes.
    * #### 2.2.4 Preventing the Abusive Usage ####
      * 2.2.4.1 The system should offer report options for users and recipes to report abusive posts.
      * 2.2.4.2 The system should set the users who are reported so many times as banned.
      * 2.2.4.3 The system should not offer the recipes that have so many reports in the search option.
    * #### 2.2.5 Acquiring the Historical Data ####
      * 2.2.5.1 The system should keep the record of which recipes the user have eaten.
      * 2.2.5.2 System should take all of the user's historical data into consider to offer the user the recipes that he or she likes more.
      * 2.2.5.3 System should offer the user the recipes that are different from recent cooked recipes not to bore him or her.
    * #### 2.2.6 Mobile Application ####
      * 2.2.6.1 The system will provide mobil application.
      * 2.2.6.2 The mobile app should offer every option that the web page does. It should have a cleaner interface and be very easy to use while on-the-move.
      * 2.2.6.3 Mobile app should have a short-cut photo taking property.

## 3. Non-Functional Requirements ##

  * ### 3.1. Availability and Platform Dependability ###
    * 3.1.1. Web version of the system shall be supported by Mozilla Firefox (v. 27+) and Google Chrome (v. 33+) on Windows, OS X and GNU/Linux, Internet Explorer (v. 11+) on Windows and Safari (v. 7+) on OS X.
    * 3.1.2. Mobile application of the system shall be supported by Android (v. 4.0+).

  * ### 3.2. Security ###
    * 3.2.1 User passwords shall be hashed using SHA-256 and salted before storing in the system.

  * ### 3.3. Scalability ###
    * 3.3.1. The system shall be able to store and retrieve one million of texts and images.
    * 3.3.2. The system shall be able to allow ten millions of users to view and create content.
    * 3.3.3. The system shall be designed according to the needs of an ever-growing international user community.

  * ### 3.4. Ease of use ###
    * 3.4.1. The user interface of the system shall be easy to use for people older than 13.
    * 3.4.2. Mobile application shall have a user interface which makes it easy to use on the go.

  * ### 3.5. Response Time ###
    * 3.5.1. Requests to web services shall be responsed in max. 5 seconds.
    * 3.5.2. Web pages shall be loaded in max. 5 seconds.
    * 3.5.3. Mobile application shall respond to a user in max. 5 seconds.

  * ### 3.6. Reliability ###
    * 3.6.1. Backups shall be scheduled frequently to make the system reliable and to avoid the loss of user generated content.

  * ### 3.7. Robustness ###
    * 3.7.1. The system shall be recoverable in at most 24 hours in any major problem.

  * ### 3.8. Modifiability ###
    * 3.8.1. The design of the system shall enable developers to modify the system according to the dynamic nature of a content generating community.

## 4. Dictionary ##

**Allergy:** A damaging immune response by the body to a substance, especially a particular food, pollen, fur, or dust, to which it has become hypersensitive.

**Anorexia:** Lack or loss of appetite for food (as a medical condition).

**Body mass index:** An approximate measure of whether someone is over- or underweight, calculated by dividing their weight in kilograms by the square of their height in metres.

**Calorie:** The energy needed to raise the temperature of 1 gram of water through 1 °C (now usually defined as 4.1868 joules).

**Carbohydrate:** one of several substances, such as sugar or starch, that provide the body with energy, or foods containing these substances such as bread, potatoes, pasta, and rice.

**Cholesterol:** A compound of the sterol type found in most body tissues. Cholesterol and its derivatives are important constituents of cell membranes and precursors of other steroid compounds, but high concentrations in the blood are thought to promote atherosclerosis.

**Cuisine:** A style of cooking.

**Diet:** A person who does not eat meat for health or religious reasons or because they want to avoid being cruel to animals.

**Dish:** A container, flatter than a bowl and sometimes with a lid, from which food can be served or which can be used for cooking.

**Fast food:** Easily prepared processed food served in snack bars and restaurants as a quick meal or to be taken away.

**Fat:** The substance under the skin of humans and animals that stores energy and keeps them warm.

**Ingredient:** A food that is used with other foods in the preparation of a particular dish.

**Junk food:** Pre-prepared or packaged food that has low nutritional value.

**Metabolism:** The chemical processes that occur within a living organism in order to maintain life.

**Nutrition:** The substances that you take into your body as food and the way that they influence your health.

**Obesity:** The state of being grossly fat or overweight.

**Protein:** One of the many substances found in food such as meat, cheese, fish, or eggs, that is necessary for the body to grow and be strong.

**Recipe:** A set of instructions telling you how to prepare and cook food, including a list of what food is needed for this.

**Vegetarian:** A person who does not eat meat for health or religious reasons or because they want to avoid being cruel to animals.

**Vitamin:** Any of a group of organic compounds which are essential for normal growth and nutrition and are required in small quantities in the diet because they cannot be synthesised by the body.