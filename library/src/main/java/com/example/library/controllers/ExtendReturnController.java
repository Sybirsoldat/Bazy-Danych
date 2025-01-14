// --- KONTROLER PRZEDŁUŻANIA TERMINU ZWROTU KSIĄŻKI ---

package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.services.ExtendReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Extend Return Date API", description = "API for extending the return date of books")
public class ExtendReturnController {

    @Autowired
    private ExtendReturnService extendReturnDateService;

    /**
     * Przedłużenie terminu zwrotu książki na podstawie zamówienia.
     */
    @PutMapping("/extend-return/{orderId}/{bookId}/{additionalDays}/{userId}")
    @Operation(summary = "Extend return date", description = "Extend the return date of a book for a specific order.")
    public ResponseEntity<Orders> extendReturnDate(
            @Parameter(description = "ID zamówienia", example = "1") @PathVariable Long orderId,
            @Parameter(description = "ID książki", example = "10") @PathVariable Long bookId,
            @Parameter(description = "Dodatkowe dni przedłużenia", example = "7") @PathVariable int additionalDays,
            @Parameter(description = "ID użytkownika", example = "5") @PathVariable Long userId) {

        Orders updatedOrder = extendReturnDateService.extendReturnDate(orderId, bookId, additionalDays, userId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}