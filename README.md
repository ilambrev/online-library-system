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

## Screenshots

Screenshots were taken at Full HD resolution (1920 x 1080)

![Screenshot](/assets/04-home-page-user.png)

![Screenshot](/assets/05-profile-menu.png)

![Screenshot](/assets/06-authors.png)

![Screenshot](/assets/07-author-about.png)

![Screenshot](/assets/08-books.png)

![Screenshot](/assets/09-book-about-unavailable.png)

![Screenshot](/assets/10-book-about-available.png)

![Screenshot](/assets/11-book-reserved.png)

![Screenshot](/assets/12-user-cart.png)

![Screenshot](/assets/13-user-profile.png)

![Screenshot](/assets/14-user-profile-edit.png)

![Screenshot](/assets/15-user-change-password-form.png)

![Screenshot](/assets/16-user-reserved-books.png)

![Screenshot](/assets/17-abot-library.png)

![Screenshot](/assets/18-contacts.png)

![Screenshot](/assets/19-staff-home-page.png)

![Screenshot](/assets/20-staff-home-page.png)

![Screenshot](/assets/21-staff-add-author.png)

![Screenshot](/assets/22-staff-add-book.png)

![Screenshot](/assets/23-staff-confirm-book-reservation.png)

![Screenshot](/assets/24-user-borrowed-books.png)

![Screenshot](/assets/25-staff-book-return.png)

![Screenshot](/assets/26-admin-home-page.png)

![Screenshot](/assets/27-admin-users.png)

![Screenshot](/assets/28-admin-user-profile.png)

![Screenshot](/assets/29-admin-user-profile-edit.png)

---

## Test Coverage

![Screenshot](/assets/03-test-coverage.png)

---

## Contributing
Feel free to fork this repository, open an issue, or submit a pull request if you have any suggestions or improvements!

---

## License
This project is licensed under the MIT License.
