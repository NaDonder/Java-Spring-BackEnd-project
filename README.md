# Java-Spring-BackEnd-project
Spring boot java based frontend Coupon Project

Information description:
A Coupon System gives ability for Companies to create coupons
The system has Customer as well, customers can purchase coupons.
The coupons are limited by Amount and End Date and every customer can buy 1 coupon of the same type.
The system store the coupon bought by every customer
The income of the system is by customer buying coupons and from the creation of the coupons by the company.
The afforities divide to 3 types of clients:
1. Administrator - Managing the Company and Customer List.
2. Company - Managing the Coupon List that belong to the company.
3. Customer - purchase of coupons.

The System Uses Spring Framework.
Using: Hibernate, Spring, JPA and MySQL driver.

Packages divided by:
Beans - the seed for the class.
Repositories - using JPA Repository for each seed.
Services - Method building.
Facade - Business Login Thinking.
Login - Singleton: for defining which clientType are you,
gives you the right Service while checking Login method in each clientType.
Exceptions - for Exception thrower.
Thread - daily job for deleting coupons out of date.
JWT - token for authorization.
Controller - RESTful API.
Advice - for REST exception thrower.
Configuration - Swagger and Cors Configuration.
CLR - Command Line Runner - for tests/Mock Data.
Util - for Code in colors.

JAVADOCS avilable to download
