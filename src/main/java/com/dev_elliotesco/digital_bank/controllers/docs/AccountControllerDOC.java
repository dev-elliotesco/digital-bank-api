package com.dev_elliotesco.digital_bank.controllers.docs;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Account", description = "Endpoints para la gestión de cuentas de usuario en el banco digital.")
public interface AccountControllerDOC {

    @Operation(
            summary = "Crear cuenta de usuario",
            description = "Crea una nueva cuenta bancaria para un usuario específico en el sistema.",
            parameters = {
                    @Parameter(name = "userId", description = "ID del usuario propietario de la cuenta", required = true, example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles de la cuenta a crear",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AccountRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "number": "1",
                                        "type": "AHORROS"
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponseDTO.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "id": "1",
                                                "number": "12345",
                                                "type": "AHORROS",
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida o datos de entrada incorrectos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class),
                                    examples = @ExampleObject(value = "[\"El número de cuenta no puede estar vacío\", \"El tipo de cuenta no puede estar vacío\"]")
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Usuario no encontrado con el ID: 1")
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "La cuenta ya existe",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "La cuenta ya existe con el numero: 12345")
                            )),
            }
    )
    public Mono<AccountResponseDTO> createAccount(@PathVariable String userId, @Valid @RequestBody AccountRequestDTO accountRequest);

    @Operation(
            summary = "Obtener saldo de cuenta",
            description = "Obtiene el saldo actual de una cuenta bancaria específica en el sistema.",
            parameters = {
                    @Parameter(name = "accountNumber", description = "Número de la cuenta bancaria", required = true, example = "12345")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saldo de la cuenta recuperado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Double.class),
                                    examples = @ExampleObject(value = "1000.50")
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "La cuenta no existe con el número: 12345")
                            ))
            }
    )
    public Mono<Double> getBalance(@PathVariable String accountNumber);


    @Operation(
            summary = "Obtener retiros de cuenta",
            description = "Recupera la lista de todas las transacciones de retiro realizadas en una cuenta específica.",
            parameters = {
                    @Parameter(name = "accountNumber", description = "Número de la cuenta bancaria", required = true, example = "12345")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de retiros obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionResponseDTO.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                {
                                                    "transactionId": "1",
                                                    "accountId": "1",
                                                    "amount": 100.0,
                                                    "type": "WITHDRAWAL",
                                                    "timestamp": "2023-10-29T10:15:30Z"
                                                },
                                                {
                                                    "transactionId": "2",
                                                    "accountId": "1",
                                                    "amount": 50.0,
                                                    "type": "WITHDRAWAL",
                                                    "timestamp": "2023-10-30T11:25:45Z"
                                                }
                                            ]
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "La cuenta no existe con el número: 12345")
                            ))
            }
    )
    public Mono<List<TransactionResponseDTO>> getWithdrawals(@PathVariable String accountNumber);


    @Operation(
            summary = "Obtener depósitos de cuenta",
            description = "Recupera la lista de todas las transacciones de depósito realizadas en una cuenta específica.",
            parameters = {
                    @Parameter(name = "accountNumber", description = "Número de la cuenta bancaria", required = true, example = "12345")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de depósitos obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionResponseDTO.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                {
                                                    "transactionId": "3",
                                                    "accountId": "1",
                                                    "amount": 200.0,
                                                    "type": "DEPOSIT",
                                                    "timestamp": "2023-10-31T09:45:30Z"
                                                },
                                                {
                                                    "transactionId": "4",
                                                    "accountId": "1",
                                                    "amount": 150.0,
                                                    "type": "DEPOSIT",
                                                    "timestamp": "2023-11-01T08:35:25Z"
                                                }
                                            ]
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "La cuenta no existe con el número: 12345")
                            ))
            }
    )
    public Mono<List<TransactionResponseDTO>> getDeposits(@PathVariable String accountNumber);


    @Operation(
            summary = "Obtener resumen de transacciones de cuenta",
            description = "Recupera un resumen de todas las transacciones (retiros y depósitos) de una cuenta específica.",
            parameters = {
                    @Parameter(name = "accountNumber", description = "Número de la cuenta bancaria", required = true, example = "12345")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resumen de transacciones obtenido exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionSummaryDTO.class),
                                    examples = @ExampleObject(value = """
                                            [
                                                {
                                                    "transactionId": "5",
                                                    "accountId": "1",
                                                    "amount": 100.0,
                                                    "type": "WITHDRAWAL",
                                                    "timestamp": "2023-10-29T10:15:30Z"
                                                },
                                                {
                                                    "transactionId": "6",
                                                    "accountId": "1",
                                                    "amount": 200.0,
                                                    "type": "DEPOSIT",
                                                    "timestamp": "2023-10-31T09:45:30Z"
                                                }
                                            ]
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "La cuenta no existe con el número: 12345")
                            ))
            }
    )
    public Mono<List<TransactionSummaryDTO>> getTransactionSummary(@PathVariable String accountNumber);
}
