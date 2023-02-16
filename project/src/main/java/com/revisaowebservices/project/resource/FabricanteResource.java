package com.revisaowebservices.project.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.revisaowebservices.project.model.Fabricante;
import com.revisaowebservices.project.repository.FabricanteRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/fabricante")
public class FabricanteResource {

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @GetMapping
    public List<Fabricante> list() {
        return fabricanteRepository.findAll();

    }

    @GetMapping("/{id}")
    public Optional<Fabricante> findById(@PathVariable Long id) {
        return fabricanteRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Fabricante> create(@RequestBody Fabricante fabricante, HttpServletResponse response) {
        Fabricante save = fabricanteRepository.save(fabricante);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(uri).body(save);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fabricanteRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fabricante> update(@PathVariable Long id, @RequestBody Fabricante fabricante) {
        Optional<Fabricante> fabricanteBanco = fabricanteRepository.findById(id);
        BeanUtils.copyProperties(fabricante, fabricanteBanco.get(), "id");
        fabricanteRepository.save(fabricanteBanco.get());
        return ResponseEntity.ok(fabricante);
    }
}