package dto;

import enums.Role;
import lombok.Data;

@Data
public class ResponseUserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean isLocked;
    private Role role;
    private String userName;
}
