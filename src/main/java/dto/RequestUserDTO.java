package dto;

import enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
public class RequestUserDTO {
    @NotBlank(message = "Must not be empty!")
    @Length(min = 2, max = 50, message = "Invalid length!")
    private String firstName;

    @NotBlank(message = "Must not be empty!")
    @Length(min = 2, max = 50, message = "Invalid length!")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    private String phoneNumber;

    @NotNull(message = "isLocked cannot be null")
    private Boolean isLocked;

    @NotNull(message = "Role cannot be null")
    @Pattern(regexp = "^READER$|^AUTHOR$|^ADMIN$", message = "Allowed input: READER, AUTHOR or ADMIN")
    private Role role;

    @NotBlank(message = "Must not be empty!")
    @Length(min = 2, max = 50, message = "Invalid length!")
    private String userName;

    @NotBlank(message = "Must not be empty!")
    @Length(min = 2, max = 50, message = "Invalid length!")
    private String password;
}
