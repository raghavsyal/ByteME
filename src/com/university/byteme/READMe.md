### Login and Signup
    Hardcoded: 
    - 2 vip customers 
    - 1 regular customer 
    - 3 snacks and 2 beverages in menu
    - new customers can also sign up

### Collections Used
    - ArrayLists and TreeMap
    - ArrayLists for storing customers, reviews, orders, menu and TreeMap for mapping Items with Quantity in Cart and made the comparable as 
    Item (so whenever items are displayed wheter in menu or daily sales, they are shown in alphabetical order)

### Assumptions
    - to become a vip member, only asked on sign up whether want to become a vip by paying an amount for a year(subscription model types)

    - to handle duplicates and avoid confusion between customers, instead of getting customer name, using customer email as email ids 
    are unique.

    - two categories in menu, Snacks and Beverages, can search by item name, can search by category also.

    - while placing order, if an item is not available then it cannot be added in the menu, admin can change the availability of items, 
    once order placed and then the item is made unavailable then the order will not get cancelled as it was available while placing the order.
    
    - when customer views cart, if multiple quantities are there for an item then the price shown is quantity * price of one
    
    - after placing order, bill is displayed and order added to pendingOrders, customer can see the pendingOrders, once order 
    processed (by admin, gets added to completedOrders), now customer cannot see that order in pendingOrders but completedOrders.
    
    - no specific option to handle requests from admin, as when admin will see the pending orders then requests will be seen along with 
    order when customer places the order, request will be added along with it.
    
    - assumption vip/regular, vip's order will be given higher priority than regular eg pending order sequence : V1 R1 R2 V2 V3, 
    completed sequence V1 V2 V3 R1 R2
    
    - when customer views a cart, they can add a request there only, and admin can see the request when looking at the orders 
    instead of making another function to handle requests as it makes more sense to see the requests along with the order.

### When admin generates daily sales report, i have printed a table kind of thing in which quantity is also shown, something like this

| Item            | Quantity | Cost    |
|-----------------|----------|---------|
| Coffee          | 2        | 70      |
| Mojito          | 1        | 60      |
| Momos           | 2        | 120     |
| Samosa          | 3        | 60      |
| Spring Roll     | 7        | 350     |
| **Total Sales** |          | **660** |

**so most popular item can be seen from the table itself**  

#### Reviews are anonymous, customers can see all reviews and can also submit a review