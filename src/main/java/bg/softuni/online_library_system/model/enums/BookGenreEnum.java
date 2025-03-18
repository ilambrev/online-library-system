package bg.softuni.online_library_system.model.enums;

public enum BookGenreEnum {
    ADVENTURE("Adventure"),
    ART("Art"),
    AUTOBIOGRAPHY("Autobiography"),
    BIOGRAPHY("Biography"),
    CLASSIC("Classic"),
    COMEDY("Comedy"),
    COMIC_BOOK("Comic book"),
    CRIME("Crime"),
    DYSTOPIA("Dystopia"),
    FAIRY_TALES("Fairy tales"),
    FANTASY("Fantasy"),
    FICTION("Fiction"),
    GAME_BOOK("Game book"),
    HISTORICAL_FICTION("Historical fiction"),
    HISTORICAL("Historical"),
    HOBBY("Hobby"),
    HORROR("Horror"),
    HUMOR("Humor"),
    MANGA("Manga"),
    MEDICAL("Medical"),
    MEMOIR("Memoir"),
    MYSTERY("Mystery"),
    NON_FICTION("Non fiction"),
    NOVEL("Novel"),
    PICTURE_BOOK("Picture book"),
    POETRY("Poetry"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science fiction"),
    SCIENCE("Science"),
    TECHNOLOGY("Technology"),
    THRILLER("Thriller");

    public final String label;

    BookGenreEnum(String label) {
        this.label = label;
    }
}