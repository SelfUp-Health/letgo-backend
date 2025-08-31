
package com.letgo.auth.persistence.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user", schema = "mng")
class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  @Column(name = "full_name")
  private String fullName;

  private String password;

  private String provider;

  @Column(name = "provider_id")
  private String providerId;

  @Column(name = "email", length = 320)
  private String email;


}
