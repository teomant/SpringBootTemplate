package org.teomant.anotherlearningproject.controllers.forms;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FighterCreationForm {
    String name;
    String strength;
    String agility;
    String mind;
}
