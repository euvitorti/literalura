package br.com.alura.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultDataBookDTO(
        String title,
        List<AuthorDTO> authors,
        List<String> subjects,
        List<String> languages,
        Map<String, String> formats
) { }
