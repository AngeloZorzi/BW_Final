package it.epicode.BW_Final.filter;

import it.epicode.BW_Final.model.Fattura;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FatturaSpecification {
    public static Specification<Fattura> byCliente(Long clienteId) {
        return (root, query, cb) -> clienteId == null ? null : cb.equal(root.get("cliente").get("id"), clienteId);
    }

    public static Specification<Fattura> byStato(String stato) {
        return (root, query, cb) -> stato == null ? null : cb.equal(root.get("stato").get("nome"), stato);
    }

    public static Specification<Fattura> byData(LocalDate data) {
        return (root, query, cb) -> data == null ? null : cb.equal(root.get("data"), data);
    }

    public static Specification<Fattura> byAnno(Integer anno) {
        return (root, query, cb) -> anno == null ? null : cb.equal(cb.function("year", Integer.class, root.get("data")), anno);
    }

    public static Specification<Fattura> byImportoBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min != null && max != null) return cb.between(root.get("importo"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("importo"), min);
            if (max != null) return cb.lessThanOrEqualTo(root.get("importo"), max);
            return null;
        };
    }

    public static Specification<Fattura> allFilters(Long clienteId, String stato, LocalDate data, Integer anno, BigDecimal minImporto, BigDecimal maxImporto) {
        return null;
    }
}