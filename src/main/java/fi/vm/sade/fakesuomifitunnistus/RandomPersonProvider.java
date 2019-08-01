package fi.vm.sade.fakesuomifitunnistus;

import org.springframework.stereotype.Component;

@Component
public class RandomPersonProvider implements PersonProvider {

    @Override
    public PersonDto get() {
        PersonDto person = new PersonDto();
        person.surname = "";
        person.firstNames = "";
        person.preferredFirstName = "";
        return person;
    }

}
