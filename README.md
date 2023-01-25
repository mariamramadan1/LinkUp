<pre>
<p align="center">
Mariam Ali 900191779
Nour Montasser 900191101
Rawan Hamad 900192388
</p>
</pre>
# Linkup: Connecting costs nothing

## What is Linkup?
Link up is an app that connects two types of users: clients and service providers. A Client searches for a service amongst defind categories and subcategories. A service provider offers their services and are rated and reviewed accordingly. This application was developed using android studio and java, and it only runs and compiles on android devices

## How to run
- clone the repo, it contains all the dependencies needed
- use android studio
- make sure linkup is selected in the configuration menu
- choose the device or the emulator you wish to run the app on from the AVD manager
- click run
- you can possibly use two devices: one for the clients view and the other for the service provider view

## Assumptions
- once the user is signed up they stay logged in
- The phone numbers are unique, a user cannot be a client and a service provider at the same time
- Requests between the same client and service provider are only limited to one per day
- when a service provider accept the request, it means a service has been provided, and hence the client can rate and review
- The categories and subcategories are predefined and cannot be changed or increased
- There is only one rating and review per request; However, it can be updated anytime.
- rating of the service provider is cummaltive, it is the total ratings divided by the number of services done.
- search is done by service provider's first name, last name, or category, but the name or the category has to be typed in full.

## Limitations
- profile pictures are static
- a page is not refreshed immediately on a database change, it might contain both old and new entries,
   unless we changed activities and go back.
- the profile info cannot be edited once the user signed up

## Future Recommendations
- chatting service to ease communication 
- service provider are able to add categories other than those already there
- notifications to instantly notify service provider when bookings are made, and clients when bookings are accepted
- allowing users to have profile pictures, and generally attaching photos in the app e.g. portfolio of previous work for service providers.

## Files' Structure and Activities' Description
- LogoScreen: splash screen on app start-up
- VerifyPhoneNumber: the activity where one enters their phone number and verification code
- AccountType: specify which user is signing up e.g. client or service provider
- SignUpClient: Sign up page for the client to fill personal info
- SignUpService: Sign up page for the service provider to fill personal info
- SignUpService2: Continuation for the service provider's sign up to fill profession details
- CustomGrid: Adapter that populates the Categories and subcategories menu
- Search: implements search for service provider by first name, last name, or category
- ServicesMenu: Home page for the client
- ProfessionMenu: subcategories menu based on the clicked category
- ProfileClient: Profile page of the client
- ProfileService: profile page of the service provider
- ProfileServiceClientView: profile page of the service provider from the client's view
- CustomWorkerAdapter: populates the list of workers in each subcategory
- CustomWorkerList: class that specifies the elements contained in each item of the workers' list page
- WorkerListPage: a page that contains a list of all service providers for a certain subcategory.
- ReviewPage: a page where a client rates and reviews a service provider after the request is accepted
- BookingsAdapter: Custom adapter that populates the booking's listview
- BookingsList: class that specifies the elements contained in each item of the booking's list
- BookingsClient: Booking page from the client's view
- HomeServiceProv: Home page for the service provider that contains recent ratings and reviews
- BookingService: Booking page from the Service Provider's view
- OfferPageService: contains all the details about a specific booking request for the service provider
- ReviewsAdapter: populates the reviews list in the Reviews page
- ReviewList: class that specifies the elements contained in each item of the Review's list
- Reviews: a page where a service provider can see his/her reviews from their clients


