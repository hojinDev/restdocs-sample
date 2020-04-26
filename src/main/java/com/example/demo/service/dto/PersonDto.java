package com.example.demo.service.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Person;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonDto {

    @Setter
    @Getter
    public static class Create {

        @NotEmpty
        private String firstName;
        @NotEmpty
        private String lastName;
        @NotNull
        private Gender gender;
        @NotNull
        //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        private String hobby;

        public Person toEntity() {
            return Person.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .gender(gender)
                    .birthDate(birthDate)
                    .hobby(hobby)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Update {

        @NotEmpty
        private String firstName;
        @NotEmpty
        private String lastName;
        @NotNull
        //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        private String hobby;

        public Person apply(Person person) {
            return person.update(firstName, lastName, birthDate, hobby);
        }
    }

    @Getter
    public static class Response {

        private Long id;
        private String firstName;
        private String lastName;
        private long age;
        private Gender gender;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private String hobby;

        @Builder
        private Response(Long id, String firstName, String lastName, Gender gender, LocalDate birthDate, String hobby) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
            this.gender = gender;
            this.birthDate = birthDate;
            this.hobby = hobby;
        }

        public static Response of(Person person) {
            return Response.builder()
                    .id(person.getId())
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .gender(person.getGender())
                    .birthDate(person.getBirthDate())
                    .hobby(person.getHobby())
                    .build();
        }
    }

    @Getter
    public static class ResponseOne {

        private Response person;

        public ResponseOne(Response person) {
            this.person = person;
        }
    }

    @Getter
    public static class ResponseList {

        private List<Response> persons;

        public ResponseList(List<Response> persons) {
            this.persons = persons;
        }
    }
}
