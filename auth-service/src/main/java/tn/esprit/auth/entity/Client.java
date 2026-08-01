package tn.esprit.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Utilisateur {
    private String entreprise;
    private String matriculeFiscal;
    private List<String> adressesEnregistreesIds;
    private List<String> adressesRamassageIds;
}
