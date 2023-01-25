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

## Limitations
- profile pictures are static
- a page is not refreshed immediately on a database change, it might contain both old and new entries,
   unless we changed activities and go back.

## Future Recommendations
- chatting service to ease communication 
- service provider are able to add categories other than those already there
- notifications to instantly notify service provider when bookings are made, and clients when bookings are accepted
- allowing users to have profile pictures, and generally attaching photos in the app e.g. portfolio of previous work for service providers.


