package com.dev_elliotesco.digital_bank.controllers.docs;

import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
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

public interface TransactionControllerDOC {

    @Operation(
            summary = "Realizar transacción",
            description = "Crea una nueva transacción para una cuenta específica en el sistema.",
            parameters = {
                    @Parameter(name = "accountNumber", description = "Número de cuenta para la transacción", required = true, example = "123456")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles de la transacción a realizar",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"amount\": 70.0, \"type\": \"RETIRO\"}"))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transacción creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionResponseDTO.class),
                                    examples = @ExampleObject(value = "{\"id\": \"6727beb1f320f651e1ee0fb6\", \"accountId\": \"3\", \"amount\": 70.0, \"type\": \"RETIRO\", \"timestamp\": \"2024-11-03T13:19:29.4247679\"}")
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "La cuenta no existe con el número: 123456")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida o datos de entrada incorrectos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class),
                                    examples = @ExampleObject(value = "[\"El tipo de transacción no puede estar vacío\", \"La cantidad no puede estar vacía\"]")
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflicto al realizar la transacción, saldo insuficiente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "No hay saldo suficiente para realizar el retiro")
                            )
                    )
            }
    )
    public Mono<TransactionResponseDTO> makeTransaction(@PathVariable String accountNumber, @Valid @RequestBody TransactionRequestDTO transactionRequest);

}
