package com.scaffolding.appcuentas.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scaffolding.appcuentas.entities.MovementEntity;
import com.scaffolding.appcuentas.services.MovementService;

@RestController
@RequestMapping("/movement")
public class MovementController {

    @Autowired
    MovementService movementService;

    @GetMapping("")
    public ResponseEntity<Page<MovementEntity>> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable oPageable, @RequestParam(name = "id", required = false) Long id) {
        return new ResponseEntity<Page<MovementEntity>>(movementService.getMovements(oPageable, id), HttpStatus.OK);
    }
}
