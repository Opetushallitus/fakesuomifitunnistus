package fi.vm.sade.fakesuomifitunnistus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

public class PersonDto {

    @JsonProperty("sukunimi")
    public String surname;
    @JsonProperty("etunimet")
    public String firstNames;
    @JsonProperty("kutsumanimi")
    public String preferredFirstName;

    public String getGivenName() {
        return Optional.ofNullable(preferredFirstName)
                .map(String::trim).filter(not(String::isEmpty))
                .or(() -> Optional.ofNullable(firstNames).flatMap(names -> Arrays.stream(names.split(" "))
                        .map(String::trim).filter(not(String::isEmpty))
                        .findFirst()))
                .orElse(null);
    }

    public String getCommonName() {
        return Stream.of(surname, firstNames)
                .filter(Objects::nonNull).map(String::trim).filter(not(String::isEmpty))
                .collect(joining(" "));
    }

    public String getDisplayName() {
        return Stream.of(getGivenName(), surname)
                .filter(Objects::nonNull).map(String::trim).filter(not(String::isEmpty))
                .collect(joining(" "));
    }

}
