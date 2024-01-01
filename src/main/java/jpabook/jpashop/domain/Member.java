package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;


@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name =  "member_id")
    private Long id;

    private String username;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();


}
