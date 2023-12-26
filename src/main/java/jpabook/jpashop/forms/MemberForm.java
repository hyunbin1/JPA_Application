package jpabook.jpashop.forms;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String username;

    private String city;
    private String street;
    private String zipcode;


}
