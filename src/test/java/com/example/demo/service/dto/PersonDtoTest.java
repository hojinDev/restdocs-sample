package com.example.demo.service.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Person;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersonDtoTest {

    @Test
    public void created_toEntity() {

        //given
        PersonDto.Create create = new PersonDto.Create();
        create.setFirstName("호진");
        create.setLastName("이");
        create.setGender(Gender.MALE);
        create.setBirthDate(LocalDate.of(1983, 7, 4));

        //when
        Person person = create.toEntity();

        //then
        assertThat(person.getFirstName(), is("호진"));
        assertThat(person.getLastName(), is("이"));
        assertThat(person.getGender(), is(Gender.MALE));
        assertThat(person.getBirthDate(), is(LocalDate.of(1983, 7, 4)));
    }

    @Test
    public void update_apply() {

        //given
        Person person = Person.builder()
                .firstName("호진")
                .lastName("이")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1983, 7, 4))
                .build();

        //when
        PersonDto.Update update = new PersonDto.Update();
        update.setFirstName("호석");
        update.setLastName("이");
        update.setBirthDate(LocalDate.of(1985, 2, 1));

        update.apply(person);

        //then
        assertThat(person.getFirstName(), is("호석"));
        assertThat(person.getLastName(), is("이"));
        assertThat(person.getGender(), is(Gender.MALE));
        assertThat(person.getBirthDate(), is(LocalDate.of(1985, 2, 1)));
    }

    @Test
    public void response_of() {

        //given
        Person person = Person.builder()
                .firstName("호진")
                .lastName("이")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1983, 7, 4))
                .build();

        //when
        PersonDto.Response response = PersonDto.Response.of(person);

        //then
        assertThat(response.getAge(), is(35L));
        assertThat(response.getFirstName(), is("호진"));
        assertThat(response.getLastName(), is("이"));
        assertThat(response.getGender(), is(Gender.MALE));
        assertThat(response.getBirthDate(), is(LocalDate.of(1983, 7, 4)));
    }
}