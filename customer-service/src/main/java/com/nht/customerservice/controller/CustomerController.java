package com.nht.customerservice.controller;

import com.nht.customerservice.dto.CustomerDto;
import com.nht.customerservice.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.create(customerDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<CustomerDto> findByUserId(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(customerService.findByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> findAll(@RequestParam Pageable pageable) {
        return ResponseEntity.ok(customerService.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<CustomerDto> update(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.update(customerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
