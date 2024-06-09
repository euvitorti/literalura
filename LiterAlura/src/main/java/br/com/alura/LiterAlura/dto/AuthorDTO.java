
package br.com.alura.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDTO(
        String name,
        int birth_year,
        int death_year
) {

//    Método toString personalizado para retornar apenas o nome e as datas de nascimento e morte de forma mais legível.
    @Override
    public String toString() {
        return name + " (" + birth_year + " - " + death_year + ")";
    }
}