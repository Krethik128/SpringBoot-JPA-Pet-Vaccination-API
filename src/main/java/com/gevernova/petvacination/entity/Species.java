package com.gevernova.petvacination.entity;//

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Species {
    DOG("Dog"),
    CAT("Cat"),
    BIRD("Bird"),
    FISH("Fish"),
    REPTILE("Reptile"),
    SMALL_MAMMAL("Small Mammal"), // e.g., Rabbit, Hamster
    OTHER("Other");

    private final String displayName;
}
