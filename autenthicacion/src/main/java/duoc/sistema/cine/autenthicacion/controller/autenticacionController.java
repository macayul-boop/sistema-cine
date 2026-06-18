package duoc.sistema.cine.autenthicacion.controller;

import duoc.sistema.cine.autenthicacion.dto.LoginRequest;
import duoc.sistema.cine.autenthicacion.dto.LoginResponse;
import duoc.sistema.cine.autenthicacion.service.AutenticacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacion")
public class autenticacionController {
    @Autowired
    private AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse respuesta = autenticacionService.login(request);
        return ResponseEntity.ok(respuesta);
    }
}
