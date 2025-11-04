package dev.maddock.minimeli.productsservice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
class TestController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublic() {
        return ResponseEntity.ok("Public endpoint");
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Admin endpoint");
    }

    @GetMapping("/private-normal")
    @PreAuthorize("hasAuthority('product:view_public')")
    public ResponseEntity<String> getPrivate(Authentication auth) {
        return ResponseEntity.ok("can view public products");
    }

    @GetMapping("/private-normal2")
    @PreAuthorize("hasAuthority('ROLE_product:view_public')")
    public ResponseEntity<String> getPrivate2(Authentication auth) {
        return ResponseEntity.ok("can view public products2");
    }
}
