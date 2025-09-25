package com.auth.authorization.filter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Size(max = 80, message = "{error.user.email.max}")
    @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,10}$", message = "{error.customer.numdoc.max}")
    private String email;
    @NotBlank
    @Size(max = 8, message = "{error.user.password.max}")
    private String password;
}
