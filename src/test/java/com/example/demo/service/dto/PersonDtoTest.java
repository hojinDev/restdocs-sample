package com.example.demo.service.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Person;
import org.junit.Test;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonDtoTest {

    @Test
    public void created_toEntity() {

        //given
        PersonDto.Create create = new PersonDto.Create();
        create.setFirstName("호진");
        create.setLastName("이");
        create.setGender(Gender.MALE);
        create.setBirthDate(of(1983, 7, 4));

        //when
        Person person = create.toEntity();

        //then
        assertThat(person.getFirstName()).isEqualTo("호진");
        assertThat(person.getLastName()).isEqualTo("이");
        assertThat(person.getGender()).isEqualTo(Gender.MALE);
        assertThat(person.getBirthDate()).isEqualTo(of(1983, 7, 4));
    }

    @Test
    public void update_apply() {

        //given
        Person person = Person.builder()
                .firstName("호진")
                .lastName("이")
                .gender(Gender.MALE)
                .birthDate(of(1983, 7, 4))
                .build();

        //when
        PersonDto.Update update = new PersonDto.Update();
        update.setFirstName("호석");
        update.setLastName("이");
        update.setBirthDate(of(1985, 2, 1));

        update.apply(person);

        //then
        assertThat(person.getFirstName()).isEqualTo("호석");
        assertThat(person.getLastName()).isEqualTo("이");
        assertThat(person.getGender()).isEqualTo(Gender.MALE);
        assertThat(person.getBirthDate()).isEqualTo(of(1985, 2, 1));
    }

    @Test
    public void response_of() {

        //given
        Person person = Person.builder()
                .firstName("호진")
                .lastName("이")
                .gender(Gender.MALE)
                .birthDate(of(1983, 7, 4))
                .build();

        //when
        PersonDto.Response response = PersonDto.Response.of(person);

        //then
        assertThat(response.getAge()).isEqualTo(36L);
        assertThat(response.getFirstName()).isEqualTo("호진");
        assertThat(response.getLastName()).isEqualTo("이");
        assertThat(response.getGender()).isEqualTo(Gender.MALE);
        assertThat(response.getBirthDate()).isEqualTo(of(1983, 7, 4));
    }
}
