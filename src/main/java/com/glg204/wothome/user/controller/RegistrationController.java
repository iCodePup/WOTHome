package com.glg204.wothome.user.controller;

import com.glg204.wothome.authentification.dto.AuthDTO;
import com.glg204.wothome.authentification.dto.WOTUserDTO;
import com.glg204.wothome.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wothome.authentification.exception.PasswordMismatchException;
import com.glg204.wothome.config.TokenProvider;
import com.glg204.wothome.user.dto.UserDTO;
import com.glg204.wothome.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user/create")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping()
    public ResponseEntity<AuthDTO> createAccount(@Valid @RequestBody UserDTO userDTO) {

        // Check if password and repassword fields are equal
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Password et repassword ne correspondent pas.");
        }
        try {
            userService.save(passwordEncoder, userDTO);
            String token = authenticateAndGetToken(userDTO.getEmail(), userDTO.getPassword());
            WOTUserDTO registeredClientDTO =
                    new WOTUserDTO(
                            userDTO.getEmail(),
                            userDTO.getFirstName(),
                            userDTO.getLastName(),
                            "CLIENT");
            AuthDTO registeredDTO = new AuthDTO(registeredClientDTO, token);
            return ResponseEntity.ok(registeredDTO);
        } catch (EmailAlreadyExistsException e) {
            throw new EmailAlreadyExistsException("Adresse email déja utilisée");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, PasswordMismatchException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException validationException) {
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                builder.append(String.format("%s: %s", fieldName, errorMessage));
            });
        } else if (ex != null) {
            builder.append(ex.getMessage());
        }
        String responseMessage = builder.toString().trim();
        String jsonResponse = String.format("{\"message\": \"%s\"}", responseMessage);
        return ResponseEntity.badRequest().body(jsonResponse);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }
}
