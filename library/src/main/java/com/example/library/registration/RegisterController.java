package com.example.library.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/register")
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping(path="/signup")
    public Map<String, Object> register(@RequestBody RegisterRequest request){
        System.out.println("Rejestracja otrzymana: " + request);
        return registerService.register(request);
    }

    @GetMapping("/confirm")
    public ResponseEntity<Map<String, Object>> confirm(@RequestParam("token") String token) {
        Map<String, Object> response = registerService.confirmToken(token);
        return ResponseEntity.ok(response);
    }
}
