package com.dev_elliotesco.digital_bank.controllers.docs;

import com.dev_elliotesco.digital_bank.dtos.UserRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserControllerDOC {

    @Operation(
            summary = "Registrar usuario",
            description = "Crea un nuevo usuario en el sistema.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles del usuario a registrar",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"name\": \"Juan Pérez\", \"email\": \"juan@example.com\", \"address\": \"Calle 1 #12-14\"}"))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class),
                                    examples = @ExampleObject(value = "{\"id\": \"6727c0971754457f988eb0a0\", \"name\": \"Juan Pérez\", \"email\": \"juan@example.com\", \"address\": \"Calle 1 #12-14\"}"))
                    ),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida o datos de entrada incorrectos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class),
                                    examples = @ExampleObject(value = "[\"La dirección no puede estar vacía\", \"El nombre no puede estar vacío\", \"El correo no puede estar vacío\"]"))
                    ),
            }
    )
    public Mono<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequest);

    @Operation(
            summary = "Obtener saldo total del usuario",
            description = "Devuelve el saldo total de un usuario específico en el sistema.",
            parameters = {
                    @Parameter(name = "userId", description = "ID del usuario para consultar su saldo", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saldo total del usuario encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Double.class),
                                    examples = @ExampleObject(value = "1500.00"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Usuario no encontrado con ID: 1"))
                    )
            }
    )
    public Mono<Double> getTotalBalanceForUser(@PathVariable String userId);
}
