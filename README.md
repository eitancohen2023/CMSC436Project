![Oyster](/images/oysterBackground.jpg)

CMSC436 Final Project - Checkpoint
Authors: Michael Orlando, Jackson Styer, Eitan Cohen
Concept
The Oyster Recovery Partnership (ORP) sends trucks out through the region to collect oysters and dumps them in the bay at select oyster farming sites. Our app will help optimize collections times, routes, and advise ORP when a restaurant has a sufficient supply to warrant a pick up on an optimized route would be helpful.
Key Functionality
App users are either a truck driver or a restaurant. Registration interfaces are available for both types of users and the login UI is used by both truck drivers and restaurants.
The UI for truck drivers includes a screen that shows which restaurants are available for pickup. The app will also provide the most optimized route to all restaurants that are available and the closest oyster drop off point.
The interface for restaurants keeps a running tally of oyster shells that are available for pickup. When the restaurant's oyster shell count reaches the minimum threshold of 500 oyster shells, the app will mark the restaurant as ready for pickup. The restaurants will get a notification when a truck driver is on the way to pick up the shells.
Rough Architecture
Login screen - login screen for both truck drivers and restaurants
Register Truck Driver - put name and password
Register Restaurant - put name, password, and address
Truck Driver UI - Google Maps UI showing the path and make an intent to go to Waze
Restaurant UI - keep running tally of oyster shell count and include a button that indicates the restaurant has enough shells for a shell pickup.
Android System Components Needed for Implementation
Google Map API, Firebase, Intents, Activities, Permissions, Live data, Maps, Notifications, User interface classes, Fragment class





Storyboard
Our app begins with a login screen that both truck drivers and restaurants can use to login to the app. If there is a new user, then the user can click register to create a truck driver or restaurant account. The first image below and on the left shows the login screen, and the two other images show the registration screens for truck drivers and restaurants.










The image to the left shows the restaurant UI, which includes restaurant name, oyster shell count, buttons to increase or decrease count, a pickup button and a logout button. The restaurant user will track their oyster shell count and request to have their shells picked up once the oyster shell count reaches the minimum threshold.

The image to the right shows the truck driver UI, which shows a map of restaurants that are ready for oyster shell pick up. The user can choose a restaurant that is ready, and the app will provide the most optimal path to the closest oyster drop off location. The UI also includes buttons that will take the user to Apple maps or Waze, if preferred.
Team member evaluation for Michael Orlando, Eiten Cohen, and Jackson Styer: 
All team members have contributed an equal share so far for this final project. During the last 2 weeks, we met multiple times to discuss the project architecture and functionality, draw mockups and write this checkpoint report as a team.
