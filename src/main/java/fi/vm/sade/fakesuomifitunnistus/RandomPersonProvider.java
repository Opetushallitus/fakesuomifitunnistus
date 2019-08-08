package fi.vm.sade.fakesuomifitunnistus;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RandomPersonProvider implements PersonProvider {

    private final Faker faker;

    public RandomPersonProvider() {
        this.faker = new Faker(new Locale("fi-FI"));
    }

    @Override
    public PersonDto get() {
        PersonDto person = new PersonDto();
        person.surname = faker.name().lastName();
        person.firstNames = faker.name().firstName();
        person.preferredFirstName = person.firstNames;
        return person;
    }

}
