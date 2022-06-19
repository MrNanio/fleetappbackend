package pl.nankiewic.fleetappbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "city", length = 45, nullable = false)
    private String city;

    @Column(name = "street", length = 45)
    private String street;

    @Column(name = "building_number", length = 5, nullable = false)
    private String buildingNumber;

    @Column(name = "flat_number", length = 7)
    private String flatNumber;

    @Column(name = "postal_code", length = 7, nullable = false)
    private String postalCode;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private UserData userData;

}
