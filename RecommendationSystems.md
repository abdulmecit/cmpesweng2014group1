<img src='http://aniav.github.io/circus/img/netflix-recommendations.jpg' width='425' />

# Definition and History #

_Recommendation_ or _recommender_ systems are information filtering systems which try to predict user behavior in respect to an item. This could be rating a product or playing a particular song. This systems are at the core of companies like Netflix and Amazon. Netflix estimates that 75 percent of their viewer activity is driven by their recommendation system. In 2009, they organized a competition called Netflix Prize and paid $1M to a team for just a 10% improvement.

_Information filtering_ was originated during 50's with the rise of AI (artificial intelligence) research. In 1994, Pattie Maes, a MIT researcher coined the term _information overload_ and described some AI systems which can act like a recommendation system to overcome this problem. Her method, _Memory-Based Reasoning_ was based on past actions of user and with this approach she created a product at her start-up Firefly, which was later sold to Microsoft.

Later GroupLens and Tapestry systems were developed and became one of the first examples of recommendation systems with their _collaborative filtering_ techniques.

Today, from Spotify to Amazon, companies try to maximize their revenue using recommendation systems at their core with a hybrid of following two main techniques.

# Collaborative Filtering #

Collecting a vast amount of data about user activities, preferences and interactions recommendation systems are able to predict future user behavior without having a knowledge about the items themselves. This approach called _collaborative filtering_.

The most important aspect of this approach is whether the items are movies, songs or shoes, the system doesn't need any information about their properties and could be implemented easily.

# Content-based Filtering #

Compared to collaborative filtering, this method seeks to extract the properties of items and find similar ones to the items a user liked previously. Information about items themselves and past user actions are used to create a user profile. For example, Pandora uses the [Music Genome Project](http://en.wikipedia.org/wiki/Music_Genome_Project) and recommends new songs based on properties of previously played songs and their artists.


# References #

  * http://pages.cs.wisc.edu/~beechung/icml11-tutorial/<br>
<ul><li><a href='http://www.wired.com/underwire/2013/08/qq_netflix-algorithm/'>http://www.wired.com/underwire/2013/08/qq_netflix-algorithm/</a><br>
</li><li><a href='http://www.netflixprize.com/leaderboard'>http://www.netflixprize.com/leaderboard</a><br>
</li><li><a href='http://en.wikipedia.org/wiki/Recommender_system'>http://en.wikipedia.org/wiki/Recommender_system</a><br>
</li><li><a href='http://en.wikipedia.org/wiki/Music_Genome_Project'>http://en.wikipedia.org/wiki/Music_Genome_Project</a><br>