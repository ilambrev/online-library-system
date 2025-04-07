package bg.softuni.online_library_system.testutils;

import bg.softuni.online_library_system.model.entity.BookGenreEntity;
import bg.softuni.online_library_system.model.entity.MostReadBookEntity;
import bg.softuni.online_library_system.model.entity.UserEntity;
import bg.softuni.online_library_system.model.entity.UserRoleEntity;
import bg.softuni.online_library_system.model.enums.BookGenreEnum;
import bg.softuni.online_library_system.model.enums.GenderEnum;
import bg.softuni.online_library_system.model.enums.UserRoleEnum;
import bg.softuni.online_library_system.repository.BookGenreRepository;
import bg.softuni.online_library_system.repository.MostReadBookRepository;
import bg.softuni.online_library_system.repository.UserRepository;
import bg.softuni.online_library_system.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static bg.softuni.online_library_system.common.constant.UserRoleDescriptionConstants.*;

//@Component
public class TestInitDB implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BookGenreRepository bookGenreRepository;
    private final MostReadBookRepository mostReadBookRepository;
    private final PasswordEncoder passwordEncoder;

//    @Autowired
    public TestInitDB(UserRepository userRepository, UserRoleRepository userRoleRepository,
                      BookGenreRepository bookGenreRepository, MostReadBookRepository mostReadBookRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.mostReadBookRepository = mostReadBookRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (this.userRoleRepository.count() == 0) {
            List<UserRoleEntity> roles = Arrays.stream(UserRoleEnum.values())
                    .map(UserRoleEntity::new)
                    .toList();
            roles.get(0).setDescription(USER_ROLE_DESCRIPTION);
            roles.get(1).setDescription(STAFF_ROLE_DESCRIPTION);
            roles.get(2).setDescription(ADMIN_ROLE_DESCRIPTION);

            this.userRoleRepository.saveAll(roles);
        }

        if (this.userRepository.count() == 0) {
            UserRoleEntity userRole = this.userRoleRepository.findByRole(UserRoleEnum.USER);
            UserEntity administrator = new UserEntity()
                    .setFirstName("Dave")
                    .setLastName("Lombardo")
                    .setUsername("lombardo")
                    .setPassword(this.passwordEncoder.encode("1234567890"))
                    .setEmail("lombardo@test.com")
                    .setPhoneNumber("+359555111222")
                    .setAddress("Somewhere")
                    .setImageURL("https://someimage.com")
                    .setGender(GenderEnum.MALE)
                    .setRole(userRole)
                    .setActive(true);

            this.userRepository.save(administrator);
        }

        if (this.bookGenreRepository.count() == 0) {
            String[] descriptions = new String[]{
                    "These books focus on exciting and often dangerous journeys, where the protagonist faces challenges and overcomes obstacles in unknown or extraordinary places.",
                    "Books in this genre explore the world of visual, performing, and fine arts, providing insight into artistic movements, techniques, and famous artists.",
                    "An autobiography is a self-written account of a person's life, where the author reflects on their experiences, struggles, and achievements.",
                    "A biography tells the life story of a real person, detailing their experiences, contributions, and challenges, often written by someone else.",
                    "Classic books are timeless works of literature that have been recognized for their lasting cultural significance and literary merit.",
                    "These books are designed to entertain and make readers laugh, often featuring humorous situations, witty dialogue, and funny characters.",
                    "Comic books are a blend of illustrations and text that tell a story, typically focusing on superheroes, adventure, or fantasy themes.",
                    "Crime books focus on the investigation and resolution of criminal cases, often with detectives or law enforcement officials solving mysteries.",
                    "Dystopian books depict fictional societies, often set in a bleak or oppressive future, exploring themes of government control, societal collapse, and loss of freedom.",
                    "Fairy tales are magical, often fantastical stories, typically featuring mythical creatures, enchanted beings, and moral lessons.",
                    "Fantasy books transport readers to imaginary worlds filled with magic, mythical creatures, and heroic quests, often with a battle between good and evil.",
                    "Fictional books are works of storytelling that originate from the imagination, not based on real events, featuring made-up characters and plots.",
                    "Game books allow readers to make decisions that influence the outcome of the story, combining narrative with interactive gameplay elements.",
                    "Historical fiction blends real historical events with fictional characters or stories, providing a narrative set in a specific time period.",
                    "Historical books focus on real events, figures, or time periods, offering in-depth analysis and accounts of the past.",
                    "These books focus on various recreational activities, such as gardening, crafting, or photography, offering advice, techniques, and inspiration.",
                    "Horror books are designed to evoke fear and suspense, often featuring supernatural creatures, monsters, or psychological terror.",
                    "Humor books provide entertainment through witty writing, satire, and jokes, aiming to amuse readers with lighthearted content.",
                    "Manga refers to Japanese comic books or graphic novels, often featuring anime-style artwork, with genres spanning from romance to action and fantasy.",
                    "Medical books provide information about health, disease, treatments, and advancements in medicine, often aimed at professionals or students in the field.",
                    "A memoir is a personal account of specific moments or periods in someone's life, often focusing on emotional experiences or significant events.",
                    "Mystery books revolve around the solving of a crime or puzzle, often featuring detectives or amateur sleuths working to uncover hidden truths.",
                    "Non-fiction books present factual information about real events, people, or topics, including subjects like history, science, and politics.",
                    "Novels are long fictional stories that explore complex characters, themes, and plots, providing an immersive narrative experience.",
                    "Picture books are designed for young readers, often featuring illustrations alongside short text, telling stories or teaching lessons.",
                    "Poetry books contain collections of poems, often exploring themes like love, nature, and personal reflection through rhythmic and figurative language.",
                    "Romance books focus on love stories, exploring relationships, emotions, and the dynamics between romantic partners.",
                    "Science fiction explores futuristic or speculative concepts, including space travel, advanced technology, and alternative realities, often with a scientific basis.",
                    "Science books provide knowledge about the natural world, including fields like biology, physics, astronomy, and environmental studies.",
                    "Books in the technology genre focus on the development, impact, and future of technological advancements, from computing to artificial intelligence.",
                    "Thriller books are fast-paced, suspenseful, and often intense, keeping readers on the edge of their seat with unexpected twists and high stakes."
            };

            List<BookGenreEntity> genres = IntStream.range(0, BookGenreEnum.values().length)
                    .mapToObj(i -> new BookGenreEntity()
                            .setGenre(BookGenreEnum.values()[i]).setDescription(descriptions[i]))
                    .toList();

            this.bookGenreRepository.saveAll(genres);
        }

        if (this.mostReadBookRepository.count() == 0) {
            List<MostReadBookEntity> mostReadBooks = new ArrayList<>();

            for (int i = 1; i <= 3; i++) {
                MostReadBookEntity mostReadBook = new MostReadBookEntity()
                        .setBookId(0L)
                        .setTitle("Thinking in Java")
                        .setImageURL("/images/sample_book.png")
                        .setBorrowCounter(i)
                        .setAuthorId(0L)
                        .setAuthorFirstName("Bruce")
                        .setAuthorLastName("Eckel");
                mostReadBooks.add(mostReadBook);
            }

            this.mostReadBookRepository.saveAll(mostReadBooks);
        }
    }
}