package com.physilink.controller.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Vignesh G
 */
@Data
@Accessors(chain = true)
public class AdminSignupFormCommand {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String role;


    @NotBlank
    @Size(min = 5, max = 13)
    private String mobileNumber;
}