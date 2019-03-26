package devlab.commons.security;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
@NoArgsConstructor
@Data
@Entity
@Table(name = "user", schema = "public")
public class UserApp {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String name;
    private String password;
    private int active; //@Todo stworzenie w logice możliwość deaktywacji usera

    @ManyToMany(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns =  @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UserApp(UserApp userApp){
        this.name = userApp.getName();
        this.password = userApp.getPassword();
        this.active = userApp.getActive();
        this.roles = userApp.getRoles();

    }

}
