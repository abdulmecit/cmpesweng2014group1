<img src='https://dl.dropboxusercontent.com/s/g5x4dr8fpu53cw6/Diagram1.png'>

<h1>Use Case #1</h1>
<ul><li><b>Name:</b> Register<br>
</li><li><b>Actors:</b> Guests<br>
</li><li><b>Goal:</b> Creating an account<br>
</li><li><b>Preconditions:</b>
<ol><li>User shall have a valid e-mail address and username which is not registered before.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will enter username, e-mail address and password.<br>
</li><li>System will send an e-mail to the user for the confirmation.<br>
</li><li>User will verify the e-mail address.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>System will create a profile page for the user.<br>
<h1>Use Case #2</h1>
</li></ol></li><li><b>Name:</b> Log in<br>
</li><li><b>Actors:</b> Registered Users<br>
</li><li><b>Goal:</b> To access the account.<br>
</li><li><b>Preconditions:</b>
<ol><li>User must have an active account in the system.<br>
</li><li>User shall provide correct username and password.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will enter his/her username and password.<br>
</li><li>System will check the information provided by the user.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>User will login to the system and will be directed to his/her homepage if the username password combination is correct.<br>
</li><li>User will be informed about the wrong combination if the username password combination is not correct.<br>
<h1>Use Case #3</h1>
</li></ol></li><li><b>Name:</b> Obtain New Password<br>
</li><li><b>Actors:</b> Registered Users<br>
</li><li><b>Goal:</b> To retrieve a new password.<br>
</li><li><b>Preconditions:</b>
<ol><li>User must have an active account in the system.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will choose “forgot password” option.<br>
</li><li>User will be directed to the “forgot password” page.<br>
</li><li>User will enter his/her e-mail address.<br>
</li><li>System will check if the e-mail address belongs to a registered user.<br>
</li><li>System will send an e-mail to the user including the new password creating page link.<br>
</li><li>User will go to the link and enter new password.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>The password of the user will be updated in the system.<br>
</li><li>User will be informed about this change with an e-mail.<br>
<h1>Use Case #4</h1>
</li></ol></li><li><b>Name:</b> Add recipe<br>
</li><li><b>Actors:</b> Registered Users.<br>
</li><li><b>Goal:</b> To share a new recipe with the system.<br>
</li><li><b>Preconditions:</b>
<ol><li>User must login to the system.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will go to the “add recipe” section.<br>
</li><li>User will choose a name for the recipe.<br>
</li><li>User will choose a category for the recipe.<br>
</li><li>User will add ingredients separately, and user will enter the amount for that ingredient.<br>
</li><li>User will add text for the instructions, with pictures if he/she wants.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>System will store the ingredients used in the recipe in the database and add these ingredients as key words to recipe.<br>
</li><li>System will store the recipe.<br>
<h1>Use Case #5</h1>
</li></ol></li><li><b>Name:</b> Add comment / Rate recipes<br>
</li><li><b>Actors:</b> Registered Users.<br>
</li><li><b>Goal:</b> To add comments or rate the recipes.<br>
</li><li><b>Preconditions:</b>
<ol><li>User must login to the system.<br>
</li><li>There have to be at least one recipe in the system.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will open the recipe which he/she wants to add comment or rate.<br>
</li><li>User will add comment using “add comment” option.<br>
</li><li>User will rate the recipe in terms of health, taste, ease, cost.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>System will show the comment or rate under the recipe.<br>
<h1>Use Case #6</h1>
</li></ol></li><li><b>Name:</b> Follow a friend<br>
</li><li><b>Actors:</b> Registered Users.<br>
</li><li><b>Goal:</b> To add a user to friend list.<br>
</li><li><b>Preconditions:</b>
<ol><li>User must login to the system.<br>
</li><li>There have to be at least one other registered user in the system.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will go to the other user’s profile.<br>
</li><li>User will choose “follow this user” option in the page.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>System will show “follower request” page to the other user.<br>
</li><li>User will choose accept or decline option.<br>
<h1>Use Case #7</h1>
</li></ol></li><li><b>Name:</b> Delete account<br>
</li><li><b>Actors:</b> Registered Users.<br>
</li><li><b>Goal:</b> To delete account in the system.<br>
</li><li><b>Preconditions:</b>
<ol><li>User must login to the system.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User will go to the settings page.<br>
</li><li>User will choose “security” option.<br>
</li><li>User will choose "delete my account" option.<br>
</li><li>User will answer the question "Why do you want to delete your account?".<br>
</li><li>User will choose "I want to delete my account" option.<br>
</li><li>User will get an e-mail regarding to this, and he/she will go to the link in the e-mail.<br>
</li><li>User will choose "delete" option.<br>
</li></ol></li><li><b>Postconditions:</b>
<ol><li>System will delete the account and related information of that user from the database.<br>
</li><li>A notification e-mail will be sent to the user.<br>
<h1>Use Case #8</h1>
</li></ol></li><li><b>Name:</b> Like/Dislike/Report a Comment<br>
</li><li><b>Actors:</b> Registered User<br>
</li><li><b>Goal:</b> Suggesting opinion about a comment<br>
</li><li><b>Preconditions:</b>
<ol><li>User must be logged in to the system.<br>
</li><li>There should be at least one comment under a recipe page.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User reads the comment that he or she wants to suggest his or her opinion.<br>
</li><li>User clicks like, dislike or report button according his or her opinion.<br>
</li></ol></li><li><b>Post conditions:</b>
<ol><li>The user that posts the comment is notified if his or her comment is liked or disliked.<br>
</li><li>Comments that are reported too many times are made invisible by the system.<br>
<h1>Use Case #9</h1>
</li></ol></li><li><b>Name:</b> Basic Search for a Recipe<br>
</li><li><b>Actors:</b> Guest/User<br>
</li><li><b>Goal:</b>
</li><li><b>Preconditions:</b>
<ol><li>System should provide a search text box which can be accessible from every pages of the site for guest or user to enter the keywords.<br>
</li><li>System should have an algorithm that searches database for given query and return the results in a decending order according to the ratings.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User or guest enters the keywords in the search text box.<br>
</li><li>User or guest clicks the search button.<br>
</li><li>User or guest selects a recipe.<br>
</li></ol></li><li><b>Post Conditions:</b>
<ol><li>System should add the search options to user’s historical data to offer him more accurate recipes for random search option.<br>
<h1>Use Case #10</h1>
</li></ol></li><li><b>Name:</b> Advanced Search for a Recipe<br>
</li><li><b>Actors:</b> Registered User<br>
</li><li><b>Goal:</b> Finding a recipe with more specific properties<br>
</li><li><b>Preconditions:</b>
<ol><li>System should offer an advance search option where registered user can decide the properties of the recipes (type of ingredient, cuisine, rating types such as health, diet, taste).<br>
</li><li>System should have an algorithm that searches database for given queries and return the results in a decending order according to the ratings.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>Registered User decides the properties in advance search as he or she likes.<br>
</li><li>User or guest clicks the search button.<br>
</li><li>User or guest selects a recipe.<br>
</li></ol></li><li><b>Post Conditions:</b>
<ol><li>System should add the search options to user’s historical data to offer him more accurate recipes for random search option.<br>
<h1>Use Case #11</h1>
</li></ol></li><li><b>Name:</b> Pick of the Day(Random Search)<br>
</li><li><b>Actors:</b> Registered User<br>
</li><li><b>Goal:</b> Getting a random recipe according to user’s preferences and historical data<br>
</li><li><b>Preconditions:</b>
<ol><li>There should be a random search button in user’s homepage.<br>
</li><li>System should have been acquiring the historical data of the user.<br>
</li><li>System should store user’s preferences.<br>
</li><li>System should have an algorithm that returns a random recipe according to user’s preferences and historical data.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User clicks the random search button.<br>
</li><li>System returns a recipe.<br>
</li><li>User enters the recipe by clicking on it or asks for another one by clicking next button.<br>
</li><li>User may give a reason for not choosing the returned recipe.<br>
</li></ol></li><li><b>Post Conditions:</b>
<ol><li>System adds the selected recipe to user’s historical data.<br>
</li><li>System updates user’s historical data according to the reasons given by the user for not choosing the returned recipe or runs an algorithm to understand user’s eating habits from the common properties of user’s not-prefered-recipes.<br>
<h1>Use Case #12</h1>
</li></ol></li><li><b>Name:</b> Make Modification<br>
</li><li><b>Actors:</b> Registered User<br>
</li><li><b>Goal:</b> Changing or adding an ingredient of the recipe<br>
</li><li><b>Preconditions:</b>
<ol><li>System should provide an option to user to make modification to other user’s recipes.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User enters a recipe page.<br>
</li><li>User clicks the “make modification” button.<br>
</li><li>User makes modifications to the original recipe from the interface which is provided by the system. (Change the amount of an ingredient or replacing an ingredient, changing the description of the recipe, adding new photos, etc.)<br>
</li><li>User clicks “done” button.<br>
</li></ol></li><li><b>Post Conditions:</b>
<ol><li>Modified recipe is listed under the original recipe.<br>
<h1>Use Case #13</h1>
</li></ol></li><li><b>Name:</b> Profile Management<br>
</li><li><b>Actors:</b> Registered User<br>
</li><li><b>Goal:</b> Giving or modifying personal information and preferences<br>
</li><li><b>Preconditions:</b>
<ol><li>User must be a verified user.<br>
</li></ol></li><li><b>Steps:</b>
<ol><li>User enters his or her homepage.<br>
</li><li>User clicks the settings button.<br>
</li><li>User makes any modification he or she wants to his or her profile. (Linking social media accounts, adding profile picture, changing password, changing privacy settings, etc.)<br>
</li><li>Click the save changes button.<br>
</li></ol></li><li><b>Post Conditions:</b>
<ol><li>System updates user’s database and offers him or her recipes according to his or her preferences.