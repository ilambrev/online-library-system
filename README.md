# Online Library System

![Screenshot](/assets/01-home-page.png)

## Project Overview
The **Online Library System** is a web-based application built with **Spring MVC** that allows users to browse authors and books, make reservation for borrowing books, manage their profiles. It provides an interactive and user-friendly experience for book enthusiasts.

---

## Features
- User registration and authentication.
- Each user has a role that can be changed by an administrator. There are three roles: USER(default after registration), STAFF and ADMIN.
- A user with an ADMIN role can change the roles of other users.
- A user with the STAFF role can add new authors and books, as well as manage book reservations and mark books as returned.
- Profile management (update personal info & profile picture).
- Users can browse the list of available authors and books, as well as view details about books and authors. They can reserve books for borrowing, with each user being allowed to borrow or reserve up to 3 books in total.
- Each user has a cart for reserving books. When a user's session expires, books for reservation (but not confirmed) remaining in the cart are deleted, as these books become available again.
- The borrow period for books is 14 days. The system monitors the expiration date and displays a list of overdue books when the user logs in.
- Every hour Scheduler marked as expired the reservations that were made more than 24 hours ago.
- The top 3 most borrowed books are displayed on the main page. The list is updated by Sheduler at 00:00 every day.

---

## Technologies Used
- **Backend**: Java, Spring MVC, Spring Security, MySQL
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Database**: MySQL
- **Image Uploads**: Cloudinary API
- **Additional Libraries**: Gson, ModelMapper
---

## EER Diagram

![Screenshot](/assets/02-eer-diagram.png)

## API Endpoints
| Endpoint                           | Method | Description                                                |
|------------------------------------|--------|------------------------------------------------------------|
| `/api/authors/all`                 | GET    | View all authors                                           |
| `/api/authors/first-name/{letter}` | GET    | View all authors by first letter of first name (uppercase) |
| `/api/authors/last-name/{letter}`  | GET    | View all authors by first letter of last name (uppercase)  |
| `/api/publishers/all`              | GET    | View all publishers                                        |

---

## Initial ADMIN account
- username: admin
- password: my&1Secret

---

## Contributing
Feel free to fork this repository, open an issue, or submit a pull request if you have any suggestions or improvements!

---

## License
This project is licensed under the MIT License.
